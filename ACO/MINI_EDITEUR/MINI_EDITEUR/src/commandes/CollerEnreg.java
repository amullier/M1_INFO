package commandes;

import classes.Enregistreur;
import classes.Moteur;
import classes.MoteurImpl;
import memento.Memento;
import memento.MementoCollerImpl;

public class CollerEnreg implements CommandeEnreg{
	Moteur moteur;
	Enregistreur enregistreur;
	Memento memento;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public CollerEnreg(Moteur moteur,Enregistreur enregistreur) {
		super();
		this.moteur = moteur;
		this.enregistreur = enregistreur;
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
		enregistreur.enregistrer((CommandeEnreg) this);
		moteur.coller();
	}

	@Override
	public Memento getMemento() {
		memento = new MementoCollerImpl();
		return memento;
	}

	@Override
	public void setMemento(Memento m) {
		memento = m;		
	}
}
