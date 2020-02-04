# Project Calculator
This project concerns the development of a calculator that allows one 

* to assign numerical values to identifiers,
* to evaluate arithmetic expressions in which defined identifiers are used.

Here follows the context-free grammar (EBNF format) that **must** be used:
```
     Statement -> 'let' Identifier '=' Expression | 'exit' | Expresssion
    Expression -> Term { ('+'|'-') Term }
          Term -> Factor { ('*'|'/') Factor }
        Factor -> [ '-' ] ( '(' Expression ')' | Num | Identifier )
```
`Num` is a real number (without exposant) and `Identifier` is a sequence of letters.

The goal of this classic project is is twofold:

1. implement a recursive descent parser/evaluator developed in Java with the help of the following lexical analyser (lexer).
    1. Lexer for the Java version: [&rarr; CalculatorLexer.java](/src/CalculatorLexer.java)
    2. Token definitions for the lexer (and parser): [&rarr; Token.java](/src/Token.java)
    3. Exception for the lexer: [&rarr; LexerException.java](/src/LexerException.java)
2. implement a top-down parser/evaluator with JavaCC.\
Make use of version 5.0 of JavaCC due to a problem of compatibility with Java 8.0.\
The home page of JavaCC is [&rarr; here](https://javacc.github.io/javacc/).

Due to a lack of time you have the choice between the two versions: recursive descent in Java or top-down parser/evaluator with JavaCC. In other words you have to implement one version, not both.
However, your Java or JavaCC version must behave as follows:

1. Repeat
    1. Invite the user to enter an expression from the console
    2. Parse and compute the result of the entered expression according to the defined assignements
    3. Provide a message as clear as possible in case of any error detection
    4. Display the result on the console
2. until 'exit' is entered

Here is an some example of execution:
```
   > java Calculator
   Type your expression: (4+5)*3/4
   Result: 6.75
   Type your expression: (10*3.141592)
   Result: 31.41592
   Type your expression: -4
   Result: -4.0
   Type your expression: (-4)
   Result: -4.0
   Type your expression: -(-4)
   Result: 4.0
   Type your expression: let x=6
   Result: 6.0
   Type your expression: let y=-7
   Result: -7.0
   Type your expression: 3*x+7
   Result: 25.0
   Type your expression: let x=14
   Result: 14.0
   Type your expression: x+y
   Result: 7.0
   Type your expression: let z=2.5
   Result: 2.5
   Type your expression: let z=3*x+y
   Result: 35.0
   Type your expression: let k=-5
   Result: -5.0
   Type your expression: let k=-k
   Result: 5.0
   Type your expression: exit
```
In any other inputs an error message must be displayed. Your errors message should explain the cause of the error in detail.  Here are some examples of error you should detect (this list is not exhaustive):
```
   Type your expression: 5+6$
   Illegal Token: '$'
   Type your expression: let x+5
   Last read token: '+' but '=' was expected
   Type your expression: 4+*5
   Last read token: '*' but '(' or Identifier or Number is expected
```
## Deliverables
The deliverables of this project consist in the implementation of a recursive descent parser/evaluator in Java or a top-down parser/evaluator in JavaCC in both cases the behavior should follow the above explanations. If you choose the Java version you must use the provided lexer and the token definition.  If your choice is for the JavaCC version take a look at these files, they might give you some ideas. Your are not allowed neither to change the grammar, nor to modify the lexer.\
You must use GitLab at the BFH (gitlab.ti.bfh.ch). Create a project with the name *calc_yourabbreviation1_yourabbreviation2* (for example: *calc_gautx1_bobsp2*) and give me the same rights as you. An invitation email will be automatically sent to me.\
Note that the error detection is an important aspect of this project.

## Organization
* This project is obligatory for every student who attends the course
* The project is realized by small groups of maximum two persons
* You must use *Gitlab* and *Java* or *JavaCC*
* Let me remind you that such a project is not a collaborative work. Each team must provide its own solution
* Deadline for the deliverable is **19th June 2019 (17:00)**
