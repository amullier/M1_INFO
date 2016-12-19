package commandes;

import classes.IHM;
import classes.Moteur;
import classes.MoteurImpl;

/**
 * Concrete command Inserer
 * @author Antoine et Romain
 */
public class Inserer implements Commande{

	Moteur moteur;
	IHM ihm;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public Inserer(IHM ihm,Moteur moteur) {
		super();
		this.moteur = moteur;
		this.ihm = ihm;
	}

	/**
	 * Getter du moteur de la commande Inserer
	 * @return Moteur
	 */
	public MoteurImpl getMoteur() {
		return (MoteurImpl) moteur;
	}

	/**
	 * Setter du moteur de la commande Inserer
	 * @param moteur
	 */
	public void setMoteur(MoteurImpl moteur) {
		this.moteur = moteur;
	}

	/**
	 * Méthode d'execution de la commande Inserer
	 */
	@Override
	public void execute() {
		moteur.inserer(ihm.getTexte_saisi());
		
	}

}
