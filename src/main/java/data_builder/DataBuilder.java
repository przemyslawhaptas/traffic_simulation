package data_builder;

import osm_processer.OSMData;
import osm_processer.OSMProcesser;
import osm_processer.structs.Way;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataBuilder {
    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
    }

    public static HashMap<Integer, Street> splitWaysIntoSingleLaneOneWayStreets(ArrayList<Way> ways) {
        HashMap<Integer, Street> streets = convertWaysToStreets(ways);
        HashMap<Integer, Street> singleLaneOneWayStreets = splitStreets(streets);

        //TODO
        return singleLaneOneWayStreets;
    }

    public static HashMap<Integer, Street> convertWaysToStreets(ArrayList<Way> ways) {
        HashMap<Integer, Street> streets = new HashMap<>();
        for (Way way: ways) {
            Street street = new Street(way);
            streets.put(street.getId(), street);
        }

        return streets;
    }

    public static HashMap<Integer, Street> splitStreets(HashMap<Integer, Street> streets) {
        for (Map.Entry<Integer, Street> entry: streets.entrySet()) {
            Street street = entry.getValue();
            if (street.isSplittable()) {
                ArrayList<Street> singleLaneOneWayStreets = splitStreet(street);
                streets.remove(entry.getKey());
                for (Street singleStreet: singleLaneOneWayStreets) {
                    streets.put(singleStreet.getId(), singleStreet);
                }
            }
        }

        return streets;
    }

    private static ArrayList<Street> splitStreet(Street street) {
        ArrayList<Street> singleLaneOneWayStreets = new ArrayList<Street>();
        int forwardLanesNumber = street.getForwardLanesNumber();
        int backwardLanesNumber = street.getBackwardLanesNumber();
        ArrayList<Long> nodeRefs = street.getNodeRefs();

        for (int i = 0; i < forwardLanesNumber; i++) {
            int coordsShiftsNumber = i;
            singleLaneOneWayStreets.add(new Street(nodeRefs, coordsShiftsNumber));
        }

        if (backwardLanesNumber > 0) {
            ArrayList<Long> reversedNodeRefs = Street.reverseNodeRefs(nodeRefs);
            for (int i = 0; i < backwardLanesNumber; i++) {
                int coordsShiftsNumber = i;
                singleLaneOneWayStreets.add(new Street(reversedNodeRefs, coordsShiftsNumber));
            }
        }

        return singleLaneOneWayStreets;
    }

}
