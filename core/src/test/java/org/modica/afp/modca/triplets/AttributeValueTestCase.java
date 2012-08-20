/**
 * 
 */
package org.modica.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

/**
 * @author tgd01
 *
 */
public class AttributeValueTestCase extends TripletTestCase<AttributeValue> {
    
    private AttributeValue x;
    private String expectedString;
    private int length;

    @Before
    @Override
    public void setUp() {
        expectedString = "Test String";
        length = expectedString.length() + 4;
        try {
            this.x = createAttributeValue(expectedString);
            AttributeValue y = createAttributeValue(expectedString);
            AttributeValue z = createAttributeValue(expectedString);
            AttributeValue notEqual = createAttributeValue("Not same string");
            setXYZ(x, y, z, notEqual);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        }
    }
    
    @Test
    public void testGetters() {
        assertEquals(expectedString, x.getValue());
        assertEquals(length, x.getLength());
        assertEquals(TripletIdentifiers.attribute_value, x.getTid());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("Value", expectedString));
        parameterTester(expectedParams, x);
    }

    public static AttributeValue createAttributeValue(String str) throws UnsupportedEncodingException {
        byte[] strData = str.getBytes("cp500");
        byte[] bytes = new byte[strData.length + 4];
        System.arraycopy(strData, 0, bytes, 4, strData.length);
        bytes[0] = (byte) bytes.length;
        Parameters params = new Parameters(bytes);
        params.skip(2);
        return new AttributeValue(params, bytes.length);
    }
    
}
