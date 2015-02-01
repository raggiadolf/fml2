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

/** Reads in characters. Skips all whitespace/comments.

 lookup() //0 ef lexeme er ekki reserved word en annars tokenCode fyrir reserved word..

 getchar() //Next char í input, setur í breytuna nextChar, athugar hvort þetta er char/digit/annað og setur í breytuna charClass

 addChar() //Bætir character við lexeme
 */
public class Lexer {
    private Reader reader;
    private char nextChar;
    private String curLexeme;
    private CharacterClass charClass; /* "digit", "letter", "other" */

    public Lexer() {
        reader = new InputStreamReader(System.in);
        curLexeme = "";
    }

    private void getChar() {
        int next = 0;
        try {
            next = reader.read(); //-1 ef EOF
        }
        catch (java.io.IOException ex) {}
        nextChar = Character.toChars(next)[0];
        if (Character.isDigit(nextChar)) {
            charClass = CharacterClass.Digit;
        }
        else if (Character.isLetter(nextChar)) {
            charClass = CharacterClass.Letter;
        }
        else {
            charClass = CharacterClass.Other;
        }
    }

    private void addChar() {
        curLexeme += nextChar;
    }

    /*
   *  Scans the standard input(stdin),
   *  looking for patterns that match one of the tokens from Token?
   */
    public Token nextToken() {
        curLexeme = "";
        Token next = new Token();
        getChar();

        switch (charClass) {
            case Digit:
                while (charClass == CharacterClass.Digit) {
                    addChar();
                    getChar();
                }
                break;
            case Letter:
                while (charClass == CharacterClass.Letter) {
                    addChar();
                    getChar();
                }
                break;
            case Other:
                addChar();
                getChar();
                break;
        }

        next.tCode = lookup(curLexeme);
        next.lexeme = curLexeme;
        return next;
    }

    /** Returns null if Lexeme is not reserved, otherwise, the tokencode for the lexeme
     * Erum ekki hérna inni nema lexeme sé strengur, integer, eða 1 random character..
     * @param lexeme
     * @return
     */
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
        else if (Character.isDigit(lexeme.charAt(0))) {
            retValue = TokenCode.INT;
        }
        else if (Character.isLetter(lexeme.charAt(0))) {
            retValue = TokenCode.ID;
        }
        else {
            retValue = TokenCode.ERROR;
        }

        return retValue;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        lex.nextToken();
    }
}
