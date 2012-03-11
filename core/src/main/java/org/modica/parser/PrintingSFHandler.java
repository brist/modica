package org.modica.parser;

import java.io.PrintStream;

import org.modica.afp.modca.structuredfields.StructuredField;

/**
 * This class is used for printing StructuredFieldHandler events to a
 * PrintStream.
 * 
 */
public class PrintingSFHandler implements StructuredFieldHandler {

    private final PrintStream out;

    public PrintingSFHandler(PrintStream out) {
        this.out = out;
    }

    @Override
    public void startAfp() {
    }

    @Override
    public void handleBegin(StructuredField structuredField) {
        out.println(structuredField);
    }

    @Override
    public void handleEnd(StructuredField structuredField) {
        out.println(structuredField);
    }

    @Override
    public void handle(StructuredField structuredField) {
        out.println(structuredField);
    }

    @Override
    public void endAfp() {
    }

}