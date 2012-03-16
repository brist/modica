package org.modica.parser.lazy;

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

public class LazyStructuredField extends AbstractStructuredField {

    private static final Logger LOG = LoggerFactory.getLogger(LazyStructuredField.class);

    private static final StructuredField SF_GUARD = new AbstractStructuredField(null) {
        @Override
        public List<ParameterAsString> getParameters() {
            return Collections.emptyList();
        }
    };

    private StructuredField structuredField;

    private Future<Context> contextFuture;

    private final StructuredFieldIntroducer introducer;

    private final FileChannelProvider fileChannelProvider;

    public LazyStructuredField(StructuredFieldIntroducer introducer, Future<Context> contextFuture,
            FileChannelProvider fileChannelProvider) {
        super(introducer);
        this.introducer = introducer;
        this.contextFuture = contextFuture;
        this.fileChannelProvider = fileChannelProvider;
    }

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
            structuredField = SF_GUARD;
            return;
        }
        StructuredFieldFactory factory = new StructuredFieldFactoryImpl(
                fileChannelProvider.getFileChannel(), context);
        switch (getType().getTypeCode()) {
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
            structuredField = SF_GUARD;
        }
        if (structuredField == null) {
            structuredField = SF_GUARD;
        }
    }

    @Override
    public List<ParameterAsString> getParameters() {
        if (structuredField == null) {
            load();
        }
        return structuredField.getParameters();
    }

    @Override
    public String toString() {
        return structuredField == null ? "Proxy: " + introducer.toString()
                : structuredField.toString();
    }
}