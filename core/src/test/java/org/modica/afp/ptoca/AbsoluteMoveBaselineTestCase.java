package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.AbsoluteMoveBaseline;
import org.modica.afp.ptoca.ControlSequenceIdentifier;

/**
 * Test case for {@link AbsoluteMoveBaseline}.
 */
public class AbsoluteMoveBaselineTestCase extends ControlSequenceTestCase<AbsoluteMoveBaseline> {

    private AbsoluteMoveBaseline sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0x01, 0x02 }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.ABSOLUTE_MOVE_BASELINE;
        int length = 4;
        boolean isChained = true;

        sut = new AbsoluteMoveBaseline(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sut.getDisplacement());

        assertEquals("moveto 258", sut.getValueAsString());
    }
}