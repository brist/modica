package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndNamedPageGroup}.
 */
public class EndNamedPageGroupTestCase extends
        StructuredFieldWithTripletsTestCase<EndNamedPageGroup> {

    private EndNamedPageGroup sut;
    private EndNamedPageGroup sutMatchesAny;
    private final String pageName = "Testpage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EPT);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(pageName.getBytes("Cp500"), "Cp500");
        Parameters matchesAny = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sut = new EndNamedPageGroup(intro, triplets, params);
        sutMatchesAny = new EndNamedPageGroup(intro, triplets, matchesAny);
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(pageName, sut.getPGrpName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getPGrpName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("PageGroupName", pageName);
        testParameters(expectedParams, sut);
    }
}
