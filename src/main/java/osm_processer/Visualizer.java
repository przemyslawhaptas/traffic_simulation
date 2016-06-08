package osm_processer;

import org.openstreetmap.gui.jmapviewer.Demo;

public class Visualizer {

    public static void call(double[] nodes) {
//        System.out.print("adaptedNodes = { ");
//        for (double node : nodes)
//            System.out.print(node + " ");
//        System.out.println("}");

        new Demo(nodes).setVisible(true);
    }
}
