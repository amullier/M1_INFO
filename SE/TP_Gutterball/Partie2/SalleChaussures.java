package leBowling;
import java.util.Stack;
/**
 * Classe SalleChaussures
 * Moniteur pour les chaussures
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */

public class SalleChaussures {
	
	Stack<PaireChaussures> listePaireChaussures;
	Bowling bowling;
	
	/**
	 * Constructeur de classe
	 * @param NB_CHAUSSURES
	 * @param bowling
	 */
	public SalleChaussures(int nb_chaussures,Bowling bowling){
		PaireChaussures paire;
		this.bowling = bowling;
		
		//On établit la liste des paires de chaussures disponibles
		listePaireChaussures = new Stack<PaireChaussures>();
		for (int i = 0; i < nb_chaussures; i++) {
			paire = new PaireChaussures(i);
			listePaireChaussures.push(paire);
		}
	}
	
	/**
	 * Méthode qui donne une paire de chaussures à un client
	 * @param client
	 * @param groupe
	 */
	public synchronized void donnerChaussures(Client client, Groupe groupe) {
		//Si le groupe n'est pas complet le client ne rentre pas dans la salle
		attenteEnDehorsSalle(groupe);
		
		System.out.println("Le client n°"+ client.getIdent() +" est dans la salle des chaussures et attend une paire de chaussures.");

		//Avant d'attribuer des chaussures on regarde si le groupe est prioritaire
		while(!bowling.estPrioritaire(groupe)){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//On attribue des chaussures au client
		client.setPaireChaussure(prendreUnePaire());

		System.out.println("Le client n°"+ client.getIdent() +" est dans la salle des chaussures et a une paire de chaussures.\nAttente de son groupe pour sortir");
		
		//Il faut que le client attende que tous les clients de son groupe aient des chaussures
		attenteAvantDeSortir(groupe);
		
		System.out.println("Le client n°"+ client.getIdent() +" est sorti.");
	}
	
	
	/**
	 * Attente que le groupe soit complet pour rentrer dans la salle des chaussures
	 * @param groupe
	 */
	private synchronized void attenteEnDehorsSalle(Groupe groupe){
		//Si le groupe n'est pas complet on attend en dehors de la salle
		while(!groupe.estComplet()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Quand le groupe est complet on réveille tout le monde pour que
		//les clients de mon groupe prenne des chaussures
		notifyAll();
	}
	
	
	/**
	 * Méthode pour prendre une paire de chaussures dans la salle des chaussures
	 * Si pas de chaussures on attend qu'une paire se libère
	 * @return
	 */
	private synchronized PaireChaussures prendreUnePaire(){
		while(listePaireChaussures.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Prendre une paire de chaussures ça prend du temps
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return listePaireChaussures.pop();
	}
	
	
	/**
	 * Attente que le reste du groupe ait des chaussures avant de sortir
	 * @param groupe
	 */
	private synchronized void attenteAvantDeSortir(Groupe groupe){
		//On ajoute une personne avec chaussures au groupe
		groupe.addPersonneChaussures();

		while(!groupe.toutLeMondeADesChaussures()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		notifyAll();
	}	
	
	/**
	 * On remet une paire dans la salle des chaussures
	 * On réveille les clients qui attendent une paire de chaussures
	 * @param p
	 */
	public synchronized void remettreUnePaire(PaireChaussures p){
		listePaireChaussures.push(p);
		
		//Attente lors de la remise de paire de chaussures
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		notifyAll();
	}
}
