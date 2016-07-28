package data_builder;

import osm_processer.OSMData;
import osm_processer.OSMProcesser;
import osm_processer.structs.Way;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBuilder {
    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
    }

    public static HashMap<Integer, Street> splitWaysIntoSingleLaneOneWayStreets(ArrayList<Way> ways) {
        HashMap<Integer, Street> allStreets = convertWaysToStreets(ways);
        
        //TODO
        return allStreets;
    }

    private static HashMap<Integer, Street> convertWaysToStreets(ArrayList<Way> ways) {
        HashMap<Integer, Street> allStreets = new HashMap<>();
        for (Way way: ways) {
            Street street = new Street(way);
            allStreets.put(street.getId(), street);
        }

        return allStreets;
    }

    private boolean isMultiLaneWay(Way way) {
        //TODO
        return true;
    }

    private boolean isTwoway(Way way) {
        //TODO
        return true;
    }

    private static ArrayList<Way> splitMultiLaneWay(Way way) {
        //TODO
        return null;
    }

    private static ArrayList<Way> splitTwoway(Way way) {
//        TODO
        return null;
    }
}
