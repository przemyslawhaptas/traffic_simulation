package osm_processer;

import org.antlr.runtime.tree.*;

public class ASTWalker {
    private Tree tree;

    public ASTWalker(Tree tree) {
        this.tree = tree;
    }

    public void walkTree(Tree t, int indent) {
        if (t != null) {
            StringBuffer sb = new StringBuffer(indent);

            for (int i = 0; i < t.getChildCount(); i++) {
                Tree child = t.getChild(i);
                if (child.getChildCount() == 0) continue;

                if (!isAttribute(child)) {
                    if (isBounds(child))
                        handleBounds(child);
                    else if (isNode(child))
                        handleNode(child);
                    else if (isWay(child))
                        handleWay(child);
                    else
                        System.out.println(child.toString());
                }
            }
        }
    }

    private boolean isAttribute(Tree t) {
        return t.getText().toString().equals("ATTRIBUTE");
    }

    private boolean isWay(Tree t) {
        return t.getChild(0).getText().toString().equals("way");
    }

    private boolean isNode(Tree t) {
        return t.getChild(0).getText().toString().equals("node");
    }

    private boolean isBounds(Tree t) {
        return t.getChild(0).getText().toString().equals("bounds");
    }

    private void handleWay(Tree t) {
        System.out.println("\nI'm handling a way:");
        System.out.println(t.toStringTree());
    }

    private void handleNode(Tree t) {
        System.out.println("\nI'm handling a node:");
        System.out.println(t.toStringTree());
    }

    private void handleBounds(Tree t) {
        System.out.println("\nI'm handling bounds:");
        System.out.println(t.toStringTree());
    }
}
