grammar Parser;

program:	(pclass)+ ;

pclass:		KWCLASS TID (KWINHERITS TID)? '{' (feature';')* '}';

feature:	OID '('  ( formal (',' formal)* )?  ')' ':' TID '{' expr '}'
		|OID ':' TID ( '<-' expr )?;

formal :	OID ':' TID ;

expr   :
		expr ('@' TID)? '.' OID '(' ( expr(','expr)*)? ')' // TODO : Check precedence of @ and dot.
		|OID '(' (expr (','expr)*)? ')'
		|KWIF expr KWTHEN expr KWELSE expr KWFI
		|KWWHILE expr KWLOOP expr KWPOOL
		|KWLET OID ':' TID  ('<-' expr)?  (',' OID ':' TID ( '<-' expr )?)* KWIN expr
		|KWCASE expr KWOF (OID ':' TID '=>' expr ';' )+	KWESAC

		|'~'	expr
		|KWISVOID expr

		|expr ('*'|'/') expr
		|expr ('+'|'-') expr
		|expr ('<'|'<='|'=') expr
		|KWNOT expr
		|OID '<-' expr 

		|KWNEW TID

		|OID
		|INT
		|STR
		|KWTRUE
		|KWFALSE 	
		|'{' (expr';')+ '}'
		|'(' expr ')'  
		;

comment:	(LINECOMMENT	|multicomment) -> skip;	 

multicomment:	'(*' .*? '*)';

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
INT	:	('-')?[0-9]+ ;
WS	:	[ \t\r\n\f\v]+ -> skip;

OPP	:	([=][>])	|([<][=|\-])	|[\[\]\{\}\(\)\.\*\+\-/@~<>=;,:];

STR	:	'"' ([\\].|	~('\n'|'\r')	)*?'"';

LINECOMMENT:	'--' .*? ('\r\n'|'\n');
