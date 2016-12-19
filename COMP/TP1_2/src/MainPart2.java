import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

/**
 * Programme principal du TP1 partie 2
 */
public class MainPart2 {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        TP1Part2Lexer lexer = new TP1Part2Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TP1Part2Parser parser = new TP1Part2Parser(tokens);

        //Production de l'AST
        TP1Part2Parser.doc_return r = parser.doc();

		CommonTree t = (CommonTree)r.getTree();
		// System.out.println(t.toStringTree());         
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
		
        Arpenteur walker = new Arpenteur(nodes);
        walker.doc();

		
    }
}
