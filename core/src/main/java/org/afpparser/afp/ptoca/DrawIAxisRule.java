package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies dimensions of a rule that extends from the current presentation
 * position in both the I-direction and the B-direction. The current I-axis and B-axis coordinates
 * are not changed by this control sequence.
 */
public class DrawIAxisRule extends ControlSequence {

    private final int length;
    private final double width;

    public DrawIAxisRule(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        this.length = params.getInt(2);
        if (getLength() > 4) {
            int integerPart = params.getInt(2);
            double fraction = getFraction(params.getByte());
            width = integerPart + fraction;
        } else {
            width = 0;
        }
    }

    /**
     * The length of the rule in the I-direction.
     *
     * @return the length of the rule
     */
    public int getDrawLength() {
        return length;
    }

    /**
     * The width of the rule in the B-direction.
     *
     * @return the width of the rule
     */
    public double getDrawWidth() {
        return width;
    }

    @Override
    public String getValueAsString() {
        return "length=" + length + " width=" + width;
    }
}
