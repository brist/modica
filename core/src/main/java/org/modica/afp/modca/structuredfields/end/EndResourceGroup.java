package org.modica.afp.modca.structuredfields.end;

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
 * The End Resource Group structured field terminates the definition of a resource group initiated
 * by a Begin Resource Group structured field.
 */
public class EndResourceGroup extends StructuredFieldWithTriplets {

    private final EndFieldName rGrpName;

    public EndResourceGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        rGrpName = new EndFieldName(params);
    }

    /**
     * Returns the name of the resource group that is being terminated. If a name is specified, it
     * must match the name in the most recent Begin Resource Group structured field in the print
     * file, document, page, or data object, or a X’01’ exception condition exists. If the first two
     * bytes of RGrpName contain the value X'FFFF', the name matches any name specified on the Begin
     * Resource Group structured field that initiated the current definition.
     *
     * @return the resource group name
     */
    public String getRGrpName() {
        return rGrpName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the
     * BeginResourceGroup object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return rGrpName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ResourceGroupName", getRGrpName()));
        return params;
    }

    public static final class ERGBuilder implements Builder {
        @Override
        public EndResourceGroup create(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new EndResourceGroup(intro, TripletHandler.parseTriplet(params, 8, context), params);
        }
    }
}
