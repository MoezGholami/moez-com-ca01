grammar Parser;

program:	{System.out.println("1");} (pclass ';')+ ;

pclass:		{System.out.println("2");} KWCLASS TID (KWINHERITS TID)? '{' (feature';')* '}';

feature:	{System.out.println("3");} OID '('  ( formal (',' formal)* )?  ')' ':' TID '{' expr '}'
		|{System.out.println("4");} OID ':' TID ( '<-' expr )?;

formal :	{System.out.println("5");} OID ':' TID ;

expr1	:
		{System.out.println("9");} KWIF expr KWTHEN expr KWELSE expr KWFI 
		|{System.out.println("10");} KWWHILE expr KWLOOP expr KWPOOL 
		|{System.out.println("12");} KWLET OID ':' TID  ('<-' expr)?  (',' OID ':' TID ( '<-' expr )?)* KWIN expr 
		|{System.out.println("13");} KWCASE expr KWOF (OID ':' TID '=>' expr ';' )+	KWESAC 
		|{System.out.println("11");} '{' (expr';')+ '}' 
		|{System.out.println("25");} '(' expr ')'  
		|{System.out.println("8");} OID '(' (expr (','expr)*)? ')' 
		|{System.out.println("6");} OID '<-' expr  
		|{System.out.println("14");} KWNEW TID 
		|{System.out.println("26");} OID 
		|{System.out.println("27");} ('+'|'-')?INT  
		|{System.out.println("28");} string 
		|{System.out.println("29");} KWTRUE  
		|{System.out.println("30");}  KWFALSE  
		;

expr2	:
		expr1 expr2p
		;

expr2p		:
		{System.out.println("7");} ('@' TID)? '.' OID '(' ( expr(','expr)*)? ')' expr2p  
		|
		;


expr3	:
		expr2
		|{System.out.println("20");} '~' expr3  
		|{System.out.println("15");} KWISVOID expr3  
		;

expr4	:
		expr3 expr4p
		;

expr4p		:
		{System.out.println("19");} '/' expr3 expr4p | {System.out.println("18");} '*' expr3 expr4p 
		|
		;


expr5	:
		expr4 expr5p
		;

expr5p 		:
	 	{System.out.println("16");}  '+' expr4 expr5p | {System.out.println("17");} '-' expr4 expr5p
		|
		;


expr6	:
		expr5 expr6p
		;

expr6p		:
		{System.out.println("21");} '<' expr5 expr6p | {System.out.println("22");} '<=' expr5 expr6p | {System.out.println("23");} '=' expr5 expr6p 
		|;

expr	:
		expr6
		| {System.out.println("24");} KWNOT expr
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
