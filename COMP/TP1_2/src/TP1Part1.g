grammar TP1Part1;

options {
    //Grammaire LL(1)
    k=1;
}

//Analyse syntaxique
doc: 
	bloc
;

bloc: 
	ID predicat[$ID.text] '.' bloc2
;

bloc2:
		bloc
	|  	//mot vide
;

predicat [String attPrincipal] : 
		ID description[$attPrincipal,$ID.text] predicat2[$attPrincipal] 
;

predicat2 [String attPrincipal] :  
		';' predicat[$attPrincipal] 
	| 	//mot vide
;


description [String attPrincipal,String attSecondaire] : 
		entite[$attPrincipal,$attSecondaire] entite_bis[$attPrincipal,$attSecondaire]
;


entite [String attPrincipal,String attSecondaire] : 
	
		ID {System.out.println($attPrincipal + " " + $attSecondaire + " " +$ID.text + ".");}
	| 
		TEXT {System.out.println($attPrincipal + " " + $attSecondaire + " " +$TEXT.text + ".");} 
;

entite_bis [String attPrincipal,String attSecondaire] : 
		',' description[$attPrincipal,$attSecondaire] entite_bis[$attPrincipal,$attSecondaire]
	| 	//mot vide
;  


//Analyse lexicale
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
ID  :  '<' ('a'..'z'|'A'..'Z'|'0'..'9'|'-')+ '>' ;
TEXT : '"' ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')* '"' ;
