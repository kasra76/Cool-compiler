import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class Test {
    public static void main(String[] args) throws Exception {
// create a CharStream that reads from standard input
        CharStream stream = CharStreams.fromFileName("input/input1.txt");
// create a lexer that feeds off of input CharStream
        COOLLexer lexer = new COOLLexer(stream);
// create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
// create a parser that feeds off the tokens buffer
        COOLParser parser = new COOLParser(tokens);
        ParseTree tree = parser.program(); // begin parsing at init rule

        ParseTreeWalker walker = new ParseTreeWalker();
        COOLListener listener = new COOLBaseListener();
        walker.walk(listener, tree);


//        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
    }
}