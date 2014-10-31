grammar Lexer;

prog:	token*;

token:	keywords	|identifier	|integer	|operators	|string |comment;

keywords:	vclass	|velse	|vfi	|vif	|vin	|vinherits	|visvoid	|vlet	|vloop	|vpool
		|vthen	|vwhile	|vcase	|vesac	|vnew	|vof	|vnot	|vtrue	|vfalse;

integer:	INT		{System.out.println($INT.text+":integer");};

identifier:	type_identifier	|o_identifier;

vclass		:KWCLASS		{System.out.println($KWCLASS.text	+" : keyword");};
velse		:KWELSE			{System.out.println($KWELSE.text	+" : keyword");};
vfi		:KWFI			{System.out.println($KWFI.text		+" : keyword");};
vif		:KWIF			{System.out.println($KWIF.text		+" : keyword");};
vin		:KWIN			{System.out.println($KWIN.text		+" : keyword");};
vinherits	:KWINHERITS		{System.out.println($KWINHERITS.text	+" : keyword");};
visvoid		:KWISVOID		{System.out.println($KWISVOID.text	+" : keyword");};
vlet		:KWLET			{System.out.println($KWLET.text		+" : keyword");};
vloop		:KWLOOP			{System.out.println($KWLOOP.text	+" : keyword");};
vpool		:KWPOOL			{System.out.println($KWPOOL.text	+" : keyword");};
vthen		:KWTHEN			{System.out.println($KWTHEN.text	+" : keyword");};
vwhile		:KWWHILE		{System.out.println($KWWHILE.text	+" : keyword");};
vcase		:KWCASE			{System.out.println($KWCASE.text	+" : keyword");};
vesac		:KWESAC			{System.out.println($KWESAC.text	+" : keyword");};
vnew		:KWNEW			{System.out.println($KWNEW.text		+" : keyword");};
vof		:KWOF			{System.out.println($KWOF.text		+" : keyword");};
vnot		:KWNOT			{System.out.println($KWNOT.text		+" : keyword");};
vtrue		:KWTRUE			{System.out.println($KWTRUE.text	+" : keyword");};
vfalse		:KWFALSE		{System.out.println($KWFALSE.text	+" : keyword");};

type_identifier:	TID		{System.out.println($TID.text		+" : id");};
o_identifier:		OID		{System.out.println($OID.text		+" : id");};

operators:		OPP		{System.out.println($OPP.text		+" : "+$OPP.text);};

string:			STR		{System.out.println($STR.text		+" : string");};

comment:	LINECOMMENT	|multicomment ;	 

multicomment:		'(*' .*? '*)';

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
