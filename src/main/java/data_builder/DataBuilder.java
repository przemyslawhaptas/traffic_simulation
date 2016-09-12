package data_builder;

import osm_processer.OSMData;
import osm_processer.structs.Node;
import osm_processer.structs.Way;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBuilder {

    public static ArrayList<StreetPart> buildStreetParts(OSMData data) {
        ArrayList<StreetPart> streetParts = convertWaysIntoStreetParts(data);
        ArrayList<StreetPart> streetPartsReindexed = reindexStreetParts(streetParts);
        ArrayList<StreetPart> aparapiReadyStreetParts = addOutputs(streetPartsReindexed);
        //todo: shift the coords

        return aparapiReadyStreetParts;
    }

    public static int[] buildAparapiStreets(ArrayList<StreetPart> streetParts) {
        int streetPartsSize = streetParts.size();
        int[] aparapiStreets = new int[streetPartsSize * Simulation.STREETS_CELLS_SIZE];

        //todo: refactor streetParts to a simple array not ArrayList to be extra sure indexes are ok
        for (int i = 0; i < streetPartsSize; i++) {
            int[] aparapiStreet = streetParts.get(i).convertIntoAparapiStreet();
            int aparapiStreetSize = aparapiStreet.length;

            for (int j = 0; j < aparapiStreetSize; j++) {
                int globalIndex = i * aparapiStreetSize + j;
                aparapiStreets[globalIndex] = aparapiStreet[j];
            }
        }
        return aparapiStreets;
    }

    private static ArrayList<StreetPart> convertWaysIntoStreetParts(OSMData data) {
        ArrayList<Way> ways = data.getWays();
        ArrayList<Node> nodes = data.getNodes();

        HashMap<Integer, Street> streets = convertWaysToStreets(ways);
        HashMap<Integer, Street> singleLaneOneWayStreets = splitStreets(streets);
        ArrayList<StreetPart> streetParts = partStreetsOnJunctions(singleLaneOneWayStreets, nodes);

        return streetParts;
    }

    private static ArrayList<StreetPart> reindexStreetParts(ArrayList<StreetPart> streetParts) {
        int streetPartsSize = streetParts.size();
        for (int i = 0; i < streetPartsSize; i++) {
            streetParts.get(i).setId(i);
        }

        return streetParts;
    }

    private static ArrayList<StreetPart> addOutputs(ArrayList<StreetPart> streetParts) {
        HashMap<Long, ArrayList<Long>> streetPartsMap = storeStreetPartsInAMap(streetParts);

        for (StreetPart streetPart: streetParts) {
            long endNodeId = streetPart.getEndNode().getId();
            ArrayList<Long> outputs = streetPartsMap.get(endNodeId);
            if (outputs == null) {
                outputs = new ArrayList<>();
            }
            //todo: make sure this null wasn't result of some earlier mistakes
            streetPart.setOutputs(outputs);
        }

        return streetParts;
    }

    private static HashMap<Long, ArrayList<Long>> storeStreetPartsInAMap(ArrayList<StreetPart> streetParts) {
        HashMap<Long, ArrayList<Long>> streetPartsMap = new HashMap<>();

        for (StreetPart streetPart: streetParts) {
            long startNodeId = streetPart.getStartNode().getId();
            ArrayList<Long> streetsWithTheSameStartNode = streetPartsMap.get(startNodeId);
            if (streetsWithTheSameStartNode == null) {
                streetsWithTheSameStartNode = new ArrayList<>();
            }

            streetsWithTheSameStartNode.add(streetPart.getId());
            streetPartsMap.put(startNodeId, streetsWithTheSameStartNode);
        }

        return streetPartsMap;
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
        HashMap<Integer, Street> streetsAfterSplit = new HashMap<>();

        for (HashMap.Entry<Integer, Street> entry: streets.entrySet()) {
            Street street = entry.getValue();
            if (street.isSplittable()) {
                ArrayList<Street> singleLaneOneWayStreets = splitStreet(street);
                for (Street singleStreet: singleLaneOneWayStreets) {
                    streetsAfterSplit.put(singleStreet.getId(), singleStreet);
                }
            } else {
                streetsAfterSplit.put(street.getId(), street);
            }
        }

        return streetsAfterSplit;
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
