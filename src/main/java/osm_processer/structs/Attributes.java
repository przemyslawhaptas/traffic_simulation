package osm_processer.structs;

import java.util.HashMap;
import org.antlr.runtime.tree.*;

public class Attributes {

    private HashMap<String, String> attributes;

    public Attributes() {
        attributes = new HashMap<String, String>();
    }

    public Attributes(Tree treeWithAttributeTrees) {
        attributes = extractAttributes(treeWithAttributeTrees);
    }

    @Override
    public String toString() {
        return "Attributes{" +
                "attributes=" + attributes +
                '}';
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void addAttributes(Attributes addedAttributes) {
        HashMap<String, String> newAttributes = new HashMap<>();
        newAttributes.putAll(this.attributes);
        newAttributes.putAll(addedAttributes.getAttributes());
        this.attributes = newAttributes;
    }

    public void addAttributes(HashMap<String, String> addedAttributes) {
        HashMap<String, String> newAttributes = new HashMap<>();
        newAttributes.putAll(this.attributes);
        newAttributes.putAll(addedAttributes);
        this.attributes = newAttributes;
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
