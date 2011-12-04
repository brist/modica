package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.triplets.TripletTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class FQNGridDataTestCase extends TripletTestCase<FQNGridData> {
    private FQNGridData x;
    private byte[] gridBytes;
    private FQNType type;
    private int length;

    @Before
    @Override
    public void setUp() {
        gridBytes = ByteUtils.createByteArray(00, 01, 00, 02, 00, 03, 00, 04);
        type = FQNType.font_family_name;
        length = 0;

        x = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes)), type);
        FQNGridData y = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes)), type);
        FQNGridData z = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes)), type);

        byte[] notEqualsGrid = new byte[gridBytes.length];
        System.arraycopy(gridBytes, 0, notEqualsGrid, 0, gridBytes.length);
        notEqualsGrid[7] = 0x05;

        FQNGridData notEqual = new FQNGridData(length, new GlobalResourceId(new Parameters(notEqualsGrid)), type);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        GlobalResourceId expected = new GlobalResourceId(new Parameters(gridBytes));
        assertEquals(expected, x.getGrid());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(type, x.getFQNType());
        assertEquals(length, x.getLength());
    }

}
