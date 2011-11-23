package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;

/**
 * The End Active Environment Group structured field terminates the definition of an Active
 * Environment Group initiated by a Begin Active Environment Group structured field.
 */
public class EndActiveEnvironmentGroup extends AbstractStructuredField {

    private final EndFieldName aegName;

    public EndActiveEnvironmentGroup(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer);
        aegName = new EndFieldName(sfData);
    }

    /**
     * Returns the name of the active environment group being terminated. If a name is specified, it
     * must match the name in the most recent Begin Active Environment Group structured field in the
     * page or a X’01’ exception condition exists. If the first two bytes in AEGName contain the
     * value X'FFFF', the name matches any name specified on the Begin Active Environment Group
     * structured field that initiated the current definition.
     *
     * @return the AEG name
     */
    public String getAegName() {
        return aegName.getName();
    }

    /**
     * Returs if the first two bytes in AEGName contain the value X'FFFF', the name matches any name
     * specified on the Begin Active Environment Group structured field that initiated the current
     * definition.
     *
     * @return whether or not the AEG name matches any in the corresponding
     * BeginActiveEnvironmentGroup
     */
    public boolean nameMatchesAny() {
        return aegName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().toString() + " AEGName=" + aegName;
    }
}
