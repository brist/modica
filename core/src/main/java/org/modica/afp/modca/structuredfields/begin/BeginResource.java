package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Begin Resource structured field begins an envelope that is used to carry resource objects in
 * print-file-level (external) resource groups. Resource references in the data stream are matched
 * against the resource identifier specified by the Begin Resource structured field.
 * <p>
 * Note: To optimize print performance, it is strongly recommended that the same encoding scheme be
 * used for a resource reference wherever in a print file that resource reference is specified. That
 * is, the encoding scheme used for the resource include, the resource map, and the resource wrapper
 * should be the same. For TrueType/OpenType fonts, optimal performance can be achieved by using
 * UTF-16BE as the encoding scheme.
 * </p>
 */
public class BeginResource extends StructuredFieldWithTriplets {

    private final String rsName;

    public BeginResource(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        rsName = params.getStringAt(0, 8);
    }

    /**
     * Is the identifier used to select the resource. This identifier is matched against the
     * resource reference in the data stream.
     *
     * @return the resource name
     */
    public String getRSName() {
        return rsName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ResourceName", rsName));
        return params;
    }

    public static final class BRSBuilder implements Builder {
        @Override
        public BeginResource create(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new BeginResource(intro, TripletHandler.parseTriplet(params, 10, context), params);
        }
    }
}
