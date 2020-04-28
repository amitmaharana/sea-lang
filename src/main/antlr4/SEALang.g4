grammar SEALang;

/** Starting of our program.*/
program : block;

/** List of either declaration or commands.*/
block : (declaration | command)+ ;

/** declaration: User can declare Int, String*/
declaration : TYPE VAR SEMICOLON ;

/** command: User can use multiple and nested If-else, loops, assignment operator, and display data types*/
command : (if_block |
          while_block |
          for_block |
          range_block |
          assign_block |
          show)+;

/** expression: This will perform airthmatic operations on numbers or variables.
*This will also evaluate ternary_block, and nested expressions.
*/
expression: OPB expression CPB #parExpression
               | left = expression op = MULTIPLY right = expression #multiplyExpression
               | left = expression op = DIVIDE right = expression #divideExpression
               | left = expression op = PLUS right = expression #plusExpression
               | left = expression op = MINUS right = expression #minusExpression
               | INT #intExpression
               | VAR #variableExpression
               | VAR OSB (INT | VAR) CSB #intArrayExpression;

/*condition: User can use NOT, nested conditions, comparators, and chaining of multiple conditions*/
condition: OPB condition CPB#parCondition
               | NOT condition #notCondition
               | left = expression op = comparator right = expression #comparatorCondition
               | left = condition op = multi_condition right = condition #multiConditionCondition
               | left = string_expression op = EQUALS right = string_expression #equalsStringCondition
               | BOOLEAN #boolCondition
               | VAR #variableCondition
               | VAR OSB (INT | VAR) CSB #boolArrayCondition;

comparator : EQUAL | NOT_EQUAL | LESSER_THAN | GREATER_THAN | LESSER_THAN_EQUAL | GREATER_THAN_EQUAL ;
multi_condition : AND | OR;

/*
condition_block is for ifelse, looping, ternary statements.
*/
condition_block : OPB condition CPB | condition;


/** if_block: User can use either only if, if-else, if-elseif-else, or nested if-else*/
if_block :
    IF OPB condition_block CPB
            OCB
                block
            CCB
    (else_statement)? ;
else_statement: ELSE
            OCB
                block
            CCB;

/** while_block: User can use nested while loops with conditions and execute a block.*/
while_block :
    WHILE condition_block
    OCB
        block
    CCB ;

/** for_block: User can use nested for loops and execute a block.*/
for_block :
    FOR OPB for_assign SEMICOLON condition_block SEMICOLON for_updation CPB
    OCB
        block
    CCB ;
for_assign : ((TYPE VAR ASSIGN expression)| (VAR ASSIGN expression) | );
for_updation : ((VAR ASSIGN expression) | (VAR INC) | (VAR DEC) |);

/** range_block: User can use nested for range loops and execute a block.*/
range_block: range_dec_block | range_inc_block;
range_inc_block :
    FOR VAR INC IN RANGE OPB range_from COMMA range_inc_to CPB
    OCB
        block
    CCB  ;
range_dec_block :
      FOR VAR DEC IN RANGE OPB range_from COMMA range_dec_to CPB
      OCB
          block
      CCB  ;
range_from : (INT | VAR | expression);
range_inc_to : (INT | VAR | expression);
range_dec_to : (INT | VAR | expression);

/* String operations */
string_expression:  left = string_expression DOT CONCAT OPB right = string_expression CPB #concatOperation
    | string_expression DOT LENGTH OPB CPB #lengthOperation
    | string_expression DOT SPLIT OPB STRING CPB #splitOperation
    | string_expression DOT SUBSTRING OPB expression CPB #substringOperation
    | string_expression DOT SUBSTRING OPB expression COMMA expression CPB #substringDoubleOperation
    | INTEGER DOT TOSTRING OPB expression CPB  #integerToStringOperation
    | BOOL DOT TOSTRING OPB condition CPB #booleanToStringOperation
    | STRING #stringOperation
    | VAR #varOperation
    | VAR OSB (INT | VAR) CSB #stringArrayOperation;

/* Arrays */
array : int_array | bool_array | string_array;
int_array : OSB (INT (COMMA INT)* |) CSB;
bool_array : OSB (BOOLEAN (COMMA BOOLEAN)* |) CSB;
string_array : OSB (STRING (COMMA STRING)* |) CSB;

/* Arrays Properties */
array_properties: VAR DOT LENGTH #arrayLengthProperty;

/** ternary_block: User can use ternary operator and evaluate expressions.*/
ternary_block : condition_block QUESTION ternary_true_block COLON ternary_false_block ;
ternary_true_block : (expression | condition);
ternary_false_block : (expression | condition);

/** assign_block: User can use this to assign expressions or strings to a variable.*/
assign_block : VAR ASSIGN (condition | expression | ternary_block | string_expression | array | array_properties) SEMICOLON ;

/** show: User can use this to display a variable.*/
show : 'show' (VAR | INT | BOOLEAN | STRING) SEMICOLON;

INTEGER : 'Integer';
BOOL : 'Boolean';
TYPE : 'Int' | 'Bool' | 'String' | 'Int[]' | 'Bool[]' | 'String[]';
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
OSB : '[' ;
CSB : ']' ;
SEMICOLON : ';' ;
COLON : ':' ;
COMMA : ',' ;
QUESTION : '?' ;
DOT : '.';
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
FOR : 'for' ;
RANGE: 'range' ;
IN : 'in' ;
LENGTH : 'length';
CONCAT : 'concat';
EQUALS : 'equals';
TOSTRING : 'toString';
SPLIT : 'split';
SUBSTRING : 'substring';
VAR : [a-z]+ ;
INT : [0-9]+ ;
STRING : '"' (~["\r\n] | '""')* '"' ;
BOOLEAN : TRUE | FALSE ;
TRUE : 'True' ;
FALSE : 'False' ;
COMMENT : DOUBLE_SLASH ~[\r\n]* -> skip ;
DOUBLE_SLASH : '//' ;
WS : [ \n\t\r]+ -> skip ;