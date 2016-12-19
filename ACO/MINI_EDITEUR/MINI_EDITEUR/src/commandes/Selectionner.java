package commandes;

import classes.Enregistreur;
import classes.IHM;
import classes.Moteur;
import classes.MoteurImpl;

/**
 * Concrete command Selectionner
 * @author Antoine et Romain
 */
public class Selectionner implements Commande{

	Moteur moteur;
	IHM ihm;
	Enregistreur enregistreur;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public Selectionner(IHM ihm,Moteur moteur) {
		super();
		this.ihm = ihm;
		this.moteur = moteur;
	}

	/**
	 * Getter du moteur de la commande Selectionner
	 * @return Moteur
	 */
	public MoteurImpl getMoteur() {
		return (MoteurImpl) moteur;
	}

	/**
	 * Setter du moteur de la commande Selectionner
	 * @param moteur
	 */
	public void setMoteur(MoteurImpl moteur) {
		this.moteur = moteur;
	}

	/**
	 * Méthode d'execution de la commande Selectionner
	 */
	@Override
	public void execute() {
		moteur.selectionner(ihm.getDebut_selection(),ihm.getFin_selection());
	}

}
