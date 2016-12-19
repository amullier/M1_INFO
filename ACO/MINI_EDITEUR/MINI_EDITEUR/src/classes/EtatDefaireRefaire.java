package classes;

import commandes.CommandeDefaireRefaire;
import memento.Memento;

/**
 * Classe qui permet de regrouper une CommandeDefaireRefaire et un memento
 * dans une seule entite
 * @since Version 3
 *
 */
public class EtatDefaireRefaire {
	private CommandeDefaireRefaire commande;
	private Memento memento;
	
	/**
	 * Constructeur de classe : Creation d'un nouvel EtatDefaireRefaire 
	 * @param commande
	 * @param memento
	 */
	public EtatDefaireRefaire(CommandeDefaireRefaire commande, Memento memento) {
		super();
		this.commande = commande;
		this.memento = memento;
	}
	
	/**
	 * Getter de la commande de l'etat
	 * @return
	 */
	public CommandeDefaireRefaire getCommande() {
		return commande;
	}
	
	/**
	 * Setter de la commande de l'etat
	 * @param commande
	 */
	public void setCommande(CommandeDefaireRefaire commande) {
		this.commande = commande;
	}
	
	/**
	 * Getter du memento de l'etat
	 * @return
	 */
	public Memento getMemento() {
		return memento;
	}
	
	/**
	 * Setter du memento de l'etat
	 * @param memento
	 */
	public void setMemento(Memento memento) {
		this.memento = memento;
	}
}
