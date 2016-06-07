package osm_processer.structs;

import org.antlr.runtime.tree.*;

public class Bounds {

    private double minLat;
    private double minLon;
    private double maxLat;
    private double maxLon;

    public Bounds(double minLat, double minLon, double maxLat, double maxLon) {
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
    }

    public Bounds(Tree tree) {

    }

    public double getMinLat() {
        return minLat;
    }

    public double getMinLon() {
        return minLon;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public double getMaxLon() {
        return maxLon;
    }

    public String toString() {
        return "minLat = " + minLat + ", " +
                "minLon = " + minLon + ", " +
                "maxLat = " + maxLat + ", " +
                "maxLon = " + maxLon;
    }
}
