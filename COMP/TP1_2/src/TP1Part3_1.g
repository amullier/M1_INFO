grammar TP1Part3_1;

options {
    //Grammaire LL(1)
    k=1;
    //Options pour produire l'AST
    output=AST;
    ASTLabelType=CommonTree;
}

//Analyse syntaxique
doc:
	bloc+
;

bloc:
	ID version '.' -> ^(ID version)
;

version:
	ID '[' liste_objet ']' suite_liste -> ^(ID liste_objet) ^(ID suite_liste?)
;

list_versions:
	'[' liste_objet ']' suite_liste -> liste_objet suite_liste?
;

suite_liste:
	',' list_versions -> list_versions
	|

;

liste_objet:
	  objet suite_objet -> objet suite_objet?
;

objet: 
	ID TEXT -> ^(ID TEXT)
;

suite_objet:
	';' liste_objet -> liste_objet
	|
;


   


//Analyse lexicale
ID  :   '<' ('a'..'z'|'A'..'Z'|'0'..'9' | '-')+ '>' ;
TEXT :'"' ('a'..'z'|'A'..'Z'|'0'..'9' | ' ' | '-'| '&')+ '"' ;
WS : (' '|'\t'|'\n'|'\r')+ { skip(); } ;
