package org.modica.parser.lazy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.StructuredFieldFactoryImpl;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.parser.lazy.LazyAfp.FileChannelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convienient factory class for creating {@link StructuredField} proxy objects.
 * 
 * @author Tim Grafford <tim@kgm.se>
 */
public class StructuredFieldProxyFactory {

    /**
     * A {@link StructuredField} proxy.
     * <p> 
     * All {@link AbstractStructuredField} methods will be delegated to the {@link StructuredFieldIntroducer}, 
     * if the proxy is not already instantiated. 
     * 
     * @author Tim Grafford <tim@kgm.se>
     */
    private static class StructuredFieldProxy implements InvocationHandler {

        private static final Logger LOG = LoggerFactory.getLogger(StructuredFieldProxy.class);

        private StructuredField structuredField;

        private Future<Context> contextFuture;

        private final StructuredFieldIntroducer introducer;

        private final FileChannelProvider fileChannelProvider;

        public StructuredFieldProxy(StructuredFieldIntroducer introducer,
                Future<Context> contextFuture,
                FileChannelProvider fileChannelProvider) {
            this.introducer = introducer;
            if (introducer == null) {
                System.out.println("null introducer");
            }
            this.contextFuture = contextFuture;
            this.fileChannelProvider = fileChannelProvider;
        }

        @Override
        public Object invoke(Object target, Method method, Object[] arguments)
                throws Throwable {
            if (structuredField == null) {
                String methodName = method.getName();
                if (methodName.equals("hasExtData")) {
                    return introducer.hasExtData();
                } else if (methodName.equals("hasSegmentedData")) {
                    return introducer.hasSegmentedData();
                } else if (methodName.equals("hasDataPadding")) {
                    return introducer.hasDataPadding();
                } else if (methodName.equals("getLength")) {
                    return introducer.getLength();
                } else if (methodName.equals("getType")) {
                    return introducer.getType();
                } else if (methodName.equals("getOffset")) {
                    return introducer.getOffset();
                } else if (methodName.equals("getExtLength")) {
                    return introducer.getExtLength();
                } else if (methodName.equals("bytesToNextStructuredField")) {
                    return introducer.bytesToNextStructuredField();
                }
            }
            load();
            return method.invoke(structuredField, arguments);
        }

        /**
         * Loads the proxy.
         */
        private void load() {
            LOG.debug("load()");
            Context context;
            try {
                context = contextFuture.get();
                contextFuture = null;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (context == null) {
                LOG.debug("Context is null so creating a guard Structured field for introduce "
                        + introducer);
                structuredField = createGuard(introducer);
                return;
            }
            StructuredFieldFactory factory = new StructuredFieldFactoryImpl(
                    fileChannelProvider.getFileChannel(), context);
            switch (introducer.getType().getTypeCode()) {
            case Begin:
                structuredField = factory.createBegin(introducer);
                break;
            case End:
                structuredField = factory.createEnd(introducer);
                break;
            case Map:
                structuredField = factory.createMap(introducer);
                break;
            case Descriptor:
                structuredField = factory.createDescriptor(introducer);
                break;
            case Migration:
                structuredField = factory.createMigration(introducer);
                break;
            case Data:
                structuredField = factory.createData(introducer);
                break;
            case Position:
                structuredField = factory.createPosition(introducer);
                break;
            case Include:
                structuredField = factory.createInclude(introducer);
                break;
            case Control:
                structuredField = factory.createControl(introducer);
                break;
            case Index:
                structuredField = factory.createIndex(introducer);
                break;
            default:
                LOG.debug("No factory method associated with introducer.  "
                        + "Creating a guard SF for introduce " + introducer);
                structuredField = createGuard(introducer);
            }
            if (structuredField == null) {
                LOG.debug("Factory created a null Structured Field. "
                        + "Creating a guard SF for introduce " + introducer);
                structuredField = createGuard(introducer);
            }
        }

        private AbstractStructuredField createGuard(
                StructuredFieldIntroducer introducer) {
            return new StructuredFieldGuard(introducer);
        }

        private static class StructuredFieldGuard extends
                AbstractStructuredField {

            public StructuredFieldGuard(StructuredFieldIntroducer introducer) {
                super(introducer);
            }

            @Override
            public List<ParameterAsString> getParameters() {
                return Collections.emptyList();
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }
    }

    public static StructuredField getProxy(StructuredFieldIntroducer intro,
            Future<Context> contextFuture,
            FileChannelProvider fileChannelProvider) {
        return (StructuredField) Proxy.newProxyInstance(
                StructuredFieldProxyFactory.class.getClassLoader(),
                new Class[] { StructuredField.class },
                new StructuredFieldProxy(intro, contextFuture,
                        fileChannelProvider));
    }
}
