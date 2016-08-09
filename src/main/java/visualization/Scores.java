package visualization;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Scores {
    public static void chuj(String[] args) throws IOException
    {
        System.out.println(84/5);
        String filename = ("C:\\Users\\Nikodem\\Desktop\\simulation_log.txt");
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        int linesCounter = 1;
        int maxCarsNumber = 0;

        while(inputFile.hasNextLine()) {
            System.out.print("linia " + linesCounter);
            linesCounter++;

            String line = inputFile.nextLine();
            Scanner lineScanner = new Scanner(line);
            int intCounterInLine = 0;
            while(lineScanner.hasNextInt()){
                intCounterInLine++;
                int tmp = lineScanner.nextInt();
                if(tmp > maxCarsNumber) { maxCarsNumber = tmp; }
            }
            System.out.println(" # " + intCounterInLine);
        }

        System.out.println("Max cars numer = " + maxCarsNumber);


/*        while (inputFile.hasNextInt())
        {
            int tempInt = inputFile.nextInt();
            ctr++;
        }*/

        inputFile.close();
       // System.out.println("The file contains this many : " + ctr);
    }
}
