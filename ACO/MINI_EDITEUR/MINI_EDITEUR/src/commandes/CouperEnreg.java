package commandes;

import classes.Enregistreur;
import classes.Moteur;
import classes.MoteurImpl;
import memento.Memento;
import memento.MementoCouperImpl;

public class CouperEnreg implements CommandeEnreg {
	Memento memento;
	Moteur moteur;
	Enregistreur enregistreur;
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public CouperEnreg(Moteur moteur, Enregistreur enregisteur) {
		super();
		this.moteur = moteur;
		this.enregistreur = enregisteur;
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
		enregistreur.enregistrer((CommandeEnreg) this);
		moteur.couper();
		
	}

	@Override
	public Memento getMemento() {
		memento = new MementoCouperImpl();
		return memento;
	}

	@Override
	public void setMemento(Memento m) {
		memento = m;		
	}


}
