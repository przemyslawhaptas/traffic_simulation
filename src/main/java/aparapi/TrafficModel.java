package aparapi;

import com.amd.aparapi.Kernel;

/**
 * Created by przemek on 07.05.16.
 */
public class TrafficModel extends Kernel {
    private int[] streets;

    public TrafficModel(int[] emptyStreets) {
        this.streets = addFirstCars(emptyStreets);
    }

    @Override
    public void run() {
        int i = getGlobalId();
        streets[i] = streets[i] * streets[i];
    }

    private int[] addFirstCars(int[] emptyStreets) {
        int[] streets = emptyStreets;
        //todo

        return streets;
    }
}
