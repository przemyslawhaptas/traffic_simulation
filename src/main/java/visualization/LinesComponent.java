package visualization;

import data_builder.StreetPart;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class LinesComponent extends JComponent {
    private static final int TIMESTAMP = 500;
    private static final int width = 1024;
    private static final int height = 768;
    private static final double latitudeRange = 18591.0;
    private static final double minLatitude = 50.062928;
    private static final double longitudeRange = 45045.0;
    private static final double minLongitude = 19.8890998;
    private static final ArrayList<Color> colorList = new ArrayList<Color>();
    private static final ArrayList allInputsTable = new ArrayList();

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1,x2,x3,x4, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        try {
            for (Line line : lines) {
                g2.setColor(line.color);
                g2.setStroke(new BasicStroke(6));
                g2.draw(new Line2D.Float(line.x1, line.y1, line.x2, line.y2));
            }
        } catch (Exception e) {
        }
    }

    public static void visualizationStart(ArrayList<StreetPart> streetParts) throws InterruptedException, IOException {
        JFrame testFrame = new JFrame();

        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(width, height));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(clearButton);
        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comp.clearLines();
            }
        });
        testFrame.pack();
        testFrame.setVisible(true);

        loadInputFile(Simulation.RESULT_PATH);
        createColorScale();
       // double minIntensity = 19.0;
       // double maxIntensity = 0.0;
        for (int iteration = 0; iteration < allInputsTable.size(); iteration++) {
            ArrayList singleIterationTable = (ArrayList) allInputsTable.get(iteration);
            for(int i = 0; i < streetParts.size(); i++) {
                int y1 = getHeightFromLatitude(streetParts.get(i).getStartNode().getLat());
                int y2 = getHeightFromLatitude(streetParts.get(i).getEndNode().getLat());
                int x1 = getWidthFromLongitude(streetParts.get(i).getStartNode().getLon());
                int x2 = getWidthFromLongitude(streetParts.get(i).getEndNode().getLon());

                double carsNumber = (double)((int)singleIterationTable.get(i));
                double capacity = streetParts.get(i).calculateAccuracyCapacity(streetParts.get(i).getStartNode(), streetParts.get(i).getEndNode());
                double intensity = Math.floor(100 * (carsNumber / capacity));

                comp.addLine(x1, y1, x2, y2, getProperColor(intensity));
            }
            Thread.sleep(TIMESTAMP);
            if(iteration < allInputsTable.size() - 1)
                comp.clearLines();
        }
    }

    private static void loadInputFile(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner inputFile = new Scanner(file);

        while(inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            Scanner lineScanner = new Scanner(line);

            ArrayList inputFromLine = new ArrayList();

            while(lineScanner.hasNextInt()){
                int tmp = lineScanner.nextInt();
                inputFromLine.add(tmp);
            }
            allInputsTable.add(inputFromLine);
        }
    }

    private static int getHeightFromLatitude(double latitude) {
        return (int)(Math.abs(((latitude - minLatitude) * 1000000 * height / latitudeRange) - height));
    }

    private static int getWidthFromLongitude(double longitude){
        return (int)((longitude - minLongitude) * 1000000 * width / longitudeRange);
    }

    private static void createColorScale() {
        // kolory wg. skali pH
        // https://pl.wikipedia.org/wiki/Skala_pH
        colorList.add(new Color(204, 255, 0)); //00-14 aut //kwasny deszcz
        colorList.add(new Color(255, 255, 0)); //14-28 aut //kawa
        colorList.add(new Color(255, 204, 0)); //28-42 aut //piwo
        colorList.add(new Color(255, 153, 0)); //42-56 aut //sok pomaranczowy
        colorList.add(new Color(255, 102, 0)); //56-70 aut //ocet
        colorList.add(new Color(153,   0, 0)); //70-84 aut //lekki kwas solny
        colorList.add(new Color(102,   0, 0)); //84-100 aut //mocny kwas solny
        colorList.add(new Color(184, 3, 255)); // ponad 100 fiolet - sth goes wrong
    }

    public static Color getProperColor(double _intensity) {
        int intensity = (int)_intensity;

        if(intensity == 0) return new Color(0, 0, 0);

        int range = (intensity / 21);

        if(range < 7)
            return colorList.get(range);
        else return colorList.get(7);
    }

    public static void drawFirstIteration(LinesComponent comp, ArrayList<StreetPart> streetParts) {
        for(int i = 0; i < streetParts.size(); i++) {
            int y1 = getHeightFromLatitude(streetParts.get(i).getStartNode().getLat());
            int y2 = getHeightFromLatitude(streetParts.get(i).getEndNode().getLat());
            int x1 = getWidthFromLongitude(streetParts.get(i).getStartNode().getLon());
            int x2 = getWidthFromLongitude(streetParts.get(i).getEndNode().getLon());
            comp.addLine(x1, y1, x2, y2, new Color(0, 0, 0));
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

