grammar Simple;

prog:	token+	;

token:
	keyword
	|	integer
	;

integer:	INT
	{System.out.println($INT.text+":integer");}
	;

keyword:	classv;

classv		:CLASS		{System.out.println($CLASS.text		+":keyword");};

ID  :   [a-zA-Z_]+ ;
INT :   [0-9]+ ;
WS  :   [ \t\r\n]+ -> skip ;

CLASS	:	'class';
