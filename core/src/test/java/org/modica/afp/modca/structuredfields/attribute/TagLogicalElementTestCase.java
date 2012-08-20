/**
 * 
 */
package org.modica.afp.modca.structuredfields.attribute;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Attribute;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.triplets.AttributeValueTestCase;
import org.modica.afp.modca.triplets.Triplet;

/**
 * @author Tim Grafford (tim @ kgm.se)
 */
public class TagLogicalElementTestCase extends StructuredFieldWithTripletsTestCase<TagLogicalElement> {
    
    public static final String ATTRIBUTE_GID_REF = "0F020B00D5C1D4C540E2E3D9C9D5C7";
    public static final String ATTRIBUTE_VALUE = "10360000E5C1D3E4C540E2E3D9C9D5C7";
    
    private StructuredFieldIntroducer intro;
    private TagLogicalElement sut;
    private String expectedNameString = "NAME STRING";
    private String expectedValueString = "VALUE STRING";
    
    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Attribute.TLE);
        
        List<Triplet> triplets = addTripletToList(
                ATTRIBUTE_GID_REF
                );
        triplets.add(AttributeValueTestCase.createAttributeValue(expectedValueString));
        
        sut = new TagLogicalElement(intro, triplets);
        setMembers(sut, intro, triplets);
    }
    
    @Test
    public void testGetters() {
        assertEquals(expectedNameString, sut.getName());
        assertEquals(expectedValueString, sut.getValue());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("Name", expectedNameString));
        expectedParams.add(new ParameterAsString("Value", expectedValueString));
        testParameters(expectedParams, sut);
    }

}
