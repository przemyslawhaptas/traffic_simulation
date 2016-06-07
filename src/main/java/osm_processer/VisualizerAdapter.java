package osm_processer;

import osm_processer.structs.Node;

import java.util.ArrayList;

public class VisualizerAdapter {
    public static double[] call(ArrayList<Node> nodes) {
        double[] adaptedNodes = new double[nodes.size() * 2];

        for (int i = 0; i < nodes.size(); i++) {
            adaptedNodes[i * 2] = nodes.get(i).getLat();
            adaptedNodes[i * 2 + 1] = nodes.get(i).getLon();
        }

        return adaptedNodes;
    }
}
