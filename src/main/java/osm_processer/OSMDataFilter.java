package osm_processer;

import osm_processer.structs.Node;
import osm_processer.structs.Way;

import java.util.*;

public class OSMDataFilter {

    public static OSMData filter(OSMData data) {
        OSMData filteredData = OSMDataFilter.RegularHighwaysFilter.filter(data);
        filteredData = OSMDataFilter.OnlyStartAndEndNodesFilter.filter(filteredData);
        filteredData = OSMDataFilter.OnlyReferencedNodesFilter.filter(filteredData);
        filteredData = OSMDataFilter.OnlyReferencingNodeRefsFilter.filter(filteredData);

        return filteredData;
    }

    public static class RegularHighwaysFilter {
        static Set<String> REGULAR_HIGHWAYS = new HashSet<String>(Arrays.asList(
                "motorway",
                "trunk",
                "primary",
                "secondary",
                "tertiary",
                "unclassified",
                "residential",
//                "service",
                "motorway_link",
                "trunk_link",
                "primary_link",
                "secondary_link",
                "tertiary_link"
//                "living_street"
        ));

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

    public static class OnlyStartAndEndNodesFilter {
        public static OSMData filter(OSMData data) {
            ArrayList<Way> ways = data.getWays();

            Set<Long> nodeRefsSet = new HashSet<Long>();
            for (Way way : ways) {
                nodeRefsSet.add(way.getStartNodeId());
                nodeRefsSet.add(way.getEndNodeId());
            }

            ArrayList<Node> nodes = data.getNodes();
            ArrayList<Node> newNodes = new ArrayList<Node>();

            for (Node node : nodes) {
                if(nodeRefsSet.contains(node.getId()))
                    newNodes.add(node);
            }

            return new OSMData(data.getBounds(), newNodes, ways);
        }
    }

    public static class OnlyReferencedNodesFilter {
        public static OSMData filter(OSMData data) {
            ArrayList<Way> ways = data.getWays();

            Set<Long> nodeRefsSet = new HashSet<Long>();
            for (Way way : ways) {
                nodeRefsSet.addAll(way.getNodeRefs());
            }

            ArrayList<Node> nodes = data.getNodes();
            ArrayList<Node> newNodes = new ArrayList<Node>();

            for (Node node : nodes) {
                if(nodeRefsSet.contains(node.getId()))
                    newNodes.add(node);
            }

            return new OSMData(data.getBounds(), newNodes, ways);
        }
    }

    public static class OnlyReferencingNodeRefsFilter {
        public static OSMData filter(OSMData data) {
            ArrayList<Way> ways = data.getWays();
            ArrayList<Node> nodes = data.getNodes();

            Set<Long> nodeIds = new HashSet<Long>();
            for (Node node : nodes) {
                nodeIds.add(node.getId());
            }

            for (Way way : ways) {
                ArrayList<Long> nodeRefs = way.getNodeRefs();
                ArrayList<Long> newNodeRefs = new ArrayList<Long>();
                for (long nodeRef : nodeRefs) {
                    if (nodeIds.contains(nodeRef))
                        newNodeRefs.add(nodeRef);
                }
                way.setNodeRefs(newNodeRefs);
            }

            return new OSMData(data.getBounds(), nodes, ways);
        }
    }
}
