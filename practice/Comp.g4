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

class		:CLASS		{System.out.println($CLASS.text		+":keyword");};
else		:ELSE		{System.out.println($ELSE.text		+":keyword");};
fi		:FI		{System.out.println($FI.text		+":keyword");};
if		:IF		{System.out.println($IF.text		+":keyword");};
in		:IN		{System.out.println($IN.text		+":keyword");};
inherits	:INHERITS	{System.out.println($INHERITS.text	+":keyword");};
isvoid		:ISVOID		{System.out.println($ISVOID.text	+":keyword");};
let		:LET		{System.out.println($LET.text		+":keyword");};
loop		:LOOP		{System.out.println($LOOP.text		+":keyword");};
pool		:POOL		{System.out.println($POOL.text		+":keyword");};
then		:THEN		{System.out.println($THEN.text		+":keyword");};
while		:WHILE		{System.out.println($WHILE.text		+":keyword");};
case		:CASE		{System.out.println($CASE.text		+":keyword");};
esac		:ESAC		{System.out.println($ESAC.text		+":keyword");};
new		:NEW		{System.out.println($NEW.text		+":keyword");};
of		:OF		{System.out.println($OF.text		+":keyword");};
not		:NOT		{System.out.println($NOT.text		+":keyword");};
true		:TRUE		{System.out.println($TRUE.text		+":keyword");};
false		:FALSE		{System.out.println($FALSE.text		+":keyword");};

ID  :   [a-zA-Z_]+ ;
INT :   [0-9]+ ;
WS  :   [ \t\r\n]+ -> skip ;

CLASS	:	[(c|C)(l|L)(a|A)(s|S)(s|S)];
ELSE	: 	[(e|E)(l|L)(s|S)(e|E)];
FI	: 	[(f|F)(i|I)];
IF	: 	[(i|I)(f|F)];
IN	: 	[(i|I)(n|N)];
INHERITS: 	[(i|I)(n|N)(h|H)(e|E)(r|R)(i|I)(t|T)(s|S)];
ISVOID	: 	[(i|I)(s|S)(v|V)(o|O)(i|I)(d|D)];
LET	: 	[(l|L)(e|E)(t|T)];
LOOP	: 	[(l|L)(o|O)(o|O)(p|P)];
POOL	: 	[(p|P)(o|O)(o|O)(l|L)];
THEN	: 	[(t|T)(h|H)(e|E)(n|N)];
WHILE	: 	[(w|W)(h|H)(i|I)(l|L)(e|E)];
CASE	: 	[(c|C)(a|A)(s|S)(e|E)];
ESAC	: 	[(e|E)(s|S)(a|A)(c|C)];
NEW	: 	[(n|N)(e|E)(w|W)];
OF	: 	[(o|O)(f|F)];
NOT	: 	[(n|N)(o|O)(t|T)];
TRUE	: 	[(t  )(r|R)(u|U)(e|E)];
FALSE	: 	[(f  )(a|A)(l|L)(s|S)(e|E)];
