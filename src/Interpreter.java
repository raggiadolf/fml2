import java.util.*;
import java.io.*;

/**
 * Implement the (main) class, Interpreter. This class reads S, the stack-based
 * intermediate code, from standard input (stdin), interprets it and executes it "on-the-fly".
 * The result is written to standard output. The main program of the interpreter should correspond
 * to the general function of interpreter, i.e. the fetch-decode-execute cycle.
 *
 * If the Interpreter encounters an invalid operator, or if there are not sufficiently many
 * arguments for an operator on the stack, then it should write out the error message:
 * "Error for operator: nameOfOperator" (Where nameOfOperator is the operator in question)
 * and immediately quit.
 * Use a Stack class to process the intermediate code and use a Map class to store the values
 * of identifiers.
 */

enum TokenCode { ID, ASSIGN, SEMICOL, INT, PLUS, MINUS, MULT, LPAREN, RPAREN, PRINT, END, ERROR };

class Token {
    public String lexeme;
    public TokenCode tCode;
}

public class Interpreter {
    private BufferedReader reader;
    private Stack<Token> st;
    private static Map<String, Integer> map;
    private static Interpreter interpreter;

    public Interpreter() {
        st = new Stack<Token>();
        map = new LinkedHashMap<String, Integer>();
        Reader tmpReader = new InputStreamReader(System.in);
        reader = new BufferedReader(tmpReader);
    }

    public static void main(String[] args) {
        interpreter = new Interpreter();
        try {
            String line;
            while((line = interpreter.reader.readLine()) != null) {
                if(line.contains("PUSH")) {
                    String op = line.split(" ")[1];
                    Token tmpToke = new Token();
                    tmpToke.lexeme = op;
                    if(isInteger(op)) {
                        tmpToke.tCode = TokenCode.INT;
                    } else {
                        tmpToke.tCode = TokenCode.ID;
                    }
                    interpreter.st.push(tmpToke);
                } else if(line.contains("ADD")) {
                    add();
                } else if(line.contains("SUB")) {
                    sub();
                } else if(line.contains("MULT")) {
                    multiply();
                } else if(line.contains("ASSIGN")) {
                    assign();
                } else if(line.contains("PRINT")) {
                    print();
                } else {
                    // Handle edge error case.
                    String error = line.split(" ")[0].toLowerCase();
                    System.out.println("Error for operator: " + error);
                }
            }
        } catch(java.io.IOException ex) {
            // Handle error
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException ex) {
            return false;
        }

        return true;
    }

    private static void add() {
        int lhs, rhs;
        try {
            lhs = fetchNumber();
            rhs = fetchNumber();
        } catch(EmptyStackException ex) {
            System.out.println("Error for operator: ADD");
            return;
        }

        Token returnToke = new Token();
        returnToke.lexeme = Integer.toString(lhs + rhs);
        returnToke.tCode = TokenCode.INT;
        interpreter.st.push(returnToke);
    }

    private static void sub() {
        int lhs, rhs;
        try {
            lhs = fetchNumber();
            rhs = fetchNumber();
        } catch(EmptyStackException ex) {
            System.out.println("Error for operator: SUB");
            return;
        }

        Token returnToke = new Token();
        returnToke.lexeme = Integer.toString(rhs - lhs);
        returnToke.tCode = TokenCode.INT;
        interpreter.st.push(returnToke);
    }

    private static void multiply() {
        int lhs, rhs;
        try {
            lhs = fetchNumber();
            rhs = fetchNumber();
        } catch(EmptyStackException ex) {
            System.out.println("Error for operator: MULT");
            return;
        }

        Token returnToke = new Token();
        returnToke.lexeme = Integer.toString(lhs * rhs);
        returnToke.tCode = TokenCode.INT;
        interpreter.st.push(returnToke);
    }

    private static void assign() {
        int number;
        try {
            number = fetchNumber();
        } catch(EmptyStackException ex) {
            System.out.println("Error for operator: ASSIGN");
            return;
        }

        String id = interpreter.st.pop().lexeme;

        map.put(id, number);
    }

    private static void print() {
        if(interpreter.st.isEmpty()) {
            System.out.println("Error for operator: PRINT");
            return;
        }
        if(interpreter.st.peek().tCode == TokenCode.INT) {
            System.out.println(interpreter.st.peek());
        } else {
            System.out.println(map.get(interpreter.st.peek().lexeme));
        }
    }

    private static int fetchNumber() {
        Token tmpToke = interpreter.st.pop();
        if(tmpToke.tCode == TokenCode.INT) {
            return Integer.parseInt(tmpToke.lexeme);
        } else {
            return map.get(tmpToke.lexeme);
        }
    }
}
