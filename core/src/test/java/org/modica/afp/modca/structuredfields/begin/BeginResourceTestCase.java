package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link BeginResource}.
 */
public class BeginResourceTestCase extends StructuredFieldWithTripletsTestCase<BeginResource> {

    private BeginResource sut;

    private static final String resName = "TESTNAME";

    @Before
    public void setUp() throws UnsupportedEncodingException, MalformedURLException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BRS);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(resName.getBytes(EbcdicStringHandler.DEFAULT_CPGID_NAME));
        sut = new BeginResource(intro, triplets, params);
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethod() {
        assertEquals(resName, sut.getRSName());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ResourceName", resName));
        testParameters(expectedParams, sut);
    }
}
