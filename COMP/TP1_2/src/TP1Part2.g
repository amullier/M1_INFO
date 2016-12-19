grammar TP1Part2;

options {
    //Grammaire LL(1)
    k=1;
    //Options pour produire l'AST
    output=AST;
    ASTLabelType=CommonTree;
}

//Analyse syntaxique
doc:
	bloc
;

bloc:
	ID predicat '.' bloc2   -> ^(ID predicat) bloc2?
;

bloc2:
	bloc   -> bloc
	|  //Mot Vide
;

predicat:
	ID objet predicat2  -> ^(ID objet) predicat2?
;

predicat2:
	';' predicat  -> predicat
	|  //Mot Vide
;

objet:
	ID objet2   	-> ID objet2?
	| TEXT objet2   -> TEXT objet2?
;

objet2:
	',' objet   -> objet
	|  //Mot Vide
;


    


//Analyse lexicale
ID  :   '<' ('a'..'z'|'A'..'Z'|'0'..'9' | '-')+ '>' ;
TEXT :'"' ('a'..'z'|'A'..'Z'|'0'..'9' | ' ' | '-'| '&')+ '"' ;
WS : (' '|'\t'|'\n'|'\r')+ { skip(); } ;
