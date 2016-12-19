package commandes;

import classes.Moteur;
import classes.MoteurImpl;

/**
 * Concrete command Couper
 * @author Antoine et Romain
 */
public class Couper implements Commande{

	Moteur moteur;
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public Couper(Moteur moteur) {
		super();
		this.moteur = moteur;
	}

	/**
	 * Getter du moteur de la commande Couper
	 * @return Moteur
	 */
	public MoteurImpl getMoteur() {
		return (MoteurImpl) moteur;
	}

	/**
	 * Setter du moteur de la commande Couper
	 * @param moteur
	 */
	public void setMoteur(MoteurImpl moteur) {
		this.moteur = moteur;
	}

	/**
	 * Méthode d'execution de la commande Couper
	 */
	@Override
	public void execute() {
		moteur.couper();
		
	}


}
