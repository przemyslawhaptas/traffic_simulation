package osm_processer;

import osm_processer.structs.Way;

import java.util.*;

public class OSMDataFilter {
    public static OSMData filterRegularHighways(OSMData data) {
        Set<String> HIGHWAY_VALUES = new HashSet<String>(Arrays.asList(
                "motorway",
                "trunk",
                "primary",
                "secondary",
                "tertiary",
                "unclassified",
                "residential",
                "service",
                "motorway_link",
                "trunk_link",
                "primary_link",
                "secondary_link",
                "tertiary_link",
                "living_street"));

        ArrayList<Way> ways = data.getWays();
        ArrayList<Way> newWays = new ArrayList<>();

        for (int i = 0; i < ways.size(); i++) {
            Way currentWay = ways.get(i);
            if(wayHasTagFromSet(currentWay, HIGHWAY_VALUES))
                newWays.add(currentWay);
        }

        return new OSMData(data.getBounds(), data.getNodes(), newWays);
    }

    private static boolean wayHasTagFromSet(Way way, Set set) {
        boolean success = false;

        HashMap<String, String> tags = way.getTags();
        for (HashMap.Entry<String, String> entry : tags.entrySet()) {
            if (!entry.getKey().equals("highway"))
                continue;
            else if (set.contains(entry.getValue())) {
                success = true;
                break;
            }
        }

        return success;
    }
}
