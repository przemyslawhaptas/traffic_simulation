package data_builder;

import osm_processer.structs.Node;
import simulation.Simulation;

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

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Long> getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList<Long> outputs) {
        this.outputs = outputs;
    }

    private int calculateCapacity(Node startNode, Node endNode){
        double startNodeLat = startNode.getLat();
        double startNodeLon = startNode.getLon();
        double endNodeLat = endNode.getLat();
        double endNodeLon = endNode.getLon();
        double streetPartLengthInMetres =
                Haversine.haversine(startNodeLat, startNodeLon, endNodeLat, endNodeLon) * 1000;
        int streetPartsCapacity =
                (int) Math.floor(streetPartLengthInMetres / Simulation.CAR_AND_SPACE_AROUND_IT_LENGTH_IN_METRES);

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

    public int[] convertIntoAparapiStreet() {
        int arrayLength = Simulation.STREETS_CELLS_SIZE;
        int[] aparapiStreet = new int[arrayLength];

        aparapiStreet[0] = getCapacity();
        aparapiStreet[1] = 0; //cars number

        ArrayList<Long> outputs = getOutputs();
        int outputsSize = outputs.size();

        aparapiStreet[2] = outputsSize; //number of possible destinations

        int outputCellsNumber = arrayLength - 5;
        for (int i = 0; i < outputCellsNumber; i++) {
            if (i >= outputsSize) {
                aparapiStreet[i + 3] = 0;
            } else {
                int outputId = outputs.get(i).intValue(); //todo refactor ids from longs into ints again
                aparapiStreet[i + 3] = outputId;
            }
        }
        aparapiStreet[arrayLength - 2] = 0; //turning into a chosen destination tries counter
        aparapiStreet[arrayLength - 1] = -1; //is the next destination chosen (-1 for false, 0 for true)

        return aparapiStreet;
    }
}
