package osm_graph;

/**
 * Created by Nikodem on 18.04.2016.
 */
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class ParseOSM {

    LinkedList nodes;
    LinkedList edges;
    RoadGraph g = new RoadGraph();

    public static void main(String[] args) {
        try {
            ParseOSM start = new ParseOSM();
            for (DirectedEdge edge: (LinkedList<DirectedEdge>) start.getEdges()) {
                System.out.println("Way " + edge.getWayId() + " on " + edge.getName() +
                " street isOneway = " + edge.isOneway());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ParseOSM() throws FileNotFoundException, IOException, XmlPullParserException {
        System.out.println("Run started at"+ LocalDateTime.now() );
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        String slash = File.separator;
        String mapFilePath = System.getProperty("user.dir") + slash + "src" + slash + "main" + slash + "resources" + slash + "raclawicka.osm";

        mapFilePath = Paths.get(mapFilePath).toString();
        System.out.println("PATH = "+mapFilePath);
        xpp.setInput ( new FileReader (mapFilePath));

        g.osmGraphParser(xpp);
        nodes = g.nodes;
        edges = g.edges;
        System.out.println("Parsing ended at"+ LocalDateTime.now() );
        System.out.println("Edges = "+edges.size());
        System.out.println("Nodes = "+nodes.size());
    }
    public LinkedList getNodes() {
        return nodes;
    }
    public void setNodes(LinkedList nodes) {
        this.nodes = nodes;
    }
    public LinkedList getEdges() {
        return edges;
    }
    public void setEdges(LinkedList edges) {
        this.edges = edges;
    }
    public RoadGraph getRoadGraph() {
        return g;
    }
    public void setG(RoadGraph g) {
        this.g = g;
    }

}