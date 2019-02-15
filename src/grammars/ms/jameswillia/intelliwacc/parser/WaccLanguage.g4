/** A simple language for use with this wacc plugin.
 *  It's C-like but without semicolons. Symbol resolution semantics are
 *  C-like: resolve symbol in current scope. If not in this scope, ask
 *  enclosing scope to resolve (recurse up tree until no more scopes or found).
 *  Forward refs allowed for functions but not variables. Globals must
 *  appear first syntactically.
 *
 *  Generate the parser via "mvn compile" from root dir of project.
 */
//grammar WaccLanguage;

/** The start rule must be whatever you would normally use, such as script
 *  or compilationUnit, etc...
 */
grammar WaccLanguage;

prog: BEGIN func* stat END EOF;

func: type IDENT LPAREN paramList? RPAREN IS stat END ;

functionCall: CALL IDENT LPAREN argList? RPAREN ;

paramList: param ( COMMA param )* ;

param: type IDENT ;

stat: skipStat
    | assignStat
    | declarationStat
    | freeStat
    | returnStat
    | exitStat
    | printStat
    | printlnStat
    | ifStat
    | whileStat
    | beginStat
    | readStat
    | stat SEMICOL stat ;

skipStat: SKIP_ ;
assignStat: lhs EQUALS rhs ;
declarationStat: type IDENT EQUALS rhs ;
readStat: READ lhs ;
freeStat: FREE expr ;
returnStat: RETURN expr ;
exitStat: EXIT expr ;
printStat: PRINT expr ;
printlnStat: PRINTLN expr ;
ifStat: IF expr THEN stat ELSE stat FI ;
whileStat: WHILE expr DO stat DONE ;
beginStat: BEGIN stat END ;

lhs: identifier
   | arrayElem
   | pairElem ;

rhs: expr
   | arrayLiter
   | newpairDeclaration
   | pairElem
   | functionCall ;

argList: expr (COMMA expr)* ;

pairElem: FST expr
         | SND expr ;

type: baseType
    | arrayType
    | pairType ;

baseType: INT | BOOL | CHAR | STRING ;

arrayType: baseType LSQUARED RSQUARED
         | arrayType LSQUARED RSQUARED
         | pairType LSQUARED RSQUARED ;

newpairDeclaration: NEWPAIR LPAREN expr COMMA expr RPAREN ;

pairType: PAIR LPAREN pairElemType COMMA pairElemType RPAREN ;

pairElemType: baseType
            | arrayType
            | PAIR ;

unaryOperator: uop=(BANG|MINUS|LEN|ORD|CHR) expr ;
parenExpression: LPAREN expr RPAREN ;

expr: primary
    | arrayElem
    | unaryOperator
    | expr bop=(MUL|MOD|DIV) expr
    | expr bop=(PLUS|MINUS) expr
    | expr bop=(GR|GRE|LS|LSE) expr
    | expr bop=(EQ|NEQ) expr
    | expr bop=AND expr
    | expr bop=OR expr
    | parenExpression ;

primary: literal
       | identifier ;

literal: sign=('+' | '-')? INT_LITER   #intLiteral
       | BOOL_LITER                    #boolLiteral
       | CHAR_LITER                    #charLiteral
       | STR_LITER                     #strLiteral
       | PAIR_LITER                    #pairLiteral ;

unOp: BANG | MINUS | LEN | ORD | CHR;

binOp: MUL | DIV | MOD | PLUS | MINUS | GR | GRE | LS | LSE | EQ
        NEQ | AND | OR ;

arrayElem: IDENT ( LSQUARED expr RSQUARED )+ ;

arrayLiter: LSQUARED ( expr ( COMMA expr )* )? RSQUARED ;

identifier: IDENT ;

// Use fragment to not include these as actual tokens
fragment DIGIT: '0'..'9' ;

// Literals
INT_LITER: DIGIT+ ;
BOOL_LITER: 'true' | 'false' ;
CHAR_LITER: '\'' CHARACTER '\'' ;
STR_LITER: '"' CHARACTER* '"' ;
PAIR_LITER: 'null' ;

// Punctuation:
LPAREN: '(' ;
RPAREN: ')' ;
LSQUARED: '[' ;
RSQUARED: ']' ;
COMMA: ',' ;
EQUALS: '=' ;
SEMICOL: ';' ;

// Base types
INT: 'int' ;
BOOL: 'bool';
CHAR: 'char';
STRING: 'string';

// Pairs:
PAIR: 'pair' ;
NEWPAIR: 'newpair' ;
FST: 'fst' ;
SND: 'snd' ;

//BASE_TYPE: 'int' | 'bool' | 'char' | 'string' ;

// Keywords:
SKIP_: 'skip' ;
BEGIN: 'begin' ;
END: 'end' ;
IS: 'is' ;
FREE: 'free' ;
RETURN: 'return' ;
EXIT: 'exit' ;
PRINT: 'print' ;
PRINTLN: 'println' ;
IF: 'if' ;
THEN: 'then' ;
ELSE: 'else' ;
FI: 'fi' ;
WHILE: 'while' ;
DO: 'do' ;
DONE: 'done' ;
CALL: 'call' ;
READ: 'read' ;

// Unary op
BANG: '!';
LEN: 'len';
ORD: 'ord';
CHR: 'chr';

// Binary operators

MUL: '*';
DIV: '/';
MOD: '%';
PLUS: '+' ;
MINUS: '-' ;
GR: '>';
GRE: '>=';
LS: '<';
LSE: '<=';
EQ: '==';
NEQ: '!=';
AND: '&&';
OR: '||';

COMMENT: '#' ~( '\r' | '\n' )* ;
MULTILINE: '/*' .*? '*/' ;

IDENT: ( '_' | 'a'..'z' | 'A'..'Z' ) ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9' )* ;
fragment ESCAPED_CHAR: '0' | 'b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\' ;
fragment CHARACTER: ~('\\' | '\'' | '"') | '\\' ESCAPED_CHAR ;

WS : [ \t\n\r]+ -> channel(HIDDEN) ;

/** "catch all" rule for any char not matche in a token rule of your
 *  grammar. Lexers in Intellij must return all tokens good and bad.
 *  There must be a token to cover all characters, which makes sense, for
 *  an IDE. The parser however should not see these bad tokens because
 *  it just confuses the issue. Hence, the hidden channel.
 */
ERRCHAR
	:	.	-> channel(HIDDEN)
	;

