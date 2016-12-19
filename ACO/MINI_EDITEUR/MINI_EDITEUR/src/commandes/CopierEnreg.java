package commandes;

import classes.Enregistreur;
import classes.Moteur;
import classes.MoteurImpl;
import memento.Memento;
import memento.MementoCopierImpl;

public class CopierEnreg implements CommandeEnreg{
	Memento memento;
	Moteur moteur;
	Enregistreur enregistreur;
	
	/**
	 * Constructeur de classe de la commande 
	 * @param moteur
	 */
	public CopierEnreg(Moteur moteur, Enregistreur enregistreur) {
		super();
		this.moteur = moteur;
		this.enregistreur = enregistreur;
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
		enregistreur.enregistrer((CommandeEnreg) this);
		moteur.copier();
		
	}

	@Override
	public Memento getMemento() {
		memento = new MementoCopierImpl();
		return memento;
	}

	@Override
	public void setMemento(Memento m) {
		memento = m;		
	}

}
