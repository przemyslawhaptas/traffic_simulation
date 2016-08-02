package data_builder;

import aparapi.ArrayOps;
import osm_processer.structs.Way;

import java.util.ArrayList;
import java.util.HashMap;

public class Street {

    private int id;
    private ArrayList<Long> nodeRefs;
    private int forwardLanesNumber;
    private int backwardLanesNumber;
    private int coordsShifts;

    private static int lastId = 0;

    public Street(Way way) {
        lastId = lastId + 1;
        this.id = lastId;
        this.nodeRefs = way.getNodeRefs();
        int[] lanesNumbers = countLanes(way);
        this.forwardLanesNumber = lanesNumbers[0];
        this.backwardLanesNumber = lanesNumbers[1];
        this.coordsShifts = 0;
    }

    public Street(ArrayList<Long> nodeRefs, int coordsShifts) {
        lastId = lastId + 1;
        this.id = lastId;
        this.nodeRefs = nodeRefs;
        this.forwardLanesNumber = 1;
        this.backwardLanesNumber = 0;
        this.coordsShifts = coordsShifts;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Long> getNodeRefs() {
        return nodeRefs;
    }

    public int getForwardLanesNumber() {
        return forwardLanesNumber;
    }

    public int getBackwardLanesNumber() {
        return backwardLanesNumber;
    }

    public int getCoordsShifts() {
        return coordsShifts;
    }

    public boolean isSplittable() {
        return ((getForwardLanesNumber() > 1) || (getBackwardLanesNumber() > 0));
    }

    // For a more accurate inspection take a look at: http://wiki.openstreetmap.org/wiki/Lanes
    private int[] countLanes(Way way) {
        int forwardLanesNumber;
        int backwardLanesNumber;

        HashMap<String, String> tags = way.getTags();
        boolean assumeWayIsOneway = !tags.containsKey("oneway");
        boolean wayIsOneway = tags.containsKey("oneway") && (tags.get("oneway").equals("yes"));
        boolean lanesNumberIsKnown = tags.containsKey("lanes");

        if (wayIsOneway || assumeWayIsOneway) {
            backwardLanesNumber = 0;
            if (lanesNumberIsKnown) {
                forwardLanesNumber = Integer.parseInt(tags.get("lanes"));
            } else {
                forwardLanesNumber = 0;
            }
        } else {
            // when way has both forward and backward lanes
            if (lanesNumberIsKnown) {
                // e.g. if there are 3 lanes to share, there are 2 forward and 2 backward lanes
                forwardLanesNumber = (int) Math.ceil(Double.parseDouble(tags.get("lanes")) / 2);
                backwardLanesNumber = forwardLanesNumber;
            } else {
                forwardLanesNumber = 1;
                backwardLanesNumber = 1;
            }
        }

        return new int[] {forwardLanesNumber, backwardLanesNumber};
    }

    public static ArrayList<Long> reverseNodeRefs(ArrayList<Long> nodeRefs) {
        ArrayList<Long> reversed = new ArrayList<Long>();
        int nodeRefsSize = nodeRefs.size();
        for (int i = 0; i < nodeRefsSize; i++) {
            reversed.add(nodeRefs.get(nodeRefsSize - (i + 1)));
        }

        return reversed;
    }
}
