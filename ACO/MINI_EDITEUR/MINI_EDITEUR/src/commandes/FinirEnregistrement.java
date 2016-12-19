package commandes;

import classes.Enregistreur;

public class FinirEnregistrement implements Commande{
	
	Enregistreur enregistreur;
	
	

	public FinirEnregistrement(Enregistreur enregistreur) {
		super();
		this.enregistreur = enregistreur;
	}



	@Override
	public void execute() {
		enregistreur.finirEnregistrement();
	}

}
