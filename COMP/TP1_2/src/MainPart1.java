import org.antlr.runtime.*;
 
public class MainPart1 {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        TP1Part1Lexer lexer = new TP1Part1Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TP1Part1Parser parser = new TP1Part1Parser(tokens);
        parser.doc();
    }
}
