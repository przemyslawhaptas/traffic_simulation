package osm_processer;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class Main {

    public static void main(String[] args) {
        try {
            CharStream input = new ANTLRFileStream("src/main/java/osm_processer/osm/cracow.osm");
            OSMLexer lex = new OSMLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lex);
            OSMParser parser = new OSMParser(tokens);
            OSMParser.document_return root = parser.document();
            CommonTree root_tree = (CommonTree) root.tree;

            ASTWalker walker = new ASTWalker(root_tree);
            OSMData data = walker.walkTree();
            System.out.println(data);

            OSMData prefilteredData = OSMDataFilter.RegularHighwaysFilter.filter(data);
            System.out.println(prefilteredData);

            OSMData filteredData = OSMDataFilter.OnlyReferencedNodesFilter.filter(prefilteredData);
            System.out.println(filteredData);

            double[] adaptedNodes = VisualizerAdapter.call(filteredData.getNodes());
            Visualizer.call(adaptedNodes);
        } catch (Throwable t) {
            System.out.println("exception: " + t);
            t.printStackTrace();
        }
    }
}
