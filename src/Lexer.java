import java.io.*;
import java.util.ArrayList;
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

 lookup() //0 is lexeme is not a reserved word, otherwise tokenCode for reserved word..

 getChar() //Next char in input, puts in the variable nextChar, check if it's a char/digit/something else and puts in the variable charClass

 addChar() //Adds a character to the lexeme
 */
public class Lexer {
    private BufferedReader reader;
    private char nextChar;
    private String curLexeme;
    private CharacterClass charClass;

    public Lexer() {
        Reader tmpReader = new InputStreamReader(System.in);
        reader = new BufferedReader(tmpReader);
        curLexeme = "";
        getChar();
    }

    public Lexer(String filename) {
        try {
            InputStream filestream = new FileInputStream(filename);
            Reader tmpReader = new InputStreamReader(filestream);
            reader = new BufferedReader(tmpReader);
            curLexeme = "";
            getChar();
        } catch(java.io.FileNotFoundException ex) {
            System.err.println("File not found.");
            System.err.println(ex.getStackTrace().toString());
        }

    }

    private void getChar() {
        int next = 0;
        try {
            /*
            do {
                next = reader.read(); //-1 ef EOF
            }while(next != -1 && Character.isWhitespace(Character.toChars(next)[0])); */
            next = reader.read();
            if (next == -1) {
                charClass = CharacterClass.EOF; //So nextTonken does not try and continue reading after eof..
                return;
            }
        }

        catch (java.io.IOException ex) { System.out.println("meh");}

        nextChar = Character.toChars(next)[0];
        if (Character.isDigit(nextChar)) {
            charClass = CharacterClass.Digit;
        }
        else if (Character.isLetter(nextChar)) {
            charClass = CharacterClass.Letter;
        }
        else if (Character.isWhitespace(nextChar)) {
            charClass = CharacterClass.Whitespace;
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
        /* We already have nextChar.. */

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
            case Whitespace:
                /* I eat whitespaces */
                while (charClass == CharacterClass.Whitespace) {
                    getChar();
                }
                return nextToken(); //Get a real token..
            case EOF:
                curLexeme = "";
                break;
        }

        //Curlexeme is empty when we are at EOF..
        if (!curLexeme.equals("")) {
            next.tCode = lookup(curLexeme);
            next.lexeme = curLexeme;
        }

        return (curLexeme.equals("")) ? null : next;
    }

    /** Returns null if Lexeme is not reserved, otherwise, the tokencode for the lexeme
     * We are not in here unless lexeme is a string, integer or 1 random character..
     * @param lexeme
     * @return
     */
    private TokenCode lookup(String lexeme) {
        TokenCode retValue = null;
        if (lexeme.equals("+")) {
            retValue = TokenCode.PLUS;
        }
        else if (lexeme.equals("-")) {
            retValue = TokenCode.MINUS;
        }
        else if (lexeme.equals("*")) {
            retValue = TokenCode.MULT;
        }
        else if (lexeme.equals("(")) {
            retValue = TokenCode.LPAREN;
        }
        else if (lexeme.equals(")")) {
            retValue = TokenCode.RPAREN;
        }
        else if (lexeme.equals(";")) {
            retValue = TokenCode.SEMICOL;
        }
        else if (lexeme.equals("=")) {
            retValue = TokenCode.ASSIGN;
        }
        else if (lexeme.equals("end")) {
            retValue = TokenCode.END;
        }
        else if (lexeme.equals("print")) {
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
        Token suchToken = lex.nextToken();
        System.out.println("Lex: " + suchToken.lexeme + ", TokenCode: " + suchToken.tCode.toString());

        for (int i = 0; i < 17; i++) {
            suchToken = lex.nextToken();
            System.out.println("Lex: " + suchToken.lexeme + ", TokenCode: " + suchToken.tCode.toString());
        }
    }
}
