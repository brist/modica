/**
 * 
 */
package org.modica.afp.modca.structuredfields.attribute;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Attribute;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.triplets.AttributeValue;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.common.ByteUtils;

/**
 * @author Tim Grafford (tim @ kgm.se)
 */
public class TagLogicalElementTestCase extends StructuredFieldWithTripletsTestCase<TagLogicalElement> {
    
    public static final String ATTRIBUTE_GID_REF = "0F020B00D5C1D4C540E2E3D9C9D5C7";
    public static final String ATTRIBUTE_VALUE = "10360000E5C1D3E4C540E2E3D9C9D5C7";
    
    private StructuredFieldIntroducer intro;
    private TagLogicalElement sut;
    
    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Attribute.TLE);
        
        List<Triplet> triplets = addTripletToList(
                ATTRIBUTE_GID_REF
                );
        Parameters params = new Parameters(ByteUtils.hexToBytes(ATTRIBUTE_VALUE));
        int length = (int) params.getUInt(1);
        params.skip(1);
        triplets.add(new AttributeValue(params, length));
        
        sut = new TagLogicalElement(intro, triplets);
        setMembers(sut, intro, triplets);
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        testParameters(expectedParams, sut);
    }

}
