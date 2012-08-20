package org.modica.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

/**
 * The Attribute Value is used to specify a value for a document attribute.
 * 
 * @see org.modica.afp.modca.structuredfields.attribute.TagLogicalElement
 * @author Tim Grafford (tim @ kgm.se)
 */
public class AttributeValue extends Triplet {
    
    private final int length;
    private final String value;

    public AttributeValue(Parameters params, int length) throws UnsupportedEncodingException {
        this.length = length;
        params.skip(2);
        int strLength = length - 4;
        this.value = params.getString(strLength);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.attribute_value;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Value", value));
        return params;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + length;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AttributeValue)) {
            return false;
        }
        AttributeValue other = (AttributeValue) obj;
        if (length != other.length) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
