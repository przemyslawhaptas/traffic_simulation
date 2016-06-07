package osm_processer.structs;

import org.antlr.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;

public class Way {

    private long id;
    private ArrayList<Long> nodesIds;
    private HashMap<String, String> tags;

    public Way(Tree wayTree) {
        id = Long.parseLong(new Attributes(wayTree).getAttributes().get("id"));
        nodesIds = new ArrayList<>();
        Attributes attributes = new Attributes();

        for (int i = 0; i < wayTree.getChildCount(); i++) {
            Tree child = wayTree.getChild(i);
            if (child.getChildCount() == 0) continue;
            if (isAttribute(child)) continue;

            if (isNd(child))
                handleNd(child, nodesIds);
            else if(isTag(child))
                attributes = handleTag(child, attributes);
        }
        tags = attributes.getAttributes();
    }

    @Override
    public String toString() {
        return "Way{" +
                "id=" + id +
                ", nodesIds=" + nodesIds +
                ", tags=" + tags +
                '}';
    }

    public long getId() {
        return id;
    }

    public ArrayList<Long> getNodesIds() {
        return nodesIds;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    private boolean isAttribute(Tree tree) {
        return tree.getText().equals("ATTRIBUTE");
    }

    private boolean isNd(Tree tree) {
        return tree.getChild(0).getText().equals("nd");
    }

    private boolean isTag(Tree tree) {
        return tree.getChild(0).getText().equals("tag");
    }

    private void handleNd(Tree ndTree, ArrayList<Long> nodesIds) {
        HashMap<String, String> attributes = new Attributes(ndTree).getAttributes();
        nodesIds.add(Long.parseLong(attributes.get("ref")));
    }

    private Attributes handleTag(Tree tagTree, Attributes tags) {
        HashMap<String, String> readAttributes = new Attributes(tagTree).getAttributes();
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put(readAttributes.get("k"), readAttributes.get("v"));
        tags.addAttributes(attributes);

        return tags;
    }
}
