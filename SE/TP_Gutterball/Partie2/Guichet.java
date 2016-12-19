package leBowling;
import java.util.ArrayList;

/**
 * Classe qui gère le Guichet
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */
public class Guichet {
	
	private ArrayList<Groupe> listeDesGroupes;
	private Bowling bowling;
	private boolean estLibre; 
	private int id;
	
	/**
	 * Constructeur de classe Guichet
	 * @param listeDesGroupes
	 */
	public Guichet(int id,ArrayList<Groupe> liste_groupe, Bowling bowling){
		this.id = id;
		this.listeDesGroupes = liste_groupe;
		this.bowling = bowling;
		this.estLibre = true;
	}
	

	/**
	 * Recherche et attribution d'un groupe
	 * @param client
	 * @return
	 */
	public synchronized Groupe attribuerGroupe(Client client) {
		//On recherche un groupe disponible
		Groupe groupeDisponible = null;
		while(groupeDisponible==null){
			groupeDisponible = chercheGroupeDisponible();			
		}
		
		//On ajoute le groupe trouvé à la file de gestion de priorité
		bowling.addGroupeAtQueue(groupeDisponible);
		
		return groupeDisponible;
	}
	
	/**
	 * Méthode qui essaie de trouver un groupe
	 * pour le client
	 * @return
	 */
	private synchronized Groupe chercheGroupeDisponible(){
		Groupe groupeDisponible=null;
		for(Groupe g:listeDesGroupes){
			if(g.ajoutClientDansGroupe()){
				groupeDisponible = g;
				break;
			}
		}
		
		//Rechercher un groupe ça prend du temps
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return groupeDisponible;
	}

	/**
	 * Payer la partie prend un certain et doit se faire client par client donc synchronized
	 */
	public synchronized void payerPartie(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter de l'attribut estLibre
	 * @return
	 */
	public synchronized boolean estLibre() {
		return estLibre;
	}

	/**
	 * On bloque le guichet pour les autres clients souhaitant
	 * passer au guichet
	 */
	public synchronized void prendreGuichet() {
		estLibre = false;		
	}

	/**
	 * On libère le guichet pour les autres clients
	 */
	public synchronized void libererGuichet() {
		estLibre = true;
	}

	/**
	 * Getter de l'id de Guichet
	 * @return id
	 */
	public synchronized int getIdent() {
		return id;
	}

}
