/**
 * Implement the class Token, which contains both a lexeme and a token code:
 *      String lexeme;
 *      TokenCode tCode;
 * where TokenCode is:
 *      enum TokenCode {    ID, ASSIGN, SEMICOL, INT, PLUS, MINUS, MULT, LPAREN,
 *                          RPAREN, PRINT, END, ERROR };
 * (See an explanation for ERROR in 2(Lexer))
 */
public class Token {
    public String lexeme;
    public TokenCode tCode;
}
