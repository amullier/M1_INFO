package commandes;
import classes.*;

/**
 * Interface Commande regroupant une méthode d'execution de commande
 * pour les classes qui implémente l'interface
 * @author Antoine et Romain
 *
 */
public interface Commande {
	Editeur editeur = null;
	Enregistreur enregistreur = null;
	
	/**
	 * Méthode d'execution de la commande
	 */
	public void execute();
}
