tree grammar ArpenteurXML;

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

String withoutQuotes(String s){return s.substring(1,s.length() -1);}

}

doc:
	{
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		System.out.println("<rdf:RDF\n\txml:base=\"http://mydomain.org/myrdf/\"");
		System.out.println("\txmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">");
	}
	bloc+
	{
		System.out.print("</rdf:RDF>");
	}
;

bloc:
	^( ID 
		{
			memory.put("attribut_principal",$ID.text);
			System.out.println("<rdf:Description rdf:about=\""+withoutQuotes($ID.text)+"\">");
		} 

		predicat+
		{
			System.out.println("</rdf:Description>");
		}
	 )
;

predicat:
	^(ID {memory.put("attribut_secondaire",withoutQuotes($ID.text));} objet+) 
;

objet:
	//Affichage de la ligne correspondante
	ID {System.out.println("\t<rdf:"+memory.get("attribut_secondaire")+" rdf:resource=\""+withoutQuotes($ID.text)+"\"/>");}
	| 
	TEXT {System.out.println("\t<"+memory.get("attribut_secondaire")+">"+withoutQuotes($TEXT.text)+"</"+memory.get("attribut_secondaire")+">");}
;