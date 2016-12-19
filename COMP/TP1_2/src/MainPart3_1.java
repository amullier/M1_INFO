import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

/**
 * Programme principal du TP1 partie 3.1
 */
public class MainPart3_1 {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        TP1Part3_1Lexer lexer = new TP1Part3_1Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TP1Part3_1Parser parser = new TP1Part3_1Parser(tokens);

        //Production de l'AST
        TP1Part3_1Parser.doc_return r = parser.doc();

		CommonTree t = (CommonTree)r.getTree();
		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
		
        ArpenteurVersion walker = new ArpenteurVersion(nodes);
        walker.doc();

		
    }
}
