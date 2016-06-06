package osm_processer;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class Main {
    public static int minLat;
    public static int minLon;
    public static int maxLat;
    public static int maxLon;

    public static void main(String[] args) {
        try {
            CharStream input = new ANTLRFileStream("src/main/java/osm_processer/osm/raclawicka_wo_relations.osm");
            OSMLexer lex = new OSMLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lex);
            OSMParser parser = new OSMParser(tokens);
            OSMParser.document_return root = parser.document();
            CommonTree root_tree = (CommonTree) root.tree;

            ASTWalker walker = new ASTWalker(root_tree);
            walker.walkTree(root_tree, 0);
        } catch (Throwable t) {
            System.out.println("exception: " + t);
            t.printStackTrace();
        }
    }
}
