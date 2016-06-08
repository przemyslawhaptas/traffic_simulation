package osm_processer;

public class Visualizer {

    public static void call(double[] nodes) {
        System.out.print("adaptedNodes = { ");
        for (double node : nodes)
            System.out.print(node + " ");
        System.out.println("}");
    }
}
