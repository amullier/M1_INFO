grammar TP1;

options {
    k=1;
}

@header {
import java.util.HashMap;
}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}


prog:   document ;

document :  s+;

s : 	
	ID obj[$ID.text] '.' 
	;



obj [String attPrincipal] : 
	ID description[$attPrincipal,$ID.text] predicat[$attPrincipal] ;



description [String attPrincipal,String attSecondaire] : 
	entite[$attPrincipal,$attSecondaire] entite_bis[$attPrincipal,$attSecondaire];

entite [String attPrincipal,String attSecondaire] : 
	
	ID {System.out.println($attPrincipal + " " + $attSecondaire + " " +$ID.text + ".");}
	| 
	Text {System.out.println($attPrincipal + " " + $attSecondaire + " " +$Text.text + ".");} ;

entite_bis [String attPrincipal,String attSecondaire] : 
	',' description[$attPrincipal,$attSecondaire] entite_bis[$attPrincipal,$attSecondaire]
	| //mot vide
	;  

predicat [String attPrincipal] :  
	';' obj[$attPrincipal] 
	| //mot vide
	;




    


//analyseur lexical
ID  :   '<' ('a'..'z'|'A'..'Z'|'0'..'9' | '-')+ '>' ;
Text :'"' ('a'..'z'|'A'..'Z'|'0'..'9' | ' ' | '-'| '&')+ '"' ;

WS : (' '|'\t'|'\n'|'\r')+ { skip(); } ;
