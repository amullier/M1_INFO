tree grammar Arpenteur;

options {
    //Grammaire LL(1)
    k=1;
    //Options pour produire l'AST
    tokenVocab=TP1Part2;
    ASTLabelType=CommonTree;
}


@header {
import java.util.HashMap;
}

@members {
HashMap memory = new HashMap();
}

doc:
	bloc+

	//Affichage du nombre de description
	{
		System.out.println("Nombre de descriptions : " + memory.get("nb_description"));
	}
;

bloc:
	^(ID {memory.put("attribut_principal",$ID.text);} predicat+) 
	{
		//On incrémente le compteur de description
		if(memory.get("nb_description")==null){
			memory.put("nb_description",1);
		}
		else{
			memory.put("nb_description",Integer.parseInt(memory.get("nb_description").toString())+1);
		}
	}
;

predicat:
	^(ID {memory.put("attribut_secondaire",$ID.text);} objet+) 
;

objet:
	//Affichage de la ligne correspondante
	ID {System.out.println(memory.get("attribut_principal")+" "+memory.get("attribut_secondaire")+" "+$ID.text);}
	| 
	TEXT {System.out.println(memory.get("attribut_principal")+" "+memory.get("attribut_secondaire")+" "+$TEXT.text);}
;