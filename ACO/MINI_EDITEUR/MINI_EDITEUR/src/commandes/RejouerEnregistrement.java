package commandes;

import classes.Enregistreur;

public class RejouerEnregistrement implements Commande {

	Enregistreur enregistreur;
	
	public RejouerEnregistrement(Enregistreur enregistreur) {
		super();
		this.enregistreur = enregistreur;
	}



	@Override
	public void execute() {
		enregistreur.rejouerEnregistrement();
	}
}