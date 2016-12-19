package commandes;

import classes.Enregistreur;

public class DemarrerEnregistrement implements Commande {

	Enregistreur enregistreur;
	
	

	public DemarrerEnregistrement(Enregistreur enregistreur) {
		super();
		this.enregistreur = enregistreur;
	}



	@Override
	public void execute() {
		enregistreur.demarrerEnregistrement();
	}
	
}
