package data_builder;

import osm_processer.OSMData;
import osm_processer.OSMProcesser;
import osm_processer.structs.Node;
import osm_processer.structs.Way;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBuilder {
    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
        ArrayList<StreetPart> streetParts = convertWaysIntoStreetParts(data);

    }

    private static ArrayList<StreetPart> convertWaysIntoStreetParts(OSMData data) {
        ArrayList<Way> ways = data.getWays();
        ArrayList<Node> nodes = data.getNodes();

        HashMap<Integer, Street> streets = convertWaysToStreets(ways);
        HashMap<Integer, Street> singleLaneOneWayStreets = splitStreets(streets);
        ArrayList<StreetPart> streetParts = partStreetsOnJunctions(singleLaneOneWayStreets, nodes);

        return streetParts;
    }

    private static HashMap<Integer, Street> convertWaysToStreets(ArrayList<Way> ways) {
        HashMap<Integer, Street> streets = new HashMap<>();
        for (Way way: ways) {
            Street street = new Street(way);
            streets.put(street.getId(), street);
        }

        return streets;
    }

    private static HashMap<Integer, Street> splitStreets(HashMap<Integer, Street> streets) {
        for (HashMap.Entry<Integer, Street> entry: streets.entrySet()) {
            Street street = entry.getValue();
            if (street.isSplittable()) {
                ArrayList<Street> singleLaneOneWayStreets = splitStreet(street);
                streets.remove(entry.getKey());
                for (Street singleStreet: singleLaneOneWayStreets) {
                    streets.put(singleStreet.getId(), singleStreet);
                }
            }
        }

        return streets;
    }

    private static ArrayList<Street> splitStreet(Street street) {
        ArrayList<Street> singleLaneOneWayStreets = new ArrayList<>();
        int forwardLanesNumber = street.getForwardLanesNumber();
        int backwardLanesNumber = street.getBackwardLanesNumber();
        ArrayList<Long> nodeRefs = street.getNodeRefs();

        for (int i = 0; i < forwardLanesNumber; i++) {
            int coordsShiftsNumber = i;
            singleLaneOneWayStreets.add(new Street(nodeRefs, coordsShiftsNumber));
        }

        if (backwardLanesNumber > 0) {
            ArrayList<Long> reversedNodeRefs = Street.reverseNodeRefs(nodeRefs);
            for (int i = 0; i < backwardLanesNumber; i++) {
                int coordsShiftsNumber = i;
                singleLaneOneWayStreets.add(new Street(reversedNodeRefs, coordsShiftsNumber));
            }
        }

        return singleLaneOneWayStreets;
    }

    private static ArrayList<StreetPart> partStreetsOnJunctions(HashMap<Integer, Street> streets,
                                                               ArrayList<Node> nodes) {
        ArrayList<StreetPart> streetParts = new ArrayList<>();
        HashMap<Long, Node> nodesMap = storeNodesInAMap(nodes);

        for (HashMap.Entry<Integer, Street> entry: streets.entrySet()) {
            Street street = entry.getValue();
            ArrayList<StreetPart> parts = partStreet(street, nodesMap);
            streetParts.addAll(parts);
        }

        return streetParts;
    }

    private static ArrayList<StreetPart> partStreet(Street street, HashMap<Long, Node> nodes) {
        ArrayList<Long> nodeRefs = street.getNodeRefs();
        int partsNumber = nodeRefs.size() - 1;
        ArrayList<StreetPart> parts = new ArrayList<>(partsNumber);

        for (int i = 0; i < partsNumber; i++) {
            Node startNode = nodes.get(nodeRefs.get(i));
            Node endNode = nodes.get(nodeRefs.get(i + 1));

            StreetPart aPart = new StreetPart(startNode, endNode, street.getCoordsShifts());
            parts.add(aPart);
        }

        return parts;
    }

    private static HashMap<Long, Node> storeNodesInAMap(ArrayList<Node> nodes) {
        HashMap<Long, Node> nodesMap = new HashMap<>();

        for (Node node: nodes) {
            nodesMap.put(node.getId(), node);
        }

        return nodesMap;
    }
}
