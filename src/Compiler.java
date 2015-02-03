/**
 * Write a main class, Compiler, which only does the following:
 *      Lexer myLexer = new Lexer();
 *      Parser myParser = new Parser(myLexer);
 *      myParser.parse();
 */
public class Compiler {
    public static void main(String[] args) {
        Lexer myLexer;
        if(args.length > 0) {
            myLexer = new Lexer(args[0]);
        } else {
            myLexer = new Lexer();
        }
        Parser myParser = new Parser(myLexer);
        myParser.parse();
    }
}
