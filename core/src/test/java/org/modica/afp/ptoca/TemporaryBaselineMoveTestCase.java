package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.TemporaryBaselineMove;
import org.modica.afp.ptoca.TemporaryBaselineMove.Direction;
import org.modica.common.ByteUtils;

public class TemporaryBaselineMoveTestCase extends ControlSequenceTestCase<TemporaryBaselineMove> {

    private TemporaryBaselineMove sut;
    private TemporaryBaselineMove withoutIncrementWithoutPrecision;
    private TemporaryBaselineMove withoutIncrementWithPrecision;
    private TemporaryBaselineMove withIncrementWithoutPrecision;
    private TemporaryBaselineMove withIncrementWithPrecision;

    @Before
    public void setUp() {
        Parameters params = new Parameters(ByteUtils.createByteArray(0));
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.TEMPORARY_BASELINE_MOVE;
        int length = 3;
        boolean isChained = true;

        sut = new TemporaryBaselineMove(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        length = 4;
        params = new Parameters(ByteUtils.createByteArray(1, 0));
        withoutIncrementWithoutPrecision = new TemporaryBaselineMove(expectedCsId, length, isChained, params);

        params = new Parameters(ByteUtils.createByteArray(2, 1));
        withoutIncrementWithPrecision = new TemporaryBaselineMove(expectedCsId, length, isChained, params);

        length = 6;
        params = new Parameters(ByteUtils.createByteArray(3, 0, 1, 2));
        withIncrementWithoutPrecision = new TemporaryBaselineMove(expectedCsId, length, isChained, params);

        params = new Parameters(ByteUtils.createByteArray(0, 1, 3, 4));
        withIncrementWithPrecision = new TemporaryBaselineMove(expectedCsId, length, isChained, params);
    }

    @Test
    public void testGetterMethods() {
        testSystemUnderTest(Direction.NO_CHANGE, false, 0, sut);
        testSystemUnderTest(Direction.RETURN_BASELINE, false, 0, withoutIncrementWithoutPrecision);
        testSystemUnderTest(Direction.INCREMENT_BASELINE, true, 0, withoutIncrementWithPrecision);
        testSystemUnderTest(Direction.DECREMENT_BASELINE, false, 0x102,
                withIncrementWithoutPrecision);
        testSystemUnderTest(Direction.NO_CHANGE, true, 0x304, withIncrementWithPrecision);
    }

    private void testSystemUnderTest(Direction dir, boolean precision, int increment,
            TemporaryBaselineMove sut) {
        assertEquals(dir, sut.getDirection());
        assertEquals(precision, sut.getPrecision());
        assertEquals(increment, sut.getIncrement());
        assertEquals(valueAsString(dir, increment), sut.getValueAsString());
    }

    private String valueAsString(Direction direction, int increment) {
        return "direction=" + direction.toString()
                + " increment=" + increment;
    }
}
