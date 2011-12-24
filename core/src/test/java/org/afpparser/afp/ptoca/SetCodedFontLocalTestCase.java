package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link SetCodedFontLocal}.
 */
public class SetCodedFontLocalTestCase extends ControlSequenceTestCase<SetCodedFontLocal> {
    private SetCodedFontLocal sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { (byte) 0x31 }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_CODED_FONT_LOCAL;
        int length = 3;
        boolean isChained = true;

        sut = new SetCodedFontLocal(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x31, sut.getLocalId());

        assertEquals("localid=0x31", sut.getValueAsString());
    }
}
