/**
 * 
 */
package org.modica.afp.modca.structuredfields.attribute;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;

/**
 * The Tag Logical Element assigns a attribute name and a attribute
 * value to a page or page group. The scope is determined by its
 * position with respect of other TLEs in the same page or page group.
 * 
 * @author Tim Grafford (tim @ kgm.se)
 */
public class TagLogicalElement extends StructuredFieldWithTriplets {

    public TagLogicalElement(StructuredFieldIntroducer introducer, List<Triplet> triplets) {
        super(introducer, triplets);
    }

    /* (non-Javadoc)
     * @see org.modica.afp.modca.structuredfields.StructuredField#getParameters()
     */
    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

}
