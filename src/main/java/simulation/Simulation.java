package simulation;

import aparapi.TrafficModel;
import com.amd.aparapi.Kernel;
import data_builder.DataBuilder;
import data_builder.StreetPart;
import osm_processer.OSMData;
import osm_processer.OSMProcesser;
import visualization.LinesComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Simulation {

    public static final String MAP_PATH = "osm/cracow.osm";
    public static final String RESULT_PATH = "simulation_results.txt";
    public static final int STREETS_CELLS_SIZE = 17;
    public static final int ITERATIONS_NUMBER = 200;
    public static final double CAR_AND_SPACE_AROUND_IT_LENGTH_IN_METRES = 8;

    private static ArrayList<StreetPart> streetParts;

    public static void main(String[] args) {
        OSMData data = OSMProcesser.run(args);
        streetParts = DataBuilder.buildStreetParts(data);
        int[] aparapiStreets = DataBuilder.buildAparapiStreets(streetParts);

        int[] startTraffic = initializeTraffic(aparapiStreets);

        TrafficModel trafficModel = buildTrafficModel(startTraffic);
        LinesComponent visualizer = new LinesComponent();

        run(trafficModel, visualizer);
    }

    public static void run(TrafficModel trafficModel, LinesComponent visualizer) {
        int streetsNumber = countStreets(trafficModel);

        TrafficWriter writer = new TrafficWriter(Simulation.RESULT_PATH, streetsNumber);

        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            System.out.println("TURN " + i + " ==========================================");
            trafficModel.execute(streetsNumber);

            boolean executedOnGPU = trafficModel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU);
            if (!executedOnGPU) {
                System.out.println("Kernel did not execute on the GPU!");
            }

            int[] traffic = trafficModel.getTraffic();
            writer.printIteration(traffic);
            writer.writeIteration(traffic);
        }

        writer.close();

        try {
            visualizer.visualizationStart(streetParts);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] initializeTraffic(int[] streets) {
        Random random = new Random();
        int streetsSize = streets.length;

        for(int i = 1; i < streetsSize; i = i + STREETS_CELLS_SIZE) {
            int capacity = streets[i - 1];
            streets[i] = capacity > 0 ? random.nextInt(capacity) : 0;
        }

        return streets;
    }

    private static TrafficModel buildTrafficModel(int[] startTraffic) {
        Random random = new Random();
        long seed = random.nextLong();

        return new TrafficModel(startTraffic, STREETS_CELLS_SIZE, seed);
    }

    private static int countStreets(TrafficModel trafficModel) {
        int[] traffic = trafficModel.getTraffic();

        return traffic.length / STREETS_CELLS_SIZE;
    }
}
