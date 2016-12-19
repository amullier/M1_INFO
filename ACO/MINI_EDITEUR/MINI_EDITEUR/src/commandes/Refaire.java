package commandes;

import classes.GestionDefaireRefaire;

public class Refaire implements Commande{

	GestionDefaireRefaire gestion_defaire_refaire;
	
	public Refaire(GestionDefaireRefaire g){
		gestion_defaire_refaire = g;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		gestion_defaire_refaire.refaire();
		
	}

}
