package data_builder;

import osm_processer.OSMData;
import osm_processer.OSMProcesser;
import osm_processer.structs.Way;

import java.util.ArrayList;

public class DataBuilder {
    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
    }

    public static ArrayList<Way> splitWaysIntoSingleLaneOneWays(ArrayList<Way> ways) {
        int lastIndex = reindexWays(ways);

        //TODO
        return ways;
    }

    public static int reindexWays(ArrayList<Way> ways) {
        int lastIndex = ways.size();
        for (int i = 0; i < lastIndex; i++) {
            ways.get(i).setId(i);
        }

        return lastIndex;
    }
}
