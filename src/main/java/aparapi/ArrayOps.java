package aparapi;

import com.amd.aparapi.Kernel;
import data_builder.DataBuilder;
import data_builder.StreetPart;
import osm_processer.OSMData;
import osm_processer.OSMProcesser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by przemek on 07.05.16.
 */
public class ArrayOps {

    public static final int STREETS_CELLS_SIZE = 17;
    private static final int ITERATIONS_NUMBER = 40;
    public static final double CAR_AND_SPACE_AROUND_IT_LENGTH_IN_METRES = 8;

    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
        ArrayList<StreetPart> streetParts = DataBuilder.buildStreetParts(data);
        int[] aparapiStreets = DataBuilder.buildAparapiStreets(streetParts);

        int[] startTraffic = initializeTraffic(aparapiStreets);
        TrafficModel trafficModel = buildTrafficModel(startTraffic);
        int streetsNumber = countStreets(trafficModel);

        run(trafficModel, streetsNumber);
    }

    public static int[] initializeTraffic(int[] streets) {
        Random random = new Random();
        int streetsSize = streets.length;

        for(int i = 1; i < streetsSize; i = i + STREETS_CELLS_SIZE) {
            int capacity = streets[i - 1];
            streets[i] = capacity > 0 ? random.nextInt(capacity) : 0;
            //todo: make sure this zero capacity wasn't result of some earlier mistakes
        }
    //cap=cars=outputs==tries=destination
//        return new int[]{
//                5, 5, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 3, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 4, 1, 4, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 5, 2, 5, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 3, 2, 5, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 5, 1, 6, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 3, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
//                5, 1, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1};

        return streets;
    }

    private static TrafficModel buildTrafficModel(int[] startTraffic) {
        Random random = new Random();
        long seed = random.nextLong();

        return new TrafficModel(startTraffic, STREETS_CELLS_SIZE, seed);
    }

    public static void run(TrafficModel trafficModel, int streetsNumber) {
        System.out.println("cap=cars=outputs==tries=destination\n");
        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            System.out.println("TURN " + i + " ==========================================");
            trafficModel.execute(streetsNumber);
            if (!trafficModel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU)){
                System.out.println("Kernel did not execute on the GPU!");
            }

            printTraffic(trafficModel.getTraffic(), streetsNumber);
        }
    }

    private static int countStreets(TrafficModel trafficModel) {
        int[] traffic = trafficModel.getTraffic();

        return traffic.length / STREETS_CELLS_SIZE;
    }

    private static void printTraffic(int[] traffic, int streetsNumber) {
        for (int i = 0; i < streetsNumber; i++) {
            int[] street = Arrays.copyOfRange(traffic, i * STREETS_CELLS_SIZE, (i + 1) * STREETS_CELLS_SIZE);
            printStreet(street);
        }
    }

    private static void printStreet(int[] street) {
        System.out.print("{ ");
        for(int i = 0; i < street.length; i++) {
            System.out.print(street[i] + " ");
        }
        System.out.print("}\n");
    }
}
