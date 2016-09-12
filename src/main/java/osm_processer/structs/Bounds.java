package osm_processer.structs;

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

    @Override
    public String toString() {
        return "Bounds{" +
                "minLat=" + minLat +
                ", minLon=" + minLon +
                ", maxLat=" + maxLat +
                ", maxLon=" + maxLon +
                '}';
    }
}
