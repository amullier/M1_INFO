package commandes;

import classes.GestionDefaireRefaire;

public class Defaire implements Commande{

	GestionDefaireRefaire gestion_defaire_refaire;
	
	public Defaire(GestionDefaireRefaire g){
		gestion_defaire_refaire = g;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		gestion_defaire_refaire.defaire();
		
	}

}
