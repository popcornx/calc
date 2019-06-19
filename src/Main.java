import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static boolean exit = false;
    private static CalculatorLexer cl = new CalculatorLexer();
    private static Map<String, Double> identifiers = new HashMap<>();
    private static Token current;
    private static String output = "";
    private static double number;

    public static void main(String[] args) {
        // Set up Input
        Scanner in = new Scanner(System.in);
        while(true) {
            // Invite input
            System.out.print("Type your expression: ");
            // Get input
            String input = in.nextLine();
            // Parse input
            parse(input);
            // Return on 'exit' Token
            if(exit) return;
            // Print output
            System.out.println(output);
            // Reset output
            output = "";
        }
    }

    private static void parse(String input) {
        cl.initLexer(input);
        try {
            current = cl.nextToken();
        }
        catch (LexerException e) {
            output = e.getMessage();
            return;
        }
        switch (current.type) {
            case Token.END :
                exit = true;
                break;
            case Token.LET :
                let();
                break;
            default :
                if(expression()) {
                    output = "Result: " + number;
                }
        }
    }

    private static void let() {
        try {
            current = cl.nextToken();
        }
        catch (LexerException e) {
            output = e.getMessage();
            return;
        }
        if(current.type == Token.ID) {
            String identifier = current.str;
            try {
                current = cl.nextToken();
            }
            catch (LexerException e) {
                output = e.getMessage();
                return;
            }
            if (current.type == Token.EQU) {
                current = cl.nextToken();
                if(expression()) {
                    identifiers.put(identifier, number);
                    output = "Result: " + identifier + "=" + number;
                }
                return;
            } else {
                output = "ERROR: wrong token '" + current.str + "',  token EQU expected";
                return;
            }
        } else {
            output = "ERROR: wrong token '" + current.str + "',  identifier token expected";
            return;
        }
    }

    private static boolean expression() {
        double term1, term2;
        if(term()) {
            term1 = number;
        } else {
            return false;
        }
        while (current.type != Token.EOL) {
            switch (current.type) {
                case Token.ADD :
                    try {
                        current = cl.nextToken();
                    }
                    catch (LexerException e) {
                        output = e.getMessage();
                        return false;
                    }
                    if (term()) {
                        term2 = number;
                        term1 = term1 + term2;
                    } else {
                        return false;
                    }
                    break;
                case Token.SUB :
                    try {
                        current = cl.nextToken();
                    }
                    catch (LexerException e) {
                        output = e.getMessage();
                        return false;
                    }
                    if (term()) {
                        term2 = number;
                        term1 = term1 - term2;
                    } else {
                        return false;
                    }
                    break;
                case Token.PAL :
                case Token.PAR :
                case Token.NUM :
                case Token.EQU :
                case Token.LET :
                case Token.ID :
                case Token.END :
                    output = illegalError();
                    return false;
                default :
                    number = term1;
                    return true;
            }
        }
        number = term1;
        return true;
    }

    private static boolean term() {
        double factor1, factor2;
        if(factor()) {
            factor1 = number;
        } else {
            return false;
        }
        while (current.type != Token.EOL) {
            switch (current.type) {
                case Token.MUL :
                    try {
                        current = cl.nextToken();
                    }
                    catch (LexerException e) {
                        output = e.getMessage();
                        return false;
                    }
                    if (factor()) {
                        factor2 = number;
                        factor1 = factor1 * factor2;
                    } else {
                        return false;
                    }
                    break;
                case Token.DIV :
                    try {
                        current = cl.nextToken();
                    }
                    catch (LexerException e) {
                        output = e.getMessage();
                        return false;
                    }
                    if (factor()) {
                        factor2 = number;
                        factor1 = factor1 / factor2;
                    } else {
                        return false;
                    }
                    break;
                case Token.PAL :
                case Token.PAR :
                case Token.NUM :
                case Token.EQU :
                case Token.LET :
                case Token.ID :
                case Token.END :
                    output = illegalError();
                    return false;
                default :
                    number = factor1;
                    return true;
            }
        }
        number = factor1;
        return true;
    }

    private static boolean factor() {
        double sign = 1;
        if(current.type == Token.SUB) {
            sign = -1;
            try {
                current = cl.nextToken();
            }
            catch (LexerException e) {
                output = e.getMessage();
                return false;
            }
        }
        switch (current.type) {
            case Token.NUM :
                number = sign * current.value;
                try {
                    current = cl.nextToken();
                }
                catch (LexerException e) {
                    output = e.getMessage();
                    return false;
                }
                break;
            case Token.ID :
                if(identifiers.containsKey(current.str)) {
                    number = sign * identifiers.get(current.str);
                    try {
                        current = cl.nextToken();
                    }
                    catch (LexerException e) {
                        output = e.getMessage();
                        return false;
                    }
                } else {
                    output = "ERROR: unknown identifier";
                    return false;
                }
                break;
            case Token.PAL :
                try {
                    current = cl.nextToken();
                }
                catch (LexerException e) {
                    output = e.getMessage();
                    return false;
                }
                if(expression()) {
                    number = sign * number;
                }
                if (current.type != Token.PAR) {
                    output = "ERROR: ')' token expected";
                    return false;
                }
                try {
                    current = cl.nextToken();
                }
                catch (LexerException e) {
                    output = e.getMessage();
                    return false;
                }
                break;
            default :
                output = "ERROR: last token '" + current.str + "', '-', '(', Number or Identifier expected";
                return false;
        }
        return true;
    }

    private static String illegalError(){
        return "ERROR: illegal token";
    }
}
