package leBowling;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * En charge de la gestion des pistes en jeu (libre/occupé)
 * Prend le rôle de moniteur des pistes
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */
public class Bowling {
	ArrayList<Piste> listePiste;
	LinkedList<Groupe> filePrioritePiste;
	
	/**
	 * Constructeur de l'objet bowling
	 * @param nb_piste : Nombre de pistes du bowling
	 */
	public Bowling(int nb_piste){
		//Création d'une liste de piste de longueur nb_piste
		Piste p;
		listePiste = new ArrayList<Piste>();
		for (int i = 0; i < nb_piste; i++) {
			p = new Piste();
			listePiste.add(p);
		}
		
		//Création de la file de gestion de priorité
		filePrioritePiste = new LinkedList<Groupe>();
	}

	/**
	 * Méthode qui réserve une piste pour un groupe associé
	 * Prends en compte la gestion de l'ordre de priorité
	 * 
	 * @param groupe : Groupe qui demande la réservation d'une piste
	 * @return piste réservée par le groupe
	 */
	public synchronized Piste reservePiste(Groupe groupe) {
		Piste ma_piste;
		
		//On regarde d'abord si le groupe est le premier groupe prioritaire
		Groupe premierGroupe = filePrioritePiste.peek();
		while(premierGroupe!=groupe){
			try {
				//Si le groupe n'est pas le groupe prioritaire on attend son tour : Pas de triche !
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			premierGroupe = filePrioritePiste.peek();
		}

		//On sait que le groupe est prioritaire on cherche donc une piste à réserver
		ma_piste = cherchePiste(groupe);
		while(ma_piste==null){
			//Le client attend sur la piste de danse qu'une piste se libère
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ma_piste = cherchePiste(groupe);
		}	
		
		/*
		 * Le client a trouvé une piste pour son groupe
		 * 
		 * -> Il réveille donc son groupe qui attendent pour trouver une piste
		 * -> Il réveille aussi les personnes en attente de piste pour leur groupe
		 * 
		 */
		notifyAll();
		
		return ma_piste;
				
	}
	
	/**
	 * Méthode privée qui permet de chercher une piste de libre pour un groupe
	 * @param Groupe : Groupe du client
	 * @return Piste ou null si pas de piste trouvée pour le groupe
	 */
	private Piste cherchePiste(Groupe groupe){
		Piste searchPistePourLeGroupe = null;
		for (Piste p:listePiste){
			if(p.isLibre(groupe)){
				searchPistePourLeGroupe = p;
				break;
			}
		}
		return searchPistePourLeGroupe;
	}
	
	/**
	 * Méthode qui enlève le groupe dans la file de priorité des groupes
	 * A appeler uniquement quand tous les clients ont pu avoir des chaussures
	 * @param groupe : Groupe à retirer de la file
	 */
	public synchronized void retirerGroupeDeFilePriorite(Groupe groupe){
		//On regarde si le groupe en tête de file est bien le même que le groupe à retirer
		Groupe searchGroupe = filePrioritePiste.peek();
		if(searchGroupe==groupe){
			filePrioritePiste.removeFirst();
			
			/*
			 * Comme le groupe @groupe n'est plus prioritaire
			 * on réveille les clients en attente que leur groupe soit prioritaire
			 */
			notifyAll();
		}		
	}
	
	
	/**
	 * On libère la piste en réveillant un client d'un autre groupe
	 * potentiellement en attente de piste pour jouer
	 * @param ma_piste
	 */
	public synchronized void liberePiste(Piste ma_piste){
		ma_piste.liberer();
		
		//On réveille qu'un seul client pour qu'il prenne la piste pour son groupe (qu'il va réveiller)
		notify();
	}
	
	/**
	 * Ajout d'un groupe sur la file de gestion de priorité
	 * dans le cas où le groupe n'est pas présent
	 * @param groupe
	 */
	public synchronized void addGroupeAtQueue(Groupe groupe){
		if(!filePrioritePiste.contains(groupe)){
			filePrioritePiste.addLast(groupe);
		}
	}
	
	/**
	 * Méthode qui permet de savoir si le groupe d'un client est prioritaire
	 * @param groupe
	 * @return boolean
	 */
	public synchronized boolean estPrioritaire(Groupe groupe){
		return filePrioritePiste.getFirst()==groupe;
	}
}
