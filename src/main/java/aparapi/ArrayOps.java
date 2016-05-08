package aparapi;

import com.amd.aparapi.Kernel;

import java.util.Arrays;

/**
 * Created by przemek on 07.05.16.
 */
public class ArrayOps {

    private static final int STREETS_CELLS_SIZE = 10;
    private static final int ITERATIONS_NUMBER = 4;

    public static void main(String[] args) {
        TrafficModel trafficModel = new TrafficModel(new int[20], STREETS_CELLS_SIZE);

        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            System.out.println("TURN " + i + " ==========================================");
            trafficModel.execute(2);
            if (!trafficModel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU)){
                System.out.println("Kernel did not execute on the GPU!");
            }

            printTraffic(trafficModel.getTraffic());
        }
    }

    public static void printTraffic(int[] traffic) {
        int streetsNumber = traffic.length / STREETS_CELLS_SIZE;

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
}
