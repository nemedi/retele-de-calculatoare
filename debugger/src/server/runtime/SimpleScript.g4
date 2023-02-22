grammar SimpleScript;

program
 : statement* EOF
 ;

statement
    : assignment
    | decision
    | loop
    | block
    ;

block
    : BEGIN statement* END
    ;

assignment
 : VARIABLE ASSIGN addition
 ;

decision
    : IF condition THEN statement (ELSE statement)?
    ;

loop
    : WHILE condition DO statement
    ;

condition
    : disjunction
    | NOT disjunction
    | LB disjunction RB
    ;

disjunction
    : conjunction (OR conjunction)*
    ;

conjunction
    : comparison (AND comparison)*
    ;

comparison
    : addition Relation addition
    ;

addition
    : subtraction (PLUS subtraction)*
    ;

subtraction
    : multiplication (MINUS multiplication)*
    ;

multiplication
    : division (MUL division)*
    ;

division
    : value (DIV value)*
    ;

value
    : (NUMBER | VARIABLE)
    | LB addition RB
    ;

Relation
    : LT | LE | EQ | NE | GE | GT
    ;
LT : '<';
LE : '<=';
EQ : '==';
NE : '<>';
GE : '>=';
GT : '>';
IF : 'if';
THEN : 'then';
ELSE : 'else';
WHILE : 'while';
DO : 'do';
BEGIN : 'begin';
END : 'end';
OR : 'or';
AND : 'and';
NOT : 'not';
ASSIGN : '=';
LB : '(';
RB : ')';
PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
VARIABLE : [a-zA-Z]+;
NUMBER : [0-9]+ ('.' [0-9]+)?;
WS : [ \t\r\n] -> skip;

