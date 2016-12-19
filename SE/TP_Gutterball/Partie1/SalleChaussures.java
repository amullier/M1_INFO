package leBowling;

//Moniteur pour les chaussures

import java.util.Stack;

public class SalleChaussures {
	
	Stack<PaireChaussures> liste_chaussures;
	
	/**
	 * Si pas de chaussures on attend qu'une paire se libère
	 * @return
	 */
	private PaireChaussures prendreUnePaire(){
		while(liste_chaussures.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Attente lors de la prise de paire de chaussures
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return liste_chaussures.pop();
	}
	
	/**
	 * Attente que le groupe soit complet pour rentrer dans la salle des chaussures
	 * @param groupe
	 */
	private void attenteEnDehorsSalle(Groupe groupe){
		while(!groupe.estComplet()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	/**
	 * Attente que le reste du groupe ait des chaussures
	 * @param groupe
	 */
	private void attenteAvantDeSortir(Groupe groupe){
		//On ajoute une personne avec chaussures au groupe
		groupe.addPersonneChaussures();
		
		while(!groupe.toutLeMondeADesChaussures()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Je reveille mon groupe");
		notifyAll();
	}
	
	public SalleChaussures(int nb_chaussures){
		PaireChaussures paire;
		liste_chaussures = new Stack<PaireChaussures>();
		for (int i = 0; i < nb_chaussures; i++) {
			paire = new PaireChaussures(i);
			liste_chaussures.push(paire);
		}
	}
	
	public synchronized void donnerChaussures(Client client, Groupe groupe) {
		//Si le groupe n'est pas complet il ne rentre pas dans la salle
		attenteEnDehorsSalle(groupe);
		
		System.out.println("Le client n°"+ client.getIdent() +" est dans la salle des chaussures et attend une paire de chaussures.");
		
		//On attribue des chaussures au client
		client.setPaireChaussure(prendreUnePaire());

		System.out.println("Le client n°"+ client.getIdent() +" est dans la salle des chaussures et a une paire de chaussures.\nAttente de son groupe pour sortir");
		
		//Il faut que le client attende que tous les clients de son groupe aient des chaussures
		attenteAvantDeSortir(groupe);
		
		System.out.println("Le client n°"+ client.getIdent() +" est sorti.");
	}
	
	/**
	 * On remet une paire dans la salle des chaussures
	 * On réveille les clients qui attendent une paire de chaussures
	 * @param p
	 */
	public synchronized void remettreUnePaire(PaireChaussures p){
		liste_chaussures.push(p);
		
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
