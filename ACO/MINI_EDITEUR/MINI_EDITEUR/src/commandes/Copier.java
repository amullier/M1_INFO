package commandes;

import classes.*;

/**
 * Concrete command Copier
 * @author Antoine et Romain
 */
public class Copier implements Commande{

	Moteur moteur;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public Copier(Moteur moteur) {
		super();
		this.moteur = moteur;
	}

	/**
	 * Getter du moteur de la commande Copier
	 * @return Moteur
	 */
	public MoteurImpl getMoteur() {
		return (MoteurImpl) moteur;
	}

	/**
	 * Setter du moteur de la commande Copier
	 * @param moteur
	 */
	public void setMoteur(MoteurImpl moteur) {
		this.moteur = moteur;
	}

	/**
	 * Méthode d'execution de la commande Copier
	 */
	@Override
	public void execute() {
		moteur.copier();
		
	}

}
