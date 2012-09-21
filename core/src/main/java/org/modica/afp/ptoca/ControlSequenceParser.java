package org.modica.afp.ptoca;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;

/**
 * Parses a chain of control sequences.
 */
public class ControlSequenceParser {

    private static final byte CONTROL_SEQUENCE_PREFIX = 0x2B;
    private static final byte CONTROL_SEQUENCE_CLASS = (byte) 0xD3;

    /**
     * Parses a control sequence or a chain of control sequences and returns them as elements of a
     * list.
     *
     * @param sfParams the structured field parameters
     * @return a List of control sequences
     * @throws UnsupportedEncodingException 
     */
    public static List<ControlSequence> parse(Parameters sfParams, Context ctx)
            throws UnsupportedEncodingException {
        int paramSize = sfParams.size();
        List<ControlSequence> controlSequences = new ArrayList<ControlSequence>();
        boolean startNewChain = sfParams.peekByte() == CONTROL_SEQUENCE_PREFIX;
        while (sfParams.getPosition() < paramSize) {
            ControlSequence cs;
            if (startNewChain) {
                cs = parseNewChain(sfParams, ctx);
            } else {
                cs = createControlSequence(sfParams, ctx);
            }
            startNewChain = !cs.isChained();
            controlSequences.add(cs);
        }

        return controlSequences;
    }

    private static ControlSequence parseNewChain(Parameters sfParams, Context ctx)
            throws UnsupportedEncodingException {
        byte prefix = sfParams.getByte();
        byte classByte = sfParams.getByte();
        assert prefix == CONTROL_SEQUENCE_PREFIX;
        assert classByte == CONTROL_SEQUENCE_CLASS;
        return createControlSequence(sfParams, ctx);
    }

    private static ControlSequence createControlSequence(Parameters params, Context ctx)
            throws UnsupportedEncodingException {
        int length = (int) params.getUInt(1);
        byte id = params.getByte();
        boolean isChained;
        if (id % 2 != 0) {
            id -= 1;
            isChained = true;
        } else {
            isChained = false;
        }
        ControlSequenceIdentifier csId = ControlSequenceIdentifier.getCsId(id);
        return csId.createControlSequence(length, isChained, params, ctx);
    }
}
