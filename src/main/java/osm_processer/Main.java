package osm_processer;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class Main {

    public static void main(String[] args) {
        try {
            CharStream input = new ANTLRFileStream("src/main/java/osm_processer/osm/raclawicka_wo_relations.osm");
            OSMLexer lex = new OSMLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lex);
            OSMParser parser = new OSMParser(tokens);
            OSMParser.document_return root = parser.document();
            CommonTree root_tree = (CommonTree) root.tree;

            ASTWalker walker = new ASTWalker(root_tree);
            OSMData data = walker.walkTree();
            System.out.println(data);

            OSMData filteredData = OSMDataFilter.RegularHighwaysFilter.filter(data);
            System.out.println(filteredData);

            OSMData filteredData2 = OSMDataFilter.OnlyReferencedNodesFilter.filter(filteredData);
            System.out.println(filteredData2);
            System.out.println("nodesNr = " + filteredData2.getNodes().size());
            System.out.println("waysNr = " + filteredData2.getWays().size());

            double[] adaptedNodes = VisualizerAdapter.call(filteredData2.getNodes());
            System.out.print("adaptedNodes = { ");
            for (double node : adaptedNodes)
                System.out.print(node + " ");
            System.out.println("}");
        } catch (Throwable t) {
            System.out.println("exception: " + t);
            t.printStackTrace();
        }
    }
}
