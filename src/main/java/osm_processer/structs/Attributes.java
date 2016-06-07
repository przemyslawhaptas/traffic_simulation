package osm_processer.structs;

import java.util.ArrayList;
import java.util.HashMap;
import org.antlr.runtime.tree.*;
import osm_processer.OSMData;

public class Attributes {

    private HashMap<String, String> attributes;

    public Attributes(Tree treeWithAttributeTrees) {
        attributes = extractAttributes(treeWithAttributeTrees);
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    private HashMap<String, String> extractAttributes(Tree treeWithAttributeTrees) {
        HashMap<String, String> attributes = new HashMap<String, String>();
        for (int i = 0; i < treeWithAttributeTrees.getChildCount(); i++) {
            Tree attributeTree = treeWithAttributeTrees.getChild(i);
            if (isAttributeTree(attributeTree))
                addAttribute(attributeTree, attributes);
        }

        return attributes;
    }

    private boolean isAttributeTree(Tree tree) {
        return tree.getText().equals("ATTRIBUTE");
    }

    private void addAttribute(Tree attributeTree, HashMap<String, String> attributes) {
        String key = attributeTree.getChild(0).getText();
        String valueWithQuotes = attributeTree.getChild(1).getText();
        String value = valueWithQuotes.substring(1, valueWithQuotes.length()-1);
        attributes.put(key, value);
    }
}
