package data_builder;

import osm_processer.OSMData;
import osm_processer.OSMProcesser;

public class DataBuilder {
    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
    }
}
