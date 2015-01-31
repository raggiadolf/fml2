import java.util.ArrayList;
import java.io.Reader;
import java.io.InputStreamReader;
/**
 * Should contain a public method, nextToken(), which scans the standard input (stdin),
 * looking for patterns that match one of the tokens from Token.
 * Note that the lexemes corresponding to the tokens PLUS, MINUS, MULT, LPAREN, RPAREN,
 * ASSIGN, SEMICOL contain only a single letter.
 * The patterns for the lexemes for the other tokens are:
 *      INT   = [0-9]+    (non-empty, finite sequence of digits)
 *      ID    = [A-Za-z]+ (non-empty, finite sequence of upper- and lower-case letters)
 *      END   = end
 *      PRINT = print
 * The lexical analyzer returns a token with TokenCode = ERROR if some illegal lexeme is found.
 */
public class Lexer {
    private int nextTokenIndex = 0;
    private ArrayList<Token> tokens = null;
    private Reader reader = new InputStreamReader(System.in);
    private char nextChar;

    /*
   *  Scans the standard input(stdin),
   *  looking for patterns that match one of the tokens from Token?
   */
    public Token nextToken() {
        String curLexeme = "";

        try {
            int ch;
            while ((ch = reader.read()) != -1) {
                nextChar = Character.toChars(ch)[0];
                curLexeme += nextChar;
                System.out.println(nextChar);
            }
        }
        catch (java.io.IOException ex) {

        }

        return lookup(curLexeme);
    }

    private char getChar() {
        try {
            int ch = reader.read();


        }

        catch (java.io.IOException ex) {
        }

        return ' ';
    }

    //Returns null if Lexeme is not reserved, otherwise, the tokencode for the lexeme
    private TokenCode lookup(String lexeme) {
        TokenCode retValue = null;
        if (lexeme == "+") {
            retValue = TokenCode.PLUS;
        }
        else if (lexeme == "-") {
            retValue = TokenCode.MINUS;
        }
        else if (lexeme == "*") {
            retValue = TokenCode.MULT;
        }
        else if (lexeme == "(") {
            retValue = TokenCode.LPAREN;
        }
        else if (lexeme == ")") {
            retValue = TokenCode.RPAREN;
        }
        else if (lexeme == ";") {
            retValue = TokenCode.SEMICOL;
        }
        else if (lexeme == "end") {
            retValue = TokenCode.END;
        }
        else if (lexeme == "print") {
            retValue = TokenCode.PRINT;
        }

        return retValue;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        lex.nextToken();
    }
}
