grammar Parser;

program:	(pclass ';')+ ;

pclass:		KWCLASS TID (KWINHERITS TID)? '{' (feature';')* '}';

feature:	 OID '('  ( formal (',' formal)* )?  ')' ':' TID '{' expr '}'
		|{System.out.println("4");} OID ':' TID ( '<-' expr )?;

formal :	 OID ':' TID ;

expr1	:
		 KWIF expr KWTHEN expr KWELSE expr KWFI 
		| KWWHILE expr KWLOOP expr KWPOOL 
		| KWLET OID ':' TID  ('<-' expr)?  (',' OID ':' TID ( '<-' expr )?)* KWIN expr 
		| KWCASE expr KWOF (OID ':' TID '=>' expr ';' )+	KWESAC 
		| '{' (expr';')+ '}' 
		| '(' expr ')'  
		| OID '(' (expr (','expr)*)? ')' 
		| OID '<-' expr  
		| KWNEW TID 
		| OID 
		| ('+'|'-')?INT  
		| string 
		| KWTRUE  
		| KWFALSE  
		;

expr2	:
		expr1 expr2p
		;

expr2p		:
		('@' TID)? '.' OID '(' ( expr(','expr)*)? ')' expr2p  
		|
		;


expr3	:
		expr2
		| '~' expr3  
		| KWISVOID expr3  
		;

expr4	:
		expr3 expr4p
		;

expr4p		:
		'/' expr3 expr4p | '*' expr3 expr4p 
		|
		;


expr5	:
		expr4 expr5p
		;

expr5p 		:
	 	'+' expr4 expr5p | '-' expr4 expr5p
		|
		;


expr6	:
		expr5 expr6p
		;

expr6p		:
		'<' expr5 expr6p | '<=' expr5 expr6p | '=' expr5 expr6p 
		|;

expr	:
		expr6
		| KWNOT expr
		;

comment:	LINECOMMENT	|MULTICOMMENT;	 
string:		STR;


KWCLASS:	[cC][lL][aA][sS][sS];
KWELSE:	 	[eE][lL][sS][eE];
KWFI:	 	[fF][iI];
KWIF:	 	[iI][fF];
KWIN:	 	[iI][nN];
KWINHERITS: 	[iI][nN][hH][eE][rR][iI][tT][sS];
KWISVOID: 	[iI][sS][vV][oO][iI][dD];
KWLET:	 	[lL][eE][tT];
KWLOOP:	 	[lL][oO][oO][pP];
KWPOOL:	 	[pP][oO][oO][lL];
KWTHEN:	 	[tT][hH][eE][nN];
KWWHILE: 	[wW][hH][iI][lL][eE];
KWCASE:	 	[cC][aA][sS][eE];
KWESAC:	 	[eE][sS][aA][cC];
KWNEW:	 	[nN][eE][wW];
KWOF:	 	[oO][fF];
KWNOT:	 	[nN][oO][tT];
KWTRUE:	 	 [t][rR][uU][eE];
KWFALSE: 	 [f][aA][lL][sS][eE];

TID	:	[A-Z][_0-9A-Za-z]* ;
OID	:	[a-z][_0-9A-Za-z]* ;
INT	:	[0-9]+ ;
WS	:	[ \t\r\n\f\v]+ -> skip;

OPP	:	([=][>])	|([<][=|\-])	|[\[\]\{\}\(\)\.\*\+\-/@~<>=;,:];

STR	:	'"' ([\\].|	[\\][\r][\n]|	~('\n'|'\r')	)*?'"';

LINECOMMENT:	'--' .*? ('\r\n'|'\n') -> skip;
MULTICOMMENT:	'(*' .*? '*)' -> skip;
