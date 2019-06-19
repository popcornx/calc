// Lexer for classical arithmetic expressions with identifiers and assignements.
// Scans a source string char by char.

public class CalculatorLexer {

  private String src;  // source string for lexical analysis
  private int idx;     // current index in source
  private int len;     // length of source

  public CalculatorLexer() { }

  public void initLexer(String source) {
    this.src = source;
    idx = 0;
    len = src.length();
  }

  // Consumes letters only and builds an identifier
  private String identifier() {
    StringBuffer s = new StringBuffer();
  
    do {
      s.append(src.charAt(idx));
      idx++;
    } while (idx < len && Character.isLetter(src.charAt(idx)));
    return s.toString();
  }

  // Consumes digits and convert integer part and decimal part
  // Convert characters using the formula
  // "3456.253" = [(((3+0)*10+4)*10+5)*10+6]+[0.1*2+0.01*5+0.001*3]
  private double number() throws LexerException {
    double v = 0;        // accumulates the result
    double factor = 0.1; // factor for decimal part

    do { // integer part
      v = v * 10 + Character.digit(src.charAt(idx),30);
      idx++;
    } while (idx < len && Character.isDigit(src.charAt(idx)));
    if (idx < len && src.charAt(idx) == '.') { // decimal point
      idx++; 
      if (idx < len && Character.isDigit(src.charAt(idx))) { // decimal part
        while (idx < len && Character.isDigit(src.charAt(idx))) {
          v = v + (factor * Character.digit(src.charAt(idx),30));
          factor = factor * 0.1;
          idx++;
        }
      }
      else throw new LexerException("Illegal number: decimal part missing");
    }
    return v;
  }

  // Skips blanks, tabs, newlines
  private void skip() {
    char c;
    while (idx < len) {
      c = src.charAt(idx);
      if (c==' ' || c=='\t' || c=='\n') idx++;
      else break;
    }
  }

  // returns next token 
  public Token nextToken() throws LexerException {
    Token tok = new Token();

    skip();
    if (idx>=len) {
	tok.str="EOL";
	tok.type=Token.EOL;
    }
    else
      // is it a positive number?
      if (Character.isDigit(src.charAt(idx))) {
        tok.value = number();
        tok.type  = Token.NUM;
        tok.str   = Double.toString(tok.value);
      }
      else
        if (Character.isLetter(src.charAt(idx))) {
          tok.value = 0;  
          tok.type  = Token.ID;
          tok.str   = identifier();
          if (tok.str.compareTo("let")==0)  tok.type = Token.LET;
          if (tok.str.compareTo("exit")==0) tok.type = Token.END;
        }
        else {
          switch (src.charAt(idx)) {
            case '+': tok.type = Token.ADD; tok.str = "+"; break;
            case '-': tok.type = Token.SUB; tok.str = "-"; break;
            case '*': tok.type = Token.MUL; tok.str = "*"; break;
            case '/': tok.type = Token.DIV; tok.str = "/"; break;
            case '(': tok.type = Token.PAL; tok.str = "("; break;
            case ')': tok.type = Token.PAR; tok.str = ")"; break;
            case '=': tok.type = Token.EQU; tok.str = "="; break;
            default : throw new LexerException("Illegal Token: '" +
                                                src.charAt(idx) + "'");
          }
          idx++;
        }
    return tok;
  }
}