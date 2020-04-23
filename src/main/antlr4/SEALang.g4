grammar SEALang;

/** Starting of our program.*/
program : block;

/** List of either declaration or commands.*/
block : (declaration | command)+ ;

/** declaration: User can declare Int, String*/
declaration : TYPE VAR SEMICOLON ;

/*condition: User can use NOT, nested conditions, comparators, and chaining of multiple conditions*/
condition: OPB condition CPB#parCondition
               | NOT condition #notCondition
               | left = expression op = comparator right = expression #comparatorCondition
               | left = condition op = multi_condition right = condition #multiConditionCondition
               | BOOLEAN #boolCondition
               | VAR #variableCondition;

comparator : EQUAL | NOT_EQUAL | LESSER_THAN | GREATER_THAN | LESSER_THAN_EQUAL | GREATER_THAN_EQUAL ;
multi_condition : AND | OR;


/** expression: This will perform airthmatic operations on numbers or variables.
*This will also evaluate ternary_block, and nested expressions.
*/
expression : term expression_com;
expression_com : (MINUS | PLUS) term expression_com | ;
term : util term_com ;
term_com : (MULTIPLY | DIVIDE) util term_com | ;
util : (VAR | INT | OPB  ternary_block  CPB | OPB expression CPB) ;

/** command: User can use multiple and nested If-else, loops, assignment operator, and display data types*/
command : (if_block |
          while_block |
          for_block |
          range_block |
          assign_block |
          show)+;

/** if_block: User can use either only if, if-else, if-elseif-else, or nested if-else*/
if_block :
    IF OPB condition CPB
    OCB
        block
    CCB
    (ELSE IF OPB condition CPB
    OCB
        block
    CCB)*
    (ELSE
    OCB
        block
    CCB)? ;

/** while_block: User can use nested while loops with conditions and execute a block.*/
while_block :
    WHILE OPB condition CPB
    OCB
        block
    CCB ;

/** for_block: User can use nested for loops and execute a block.*/
for_block :
    FOR OPB ((TYPE VAR ASSIGN INT)| (VAR ASSIGN INT) | ) SEMICOLON condition SEMICOLON ((VAR (INC | DEC | ASSIGN expression)) | ) CPB
    OCB
        block
    CCB ;

/** range_block: User can use nested for range loops and execute a block.*/
range_block :
    FOR VAR IN RANGE OPB (INT | VAR | expression) COMMA (INT | VAR | expression) CPB
    OCB
        block
    CCB  ;

/** assign_block: User can use this to assign expressions or strings to a variable.*/
assign_block : VAR ASSIGN (condition | STRING | expression) SEMICOLON ;

/** show: User can use this to display a variable.*/
show : 'show' (VAR | INT | BOOLEAN | STRING) SEMICOLON;

/** ternary_block: User can use ternary operator and evaluate expressions.*/
ternary_block : condition QUESTION expression COLON expression ;

TYPE : 'Int' | 'Boolean' |'String';
PLUS : '+' ;
MINUS : '-' ;
MULTIPLY : '*' ;
DIVIDE : '/' ;
ASSIGN : '=' ;
EQUAL : '==' ;
NOT : '!' ;
NOT_EQUAL : '!=' ;
LESSER_THAN : '<' ;
GREATER_THAN : '>' ;
LESSER_THAN_EQUAL : '<=' ;
GREATER_THAN_EQUAL : '>=' ;
INC : '++' ;
DEC : '--' ;
AND : '&&' ;
OR : '||' ;
OPB : '(' ;
CPB : ')' ;
OCB : '{' ;
CCB : '}' ;
SEMICOLON : ';' ;
COLON : ':' ;
COMMA : ',' ;
QUESTION : '?' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
FOR : 'for' ;
RANGE: 'range' ;
IN : 'in' ;
VAR : [a-z]+ ;
INT : [0-9]+ ;
STRING : '"' (~["\r\n] | '""')* '"' ;
BOOLEAN : TRUE | FALSE ;
TRUE : 'True' ;
FALSE : 'False' ;
COMMENT : DOUBLE_SLASH ~[\r\n]* -> skip ;
DOUBLE_SLASH : '//' ;
WS : [ \n\t\r]+ -> skip ;