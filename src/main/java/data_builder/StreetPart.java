package data_builder;

import osm_processer.structs.Node;
import osm_processer.structs.Way;

import java.util.ArrayList;

// A single-lane, one-way, between-junctions part of a street
public class StreetPart {
    private int id;
    private int capacity;
    private ArrayList<Integer> outputs;
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

    private int calculateCapacity(Node startNode, Node endNode){
        //TODO

        return 0;
    }
}
