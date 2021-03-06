import java.util.*;
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

/**
   Grammar G for language L
   Statements -> Statement ; Statements | end
   Statement -> id = Expr | print id
   Expr -> Term | Term + Expr | Term - Expr
   Term -> Factor | Factor * Term
   Factor -> int | id | (Expr)
*/

    /** Algorithm to create intermediate code */
    /** IF not op:
       Add "push this" to list
       IF op/LPAREN:
          push to ops stack
       IF RPAREN
          while (next pop != LPAREN)
              pop and add tokenCode to list
       IF SEMICOL:
          while(!stack.empty())
             pop ops stack and add tokenCode to list..
     */

     /* Algrimurinn Josafat:
        IF *: push
        IF +/-: Athuga hvort fyrri a ops stack se *, ef svo er.. pop og output MULT.. svo push self to stack..
        IF ( : Push to stack
        IF ) : Pop ops stack until (and including the LPAREN)
        IF ; : Pop ops stack until.. empty..
       * */

    /** IF not op:
     Add "push this" to list
     IF op/LPAREN:
        push to ops stack
        if (+ or -)
           while (ops.peek() == *)
              pop ops stack and add mult to list
     IF RPAREN
       while (next pop != LPAREN)
          pop and add tokenCode to list
       if (ops.peek() == *)
          pop ops stack and add MULT to list..
     IF SEMICOL:
     while(!stack.empty())
     pop ops stack and add tokenCode to list..
     */

public class Parser {
    private Lexer lex;
    private Token token;
    private ArrayList<String> intermediateCode;
    private Stack<Token> ops;

    public Parser(Lexer lexer) {
        intermediateCode = new ArrayList<String>();
        ops = new Stack<Token>();
        lex = lexer;
    }

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
            if (token.tCode == TokenCode.SEMICOL) {
                HandleSemiCol();
                token = nextToken();
            }
            else
                Error();
            Statements();
        }
    }

    private void Statement() {
        if (token.tCode == TokenCode.ID) {
            intermediateCode.add("PUSH " + token.lexeme);
            token = nextToken();
            if (token.tCode == TokenCode.ASSIGN) {
                ops.push(token);
                token = nextToken();
                Expr();
            }
            else {
                Error();
            }
        }
        else if (token.tCode == TokenCode.PRINT) {
            ops.push(token);
            token = nextToken();
            if (token.tCode == TokenCode.ID) {
                intermediateCode.add("PUSH " + token.lexeme);
                token = nextToken();
            }
            else {
                Error();
            }
        }
        else {
            Error();
        }
    }

    private void Expr() {
        Term();

        if (token.tCode == TokenCode.PLUS) {
            /*Check for mult.. (+/- skiptir lidum)
            while (ops.peek().tCode == TokenCode.MULT)
                intermediateCode.add(ops.pop().tCode.toString());
            ops.push(token); */
            if (!ops.isEmpty()) {
                if (ops.peek().tCode == TokenCode.MULT) {
                    intermediateCode.add(ops.pop().tCode.toString());
                }
            }

            ops.push(token);

            token = nextToken();
            Expr();
        }
        else if (token.tCode == TokenCode.MINUS) {
            /*Check for mult.. (+/- skiptir lidum)
            while (ops.peek().tCode == TokenCode.MULT)
                intermediateCode.add(ops.pop().tCode.toString());
            ops.push(token); */
            if (!ops.isEmpty()) {
                if (ops.peek().tCode == TokenCode.MULT) {
                    intermediateCode.add(ops.pop().tCode.toString());
                }
            }

            ops.push(token);

            token = nextToken();
            Expr();
        }
        //No error.. can be just term and nothing more..
    }

    private void Term() {
        Factor();

        if (token.tCode == TokenCode.MULT) {
            ops.push(token);
            token = nextToken();
            Term();
        }

        //No error.. can be just factor..
    }

    private void Factor() {
        if (token.tCode == TokenCode.INT) {
            intermediateCode.add("PUSH " + token.lexeme);
            token = nextToken();
        }
        else if (token.tCode == TokenCode.ID) {
            intermediateCode.add("PUSH " + token.lexeme);
            token = nextToken();
        }
        else if (token.tCode == TokenCode.LPAREN) {
            ops.push(token);
            token = nextToken();
            Expr();
            if (token.tCode == TokenCode.RPAREN) {
                HandleRPAREN();
                token = nextToken();
            }
            else {
                Error();
            }
        }
        //Got none of that? not a valid Factor..
        else {
            Error();
        }
    }

    private void Error() {
        OutputCode();
        System.out.println("Syntax error!");
        System.exit(0);
    }

    private Token nextToken() {
        Token tmpToken = lex.nextToken();

        if (tmpToken == null) {
            OutputCode();
            System.exit(0);
        }

        if (tmpToken.tCode == TokenCode.ERROR)
            Error();

        return tmpToken;
    }

    private void HandleSemiCol() {
        Token tmp;
        while (!ops.empty()) {
            tmp = ops.pop();
            intermediateCode.add(getOpCodeForOp(tmp.tCode));
            //intermediateCode.add(tmp.tCode.toString());
        }
    }

    private void HandleRPAREN() {
        Token tmp;
        while (true) {
            if ((tmp = ops.pop()).tCode == TokenCode.LPAREN)
                break;
            intermediateCode.add(getOpCodeForOp(tmp.tCode));
            //intermediateCode.add(tmp.tCode.toString());
        }

        /*Check for MULT
        if (ops.peek().tCode == TokenCode.MULT)
            intermediateCode.add(ops.pop().tCode.toString()); */
    }

    private String getOpCodeForOp(TokenCode tCode) {
        if (tCode == TokenCode.PLUS)
            return "ADD";
        else if (tCode == TokenCode.MINUS)
            return "SUB";
        else
            return tCode.toString();
    }

    private void OutputCode() {
        for (String item : intermediateCode) {
            System.out.println(item);
        }
    }
}
