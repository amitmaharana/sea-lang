grammar SEALang;

program : block;

block : (declaration | command)+ ;

declaration : let SEMICOLON ;
let : TYPE VAR (ASSIGN INT | ASSIGN BOOLEAN | ASSIGN STRING | ASSIGN expression)? ;
command : (expression SEMICOLON | statement SEMICOLON| if_block | while_block | for_block | range_block | VAR ASSIGN expression SEMICOLON)+;

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

while_block :
    WHILE OPB condition CPB
    OCB
        block
    CCB ;

for_block :
    FOR OPB ((TYPE VAR ASSIGN INT)| (VAR ASSIGN INT) | ) SEMICOLON condition SEMICOLON ((VAR (INC | DEC | ASSIGN expression)) | ) CPB
    OCB
        block
    CCB ;

range_block :
    FOR VAR IN RANGE OPB (INT | VAR | expression) COMMA (INT | VAR | expression) CPB
    OCB
        block
    CCB  ;

ternary_block :
    (TYPE VAR | VAR) ASSIGN condition QUESTION expression COLON expression SEMICOLON ;


condition : ( (NOT)? (((expression | VAR) (EQUAL | NOT_EQUAL | LESSER_THAN | GREATER_THAN | LESSER_THAN_EQUAL | GREATER_THAN_EQUAL) (expression | VAR) ) | BOOLEAN)) | condition (AND | OR) condition;


expression : term expression_com;
expression_com : (MINUS | PLUS) term expression_com | ;
term : util term_com ;
term_com : (MULTIPLY | DIVIDE) util term_com | ;
util : (INT | OPB expression CPB) ;

statement : show ;

show : 'show' (VAR | INT | BOOLEAN | STRING | CHAR) ;

TYPE : 'Int' | 'Boolean' | 'String' | 'Float' | 'Char';

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

TRUE : 'true' ;
FALSE : 'false' ;

IF : 'if' ;
ELSE : 'else' ;

WHILE : 'while' ;
FOR : 'for' ;

RANGE: 'range' ;
IN : 'in' ;

VAR : [a-zA-Z]+ ;
INT : [0-9]+ ;
STRING : '"' (~["\r\n] | '""')* '"' ;
BOOLEAN : 'True' | 'False' ;
CHAR : '[a-zA-Z]' ;
FLOAT : [0-9]+ '.' [0-9]+ | '.' [0-9]+ ;

WS : [ \n\t\r]+ -> skip;