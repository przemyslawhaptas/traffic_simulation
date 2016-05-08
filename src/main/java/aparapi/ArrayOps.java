package aparapi;

import com.amd.aparapi.Kernel;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by przemek on 07.05.16.
 */
public class ArrayOps {

    private static final int STREETS_CELLS_SIZE = 10;
    private static final int ITERATIONS_NUMBER = 4;

    public static void main(String[] args) {
        int[] startTraffic = initializeTraffic();
        Random random = new Random();
        long seed = random.nextLong();
        TrafficModel trafficModel = new TrafficModel(startTraffic, STREETS_CELLS_SIZE, seed);
        int[] traffic = trafficModel.getTraffic();
        int streetsNumber = traffic.length / STREETS_CELLS_SIZE;

        System.out.println("cap=cars=outputs===========destination");
        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            System.out.println("TURN " + i + " ==========================================");
//            trafficModel.execute(streetsNumber);
//            if (!trafficModel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU)){
//                System.out.println("Kernel did not execute on the GPU!");
//            }
            trafficModel.execute2(streetsNumber);

            printTraffic(trafficModel.getTraffic(), streetsNumber);
        }
    }

    public static void printTraffic(int[] traffic, int streetsNumber) {


        for (int i = 0; i < streetsNumber; i++) {
            int[] street = Arrays.copyOfRange(traffic, i * STREETS_CELLS_SIZE, (i + 1) * STREETS_CELLS_SIZE);
            printStreet(street);
        }
    }

    public static void printStreet(int[] street) {
        System.out.print("{ ");
        for(int i = 0; i < street.length; i++) {
            System.out.print(street[i] + " ");
        }
        System.out.print("}\n");
    }

    public static int[] initializeTraffic() {
    //todo: build traffic from graph here
        return new int[]{ 5, 2, 1, 3, 0, 0, 0, 0, 0, -1,
                5, 3, 1, 3, 0, 0, 0, 0, 0, -1,
                5, 4, 3, 0, 1, 3, 0, 0, 0, -1,
                5, 3, 2, 0, 2, 0, 0, 0, 0, -1};
    }
}
