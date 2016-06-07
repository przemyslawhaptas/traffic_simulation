package osm_processer;

import org.antlr.runtime.tree.*;
import osm_processer.structs.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ASTWalker {
    private Tree tree;
    private OSMData data;

    public ASTWalker(Tree tree) {
        this.tree = tree;
    }

    public OSMData walkTree() {
        Bounds bounds = null;
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Way> ways = new ArrayList<>();

        if (tree != null) {
            for (int i = 0; i < tree.getChildCount(); i++) {
                Tree child = tree.getChild(i);
                if (child.getChildCount() == 0) continue;

                if (isAttribute(child))
                    handleAttribute(child);
                else if (isBounds(child))
                    bounds = handleBounds(child);
                else if (isNode(child))
                    nodes.add(handleNode(child));
                else if (isWay(child))
                    ways.add(handleWay(child));
                else
                    handleText(child);
            }
        }
        return new OSMData(bounds, nodes, ways);
    }

    private boolean isAttribute(Tree tree) {
        return tree.getText().equals("ATTRIBUTE");
    }

    private void handleAttribute(Tree tree) {

    }

    private boolean isBounds(Tree tree) {
        return tree.getChild(0).getText().equals("bounds");
    }

    private boolean isNode(Tree tree) {
        return tree.getChild(0).getText().equals("node");
    }

    private boolean isWay(Tree tree) {
        return tree.getChild(0).getText().equals("way");
    }

    private Bounds handleBounds(Tree tree) {
        System.out.println("\nI'm handling bounds:");
        HashMap<String, String> attributes = new Attributes(tree).getAttributes();
        Bounds bounds = new Bounds(
                Double.parseDouble(attributes.get("minlat")),
                Double.parseDouble(attributes.get("minlon")),
                Double.parseDouble(attributes.get("maxlat")),
                Double.parseDouble(attributes.get("maxlon")));
        System.out.println(bounds);

        return bounds;
    }

    private Node handleNode(Tree tree) {
        System.out.println("\nI'm handling a node:");
        HashMap<String, String> attributes = new Attributes(tree).getAttributes();
        Node node = new Node(
                Long.parseLong(attributes.get("id")),
                Double.parseDouble(attributes.get("lat")),
                Double.parseDouble(attributes.get("lon")));
        System.out.println(node);

        return node;
    }

    private Way handleWay(Tree tree) {
        System.out.println("\nI'm handling a way:");

        Way way = new Way(tree);
        System.out.println(way);

        return way;
    }

    private void handleText(Tree tree) {
        //System.out.println(tree.toString());
    }
}
