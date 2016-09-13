package simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static simulation.Simulation.STREETS_CELLS_SIZE;

public class TrafficWriter {

    private String filepath;
    private int streetsNumber;
    private BufferedWriter fileWriter;

    public TrafficWriter(String filepath, int streetsNumber) {
        this.filepath = filepath;
        this.streetsNumber = streetsNumber;

        try {
            fileWriter = new BufferedWriter(new FileWriter(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIteration(int[] traffic) {
        try {
            for (int i = 0; i < traffic.length; i++) {
                if ((i % STREETS_CELLS_SIZE) == 1) {
                    fileWriter.write(traffic[i] + " ");
                }
            }
            fileWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printIteration(int[] traffic) {
        for (int i = 0; i < streetsNumber; i++) {
            int[] street = Arrays.copyOfRange(traffic, i * STREETS_CELLS_SIZE, (i + 1) * STREETS_CELLS_SIZE);
            printStreet(street);
        }
    }

    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStreet(int[] street) {
        System.out.print("{ ");
        for (int i = 0; i < street.length; i++) {
            System.out.print(street[i] + " ");
        }
        System.out.print("}\n");
    }
}
