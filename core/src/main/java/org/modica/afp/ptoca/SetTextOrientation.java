package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;

/**
 * This control sequence specifies the I-axis and B-axis orientations with respect to the Xp-axis
 * for the current Presentation Text object. The orientations are rotational values expressed in
 * degrees and minutes.
 */
public class SetTextOrientation extends ControlSequence {

    private final int iOrientationDegrees;
    private final int iOrientationMinutes;
    private final int bOrientationDegrees;
    private final int bOrientationMinutes;

    public SetTextOrientation(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        byte byte1 = params.getByte();
        byte byte2 = params.getByte();
        iOrientationDegrees = getDegrees(byte1, byte2);
        iOrientationMinutes = getMinutes(byte2);
        byte1 = params.getByte();
        byte2 = params.getByte();
        bOrientationDegrees = getDegrees(byte1, byte2);
        bOrientationMinutes = getMinutes(byte2);
    }

    private int getDegrees(byte byte1, byte byte2) {
        // bits 0-9 are the degrees field
        int degrees = (byte1 & 0xff) << 1;
        degrees += (byte2 & 0xff) >>> 7;
        return degrees;
    }

    private int getMinutes(byte b) {
        return b & 0x7F;
    }

    /**
     * The degrees of rotation around the I-Axis.
     *
     * @return the degrees of rotation
     */
    public int getIOrientationDegrees() {
        return iOrientationDegrees;
    }

    /**
     * The minutes of rotation around the I-Axis.
     *
     * @return the minutes of rotation
     */
    public int getIOrientationMinutes() {
        return iOrientationMinutes;
    }

    /**
     * The degrees of rotation around the B-Axis.
     *
     * @return the degrees of rotation
     */
    public int getBOrientationDegrees() {
        return bOrientationDegrees;
    }

    /**
     * The minutes of rotation around the B-Axis.
     *
     * @return the minutes of rotation
     */
    public int getBOrientationMinutes() {
        return bOrientationMinutes;
    }

    @Override
    public String getValueAsString() {
        return "I-Axis=" + iOrientationDegrees + "\"" + iOrientationMinutes + "' "
                + "B-Axis=" + bOrientationDegrees + "\"" + bOrientationMinutes + "'";
    }
}
