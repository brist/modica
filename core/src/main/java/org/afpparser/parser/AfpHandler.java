package org.afpparser.parser;

import org.afpparser.afp.modca.StructuredField;

public interface AfpHandler {
    void handle(StructuredField sf);
}
