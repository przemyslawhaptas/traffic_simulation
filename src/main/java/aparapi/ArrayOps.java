package aparapi;

import com.amd.aparapi.Kernel;

import java.util.Arrays;

/**
 * Created by przemek on 07.05.16.
 */
public class ArrayOps {

    static final int STREETS_CELLS_SIZE = 10;

    public static void main(String[] args) {
        TrafficModel trafficModel = new TrafficModel(new int[20], STREETS_CELLS_SIZE);
        trafficModel.execute(16);
        if (!trafficModel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU)){
            System.out.println("Kernel did not execute on the GPU!");
        }

        printTraffic(trafficModel.getTraffic());
    }

    public static void printTraffic(int[] traffic) {
        int streetsNumber = traffic.length % STREETS_CELLS_SIZE;

        for (int i = 0; i < streetsNumber; i++) {
            int[] street = Arrays.copyOfRange(traffic, i * STREETS_CELLS_SIZE, STREETS_CELLS_SIZE);
            printStreet(street);
        }
    }

    public static void printStreet(int[] street) {
        System.out.print("{ ");
        for(int i = 0; i < street.length-1; i++) {
            System.out.print(street[i] + " ");
        }
        System.out.print(" }\n");
    }
}
