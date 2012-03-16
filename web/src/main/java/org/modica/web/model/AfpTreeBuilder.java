package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public interface AfpTreeBuilder {

    SfTreeNode buildTree(File inStream) throws IOException;

    void attach(SfTreeNode sfTreeNode, FileInputStream inStream) throws IOException;

    void detach(SfTreeNode sfTreeNode) throws IOException;
}
