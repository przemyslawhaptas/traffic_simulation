package data_builder;

import osm_processer.structs.Node;
import osm_processer.structs.Way;

import java.util.ArrayList;

// A single-lane, one-way, between-junctions part of a street
public class StreetPart {
    private long id;
    private int capacity;
    private ArrayList<Long> outputs;
    private Node startNode;
    private Node endNode;
    private int coordsShift;

    private static int lastId = 0;

    public StreetPart(Node startNode, Node endNode, int coordsShift) {
        lastId = lastId + 1;
        this.id = lastId;
        this.startNode = startNode;
        this.endNode = endNode;
        this.coordsShift = coordsShift;
        this.capacity = calculateCapacity(startNode, endNode);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setOutputs(ArrayList<Long> outputs) {
        this.outputs = outputs;
    }

    private int calculateCapacity(Node startNode, Node endNode){
        final double singleCarAndSpaceAroundItLengthInMetres = 10;

        double startNodeLat = startNode.getLat();
        double startNodeLon = startNode.getLon();
        double endNodeLat = endNode.getLat();
        double endNodeLon = endNode.getLon();
        double streetPartLengthInMetres = Haversine.haversine(startNodeLat, startNodeLon, endNodeLat, endNodeLon) * 1000;
        int streetPartsCapacity = (int) Math.floor(streetPartLengthInMetres / singleCarAndSpaceAroundItLengthInMetres);

        return streetPartsCapacity;
    }

    private static class Haversine {
        private static final double R = 6372.8; // In kilometers

        private static double haversine(double lat1, double lon1, double lat2, double lon2) {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c;
        }
    }
}
