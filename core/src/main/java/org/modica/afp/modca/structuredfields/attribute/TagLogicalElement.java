/**
 * 
 */
package org.modica.afp.modca.structuredfields.attribute;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.AttributeValue;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNCharStringData;

/**
 * The Tag Logical Element assigns a attribute name and a attribute
 * value to a page or page group. The scope is determined by its
 * position with respect of other TLEs in the same page or page group.
 * 
 * @author Tim Grafford (tim @ kgm.se)
 */
public class TagLogicalElement extends StructuredFieldWithTriplets {
    
    private final String name;
    private final String value;

    @SuppressWarnings("incomplete-switch")
    public TagLogicalElement(StructuredFieldIntroducer introducer, List<Triplet> triplets) {
        super(introducer, triplets);
        String n = null, v = null;
        for (Triplet triplet : triplets) {
            if (triplet instanceof FQNCharStringData && n == null) {
                FQNCharStringData fqn = (FQNCharStringData) triplet;
                switch (fqn.getFQNType()) {
                case attribute_gid:
                    n = fqn.getString();
                    break;
                }
            }
            else if (triplet instanceof AttributeValue && v == null) {
                AttributeValue atv = (AttributeValue) triplet;
                v = atv.getValue();
            } 
        }        
        name = n;
        value = v;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see org.modica.afp.modca.structuredfields.StructuredField#getParameters()
     */
    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Name", name));
        params.add(new ParameterAsString("Value", value));
        return params;
    }

}
