package org.modica.afp.modca.structuredfields.attribute;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Attribute;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Attribute} type structured fields.
 * 
 * @author Tim Grafford (tim @ kgm.se)
 */
public class AttributeHandler {

    private AttributeHandler() {
    }
    
    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case process_element:
                triplets = TripletHandler.parseTriplet(params, 0, context);
                sf = new TagLogicalElement(intro, triplets);
                break;
            default:
                sf = null;
            }
            return sf;
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        } catch (MalformedURLException mue) {
            throw new IllegalStateException(mue);
        }
    }

}
