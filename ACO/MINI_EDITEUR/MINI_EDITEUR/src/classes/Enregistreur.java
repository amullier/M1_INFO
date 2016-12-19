package classes;

import commandes.CommandeEnreg;

/**
 * Interface de l'enregistreur de commande
 * @author 14006594
 *
 */
public interface Enregistreur {
		
	public void enregistrer(CommandeEnreg c);
	
	public void demarrerEnregistrement();
	
	public void finirEnregistrement();
	
	public void rejouerEnregistrement();

}
