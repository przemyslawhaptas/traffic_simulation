package osm_processer;

import osm_processer.structs.Node;
import osm_processer.structs.Way;

import java.util.*;

public class OSMDataFilter {

    public static class RegularHighwaysFilter {
        static Set<String> REGULAR_HIGHWAYS = new HashSet<String>(Arrays.asList(
                "motorway",
                "trunk",
                "primary",
                "secondary",
                "tertiary",
                "unclassified",
                "residential",
                "service",
                "motorway_link",
                "trunk_link",
                "primary_link",
                "secondary_link",
                "tertiary_link",
                "living_street"));

        public static OSMData filter(OSMData data) {
            ArrayList<Way> ways = data.getWays();
            ArrayList<Way> newWays = new ArrayList<>();

            for (int i = 0; i < ways.size(); i++) {
                Way currentWay = ways.get(i);
                if(wayHasTagFromSet(currentWay, REGULAR_HIGHWAYS))
                    newWays.add(currentWay);
            }

            return new OSMData(data.getBounds(), data.getNodes(), newWays);
        }

        private static boolean wayHasTagFromSet(Way way, Set set) {
            boolean success = false;

            HashMap<String, String> tags = way.getTags();
            for (HashMap.Entry<String, String> entry : tags.entrySet()) {
                if (!entry.getKey().equals("highway"))
                    continue;
                else if (set.contains(entry.getValue())) {
                    success = true;
                    break;
                }
            }

            return success;
        }
    }

    public static class OnlyReferencedNodesFilter {
        public static OSMData filter(OSMData data) {
            ArrayList<Way> ways = data.getWays();
            Set<Long> nodeRefs = new HashSet<Long>();

            for (Way way : ways) {
                nodeRefs.addAll(way.getNodeRefs());
            }

            ArrayList<Node> nodes = data.getNodes();
            ArrayList<Node> newNodes = new ArrayList<Node>();

            for (Node node : nodes) {
                if(nodeRefs.contains(node.getId()))
                    newNodes.add(node);
            }

            return new OSMData(data.getBounds(), newNodes, data.getWays());
        }
    }
}
