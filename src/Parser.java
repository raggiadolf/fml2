/**
 * This should be a top-down recursive-descent parser from the grammar G.
 * The output of the parser is the stack-based intermediate code S,
 * corresponding to the given program, written to standard output(stdout).
 * If the expression is not in the language (or if an ERROR token is returned by the Lexer)
 * then the parser should output the erorr message "Syntax error!"
 * (at the point when the error is recognized) and immediately quit.
 *
 * The parser should have at least two (private) member variables, one of type
 * Lexer, the other of type Token (for the current token). It should only have a
 * single public method, parse(), for initiating the parse - other methods are private.
 */

/*
   Grammar G for language L
   Statements -> Statement ; Statements | end
   Statement -> id = Expr | print id
   Expr -> Term | Term + Expr | Term - Expr
   Term -> Factor | Factor * Term
   Factor -> int | id | (Expr)
*/

public class Parser {
    private Lexer lex = new Lexer();
    private Token token;
    public void parse() {
        token = nextToken();
        Statements();
    }

    private void Statements() {
        if (token.tCode == TokenCode.END) {
            token = nextToken();
        }
        else {
            Statement();
            if (token.tCode == TokenCode.SEMICOL)
                token = nextToken();
            else
                Error();
            Statements();
        }
    }

    private void Statement() {
        if (token.tCode == TokenCode.ID) {
            token = nextToken();
            if (token.tCode == TokenCode.ASSIGN) {
                token = nextToken();
                Expr();
            }
            else {
                error();
            }
        }
        else if (token.tCode == TokenCode.PRINT) {
            token = nextToken();
            if (token.tCode == TokenCode.id) {
                token = nextToken();
            }
            else {
                error();
            }
        }
        else {
            error();
        }
    }

    private void Expr() {
        Term();

        if (token.tCode == TokenCode.PLUS) {
            token = nextToken();
            Expr();
        }
        else if (token.tCode == TokenCode.MINUS) {
            token = nextToken();
            Expr();
        }
        //Engin error.. má vera bara term og ekkert meira..
    }

    private void Term() {
        Factor();

        if (token.tCode == TokenCode.MULT) {
            token = nextToken();
            Term();
        }

        //Ekkert error.. má vera bara factor..
    }

    private void Factor() {
        if (token.tCode == TokenCode.INT) {
            token = nextToken();
        }
        else if (token.tCode == TokenCode.ID) {
            token = nextToken();
        }
        else if (token.tCode == TokenCode.LPAREN) {
            token = nextToken();
            Expr();
            if (token.tCode == TokenCode.RPAREN) {
                token = nextToken();
            }
            else {
                error();
            }
        }
        //Fengum ekkert af þessu? Ekki valid Factor..
        else {
            error();
        }
    }

    private void Error() {
        System.out.println("Syntax error!");
        System.exit(0);
    }

    private Token nextToken() {
        Token tmpToken = lex.nextToken();
        if (tmpToken.tCode == TokenCode.ERROR)
            error();
        return tmpToken;
    }
}
