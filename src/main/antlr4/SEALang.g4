grammar SEALang;

program : block;

block : (declaration | command)+ ;

declaration : let SEMICOLON ;
let : TYPE VAR (ASSIGN INT | ASSIGN BOOLEAN | ASSIGN STRING | ASSIGN expression | ASSIGN ternary_block)? ;
command : (statement SEMICOLON| if_block | while_block | for_block | range_block | VAR ASSIGN (expression | ternary_block) SEMICOLON)+;

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

ternary_block : condition QUESTION expression COLON expression ;

condition :  (NOT)? OPB condition CPB | BOOLEAN | expression comparator expression | condition multi_condition condition;
comparator : EQUAL | NOT_EQUAL | LESSER_THAN | GREATER_THAN | LESSER_THAN_EQUAL | GREATER_THAN_EQUAL ;
multi_condition : AND | OR ;

expression : term expression_com;
expression_com : (MINUS | PLUS) term expression_com | ;
term : util term_com ;
term_com : (MULTIPLY | DIVIDE) util term_com | ;
util : (VAR | INT | OPB expression CPB | BOOLEAN) ;

statement : show ;

show : 'show' (VAR | INT | BOOLEAN | STRING) ;

TYPE : 'Int' | 'Boolean' | 'String' ;

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

WS : [ \n\t\r]+ -> skip;