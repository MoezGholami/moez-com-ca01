grammar Simple;

prog:	token+	;

token:
	keyword
	|	integer
	;

integer:	INT
	{System.out.println($INT.text+":integer");}
	;

keyword:	class	|else	|fi	|if	|in	|inherits	|isvoid	|let	|loop	|pool
		|then	|while	|case	|esac	|new	|of	|not	|true	|false;

class		:KW_CLASS		{System.out.println($KW_CLASS.text		+":keyword");};
else		:KW_ELSE		{System.out.println($KW_ELSE.text		+":keyword");};
fi		:KW_FI			{System.out.println($KW_FI.text			+":keyword");};
if		:KW_IF			{System.out.println($KW_IF.text			+":keyword");};
in		:KW_IN			{System.out.println($KW_IN.text			+":keyword");};
inherits	:KW_INHERITS		{System.out.println($KW_INHERITS.text		+":keyword");};
isvoid		:KW_ISVOID		{System.out.println($KW_ISVOID.text		+":keyword");};
let		:KW_LET			{System.out.println($KW_LET.text		+":keyword");};
loop		:KW_LOOP		{System.out.println($KW_LOOP.text		+":keyword");};
pool		:KW_POOL		{System.out.println($KW_POOL.text		+":keyword");};
then		:KW_THEN		{System.out.println($KW_THEN.text		+":keyword");};
while		:KW_WHILE		{System.out.println($KW_WHILE.text		+":keyword");};
case		:KW_CASE		{System.out.println($KW_CASE.text		+":keyword");};
esac		:KW_ESAC		{System.out.println($KW_ESAC.text		+":keyword");};
new		:KW_NEW			{System.out.println($KW_NEW.text		+":keyword");};
of		:KW_OF			{System.out.println($KW_OF.text			+":keyword");};
not		:KW_NOT			{System.out.println($KW_NOT.text		+":keyword");};
true		:KW_TRUE		{System.out.println($KW_TRUE.text		+":keyword");};
false		:KW_FALSE		{System.out.println($KW_FALSE.text		+":keyword");};

ID  :   [a-zA-Z_]+ ;
INT :   [0-9]+ ;
WS  :   [ \t\r\n]+ -> skip ;

KW_CLASS	:	[(c|C)(l|L)(a|A)(s|S)(s|S)];
KW_ELSE		: 	[(e|E)(l|L)(s|S)(e|E)];
KW_FI		: 	[(f|F)(i|I)];
KW_IF		: 	[(i|I)(f|F)];
KW_IN		: 	[(i|I)(n|N)];
KW_INHERITS	: 	[(i|I)(n|N)(h|H)(e|E)(r|R)(i|I)(t|T)(s|S)];
KW_ISVOID	: 	[(i|I)(s|S)(v|V)(o|O)(i|I)(d|D)];
KW_LET		: 	[(l|L)(e|E)(t|T)];
KW_LOOP		: 	[(l|L)(o|O)(o|O)(p|P)];
KW_POOL		: 	[(p|P)(o|O)(o|O)(l|L)];
KW_THEN		: 	[(t|T)(h|H)(e|E)(n|N)];
KW_WHILE	: 	[(w|W)(h|H)(i|I)(l|L)(e|E)];
KW_CASE		: 	[(c|C)(a|A)(s|S)(e|E)];
KW_ESAC		: 	[(e|E)(s|S)(a|A)(c|C)];
KW_NEW		: 	[(n|N)(e|E)(w|W)];
KW_OF		: 	[(o|O)(f|F)];
KW_NOT		: 	[(n|N)(o|O)(t|T)];
KW_TRUE		: 	[(t  )(r|R)(u|U)(e|E)];
KW_FALSE	: 	[(f  )(a|A)(l|L)(s|S)(e|E)];
