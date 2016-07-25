package data_builder;

import aparapi.ArrayOps;
import osm_processer.structs.Way;

import java.util.ArrayList;

public class Street {
    private int id;
    private int capacity;
    private ArrayList<Integer> outputs;

    public Street(Way way) {
        //TODO
    }

    public int[] toIntegerArray() {
        int[] array = new int[ArrayOps.STREETS_CELLS_SIZE];
        //TODO

        return array;
    }
}
