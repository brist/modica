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
 * The Begin Named Page Group structured field begins a page group, which is a named, logical
 * grouping of sequential pages. A page group may contain other nested page groups. All pages in
 * the page group and all other page groups that are nested in the page group inherit the attributes
 * that are assigned to the page group using TLE structured fields.
 */
public class BeginNamedPageGroup extends StructuredFieldWithTriplets {

    private final String pGrpName;

    BeginNamedPageGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pGrpName = params.getStringAt(0, 8);
    }

    /**
     * Return the name of the page group.
     *
     * @return the name of the page group
     */
    public String getPageGroupName() {
        return pGrpName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("PageGroupName", pGrpName));
        return params;
    }

    public static final class BNGBuilder implements Builder {
        @Override
        public BeginNamedPageGroup build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new BeginNamedPageGroup(intro, TripletHandler.parseTriplet(params, 8, context), params);
        }
    }
}
