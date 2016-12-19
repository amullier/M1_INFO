package classes;

import java.util.ArrayList;
import java.util.List;

import commandes.CommandeEnreg;
import memento.Memento;

/**
 * Enregistreur de commandes
 * @since Version 2
 * @author Antoine Mullier et Romain Sadok
 */
public class EnregistreurImpl implements Enregistreur{
	//Liste des commandes enregistr�es dans l'enregistrement
	private List<EtatEnregistrement> listeCommande = new ArrayList<EtatEnregistrement>();
	
	//Variables interm�diaires
	private Memento memento;
	private CommandeEnreg commande;
	private EtatEnregistrement etat;
	
	private boolean enCoursEnregistrement = false;
		
	/**
	 * M�thode qui initialise l'enregistreur pour enregistr� les commandes futures
	 */
	public void demarrerEnregistrement(){
		enCoursEnregistrement = true;
		listeCommande.clear();
	}
	
	/**
	 * M�thode qui stoppe l'enregistrement des commandes
	 */
	public void finirEnregistrement(){
		enCoursEnregistrement = false;
	}
	
	/**
	 * Enregistrement d'une commande dans l'enregistreur
	 * @param CommandeEnreg c : commande � enregistrer
	 */
	public void enregistrer(CommandeEnreg c){
		if(enCoursEnregistrement){
			memento = c.getMemento();
			etat = new EtatEnregistrement(c,memento);
			listeCommande.add(etat);
		}
		
	}

	/**
	 * M�thode qui rejoue toutes les commandes enregistr�es
	 */
	public void rejouerEnregistrement() {
		for(EtatEnregistrement e:listeCommande){
			memento = e.getMemento();
			commande = e.getCommande();
			commande.setMemento(memento);
			commande.execute();
		}		
	}
	
	
}
