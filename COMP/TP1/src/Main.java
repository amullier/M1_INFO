import org.antlr.runtime.*;
 
public class Main {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        TP1Lexer lexer = new TP1Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TP1Parser parser = new TP1Parser(tokens);
        parser.prog();
    }
}
