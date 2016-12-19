package commandes;

import classes.Moteur;
import classes.MoteurImpl;

/**
 * Concrete command Coller
 * @author Antoine et Romain
 */
public class Coller implements Commande{
	
	Moteur moteur;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public Coller(Moteur moteur) {
		super();
		this.moteur = moteur;
	}

	/**
	 * Getter du moteur de la commande Coller
	 * @return Moteur
	 */
	public MoteurImpl getMoteur() {
		return (MoteurImpl) moteur;
	}

	/**
	 * Setter du moteur de la commande Coller
	 * @param moteur
	 */
	public void setMoteur(MoteurImpl moteur) {
		this.moteur = moteur;
	}

	/**
	 * Méthode d'execution de la commande Coller
	 */
	@Override
	public void execute() {
		moteur.coller();		
	}

}
