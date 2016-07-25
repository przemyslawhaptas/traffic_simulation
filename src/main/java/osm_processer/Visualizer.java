package osm_processer;

import org.openstreetmap.gui.jmapviewer.Demo;

public class Visualizer {

    public static void call(double[] nodes) {
        Demo demo = new Demo(nodes);
        demo.setVisible(true);
    }
}
