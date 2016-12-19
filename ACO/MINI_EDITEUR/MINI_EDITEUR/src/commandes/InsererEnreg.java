package commandes;

import classes.Enregistreur;
import classes.IHM;
import classes.Moteur;
import classes.MoteurImpl;
import memento.Memento;
import memento.MementoInsererImpl;

/**
 * Concrete command Inserer
 * @author Antoine et Romain
 */
public class InsererEnreg implements CommandeEnreg{

	Moteur moteur;
	IHM ihm;
	Enregistreur enregistreur;
	Memento memento;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public InsererEnreg(IHM ihm,Moteur moteur,Enregistreur enregistreur) {
		super();
		this.moteur = moteur;
		this.ihm = ihm;
		this.enregistreur = enregistreur;
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
		enregistreur.enregistrer((CommandeEnreg) this);
		moteur.inserer(((MementoInsererImpl) memento).getTexte());
	}

	@Override
	public Memento getMemento() {
		memento = new MementoInsererImpl();
		((MementoInsererImpl) memento).setTexte(ihm.getTexte_saisi());
		return memento;
	}

	@Override
	public void setMemento(Memento m) {
		memento = m;		
	}

}
