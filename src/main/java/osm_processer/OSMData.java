package osm_processer;

import osm_processer.structs.*;

import java.util.ArrayList;

public class OSMData {
    private Bounds bounds;
    private ArrayList<Node> nodes;
    private ArrayList<Way> ways;

    public OSMData(Bounds bounds, ArrayList<Node> nodes, ArrayList<Way> ways) {
        this.bounds = bounds;
        this.nodes = nodes;
        this.ways = ways;
    }
}
