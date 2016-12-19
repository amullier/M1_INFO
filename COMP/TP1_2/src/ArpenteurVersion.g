tree grammar ArpenteurVersion;

options {
    //Grammaire LL(1)
    k=1;
    //Options pour produire l'AST
    tokenVocab=TP1Part3_1;
    ASTLabelType=CommonTree;
}


@header {
import java.util.HashMap;
}

@members {
HashMap memory = new HashMap();
}

doc:
	(
		{
			//On initialise de nombre versions à zero
			memory.put("nb_version",1);
		}
		bloc
	)+
;

bloc:
	^(ID {memory.put("attribut_principal",$ID.text);} version+) 
		
;
version: ^(
		ID
		{
			//Pour avoir un identifiant avec la première lettre de <version>
			memory.put("ident_version", $ID.text.substring(1,2));
			System.out.print(memory.get("attribut_principal").toString() + " " + $ID.text+" _:"+ $ID.text.substring(1,2) + memory.get("nb_version").toString()+" .\n");
		}
		predicat+ 
		{
			//Incrémentation de nb_version
			memory.put("nb_version",Integer.parseInt(memory.get("nb_version").toString())+1);
		}
	)

; 
predicat:
	^(ID 
		{ memory.put("attribut_secondaire",$ID.text);} 
		objet
	) 
;


objet:	
	//Affichage de la ligne correspondante
	ID {System.out.println("_:"+memory.get("ident_version") + memory.get("nb_version")+" "+memory.get("attribut_secondaire")+" "+$ID.text+" .");}
	| 
	TEXT {System.out.println("_:"+memory.get("ident_version") +memory.get("nb_version")+" "+memory.get("attribut_secondaire")+" "+$TEXT.text+" .");}
;
