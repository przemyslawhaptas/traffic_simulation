package osm_processer;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class OSMProcesser {

    public static OSMData run(String[] args) {
        try {
            CharStream input = new ANTLRFileStream("src/main/java/osm_processer/osm/cracow.osm");
            OSMLexer lex = new OSMLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lex);
            OSMParser parser = new OSMParser(tokens);
            OSMParser.document_return root = parser.document();
            CommonTree root_tree = (CommonTree) root.tree;

            ASTWalker walker = new ASTWalker(root_tree);
            OSMData data = walker.walkTree();
            OSMData filteredData = OSMDataFilter.filter(data);
            System.out.println(filteredData);

            double[] adaptedNodes = VisualizerAdapter.call(filteredData.getNodes());
            Visualizer.call(adaptedNodes);

            return filteredData;
        } catch (Throwable t) {
            System.out.println("exception: " + t);
            t.printStackTrace();

            return null;
        }
    }
}
