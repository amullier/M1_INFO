package classes;

import commandes.CommandeEnreg;
import memento.Memento;

/**
 * Classe qui permet de regrouper une commande enregistrable et un memento associe
 * @since Version 2
 *
 */
public class EtatEnregistrement {
	private CommandeEnreg commande;
	private Memento memento;
	
	/**
	 * Constructeur de classe : creation d'un etat d'enregistrement avec une commande et un memento
	 * @param commande
	 * @param memento
	 */
	public EtatEnregistrement(CommandeEnreg commande, Memento memento) {
		super();
		this.commande = commande;
		this.memento = memento;
	}
	
	public CommandeEnreg getCommande() {
		return commande;
	}
	public void setCommande(CommandeEnreg commande) {
		this.commande = commande;
	}
	public Memento getMemento() {
		return memento;
	}
	public void setMemento(Memento memento) {
		this.memento = memento;
	}
}
