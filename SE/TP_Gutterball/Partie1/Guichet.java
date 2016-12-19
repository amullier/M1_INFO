package leBowling;

import java.util.ArrayList;

public class Guichet {
	private ArrayList<Groupe> liste_groupe;
	
	/**
	 * Essaie de trouver un groupe pas complet
	 * @return
	 */
	private Groupe chercheGroupeDisponible(){
		Groupe dispo_groupe=null;
		for(Groupe g:liste_groupe){
			if(!g.estComplet()){
				dispo_groupe = g;
				break;
			}
		}
		
		//Attente lors de la recherche de groupe
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return dispo_groupe;
	}
	
	/**
	 * Si le groupe n'est pas valide (null) c'est a dire pas de groupe de libre alors le client attend
	 * @param dispo_groupe
	 */
	private void groupeValide(Groupe dispo_groupe){
		if(dispo_groupe==null){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Constructeur
	 * @param liste_groupe
	 */
	public Guichet(ArrayList<Groupe> liste_groupe){
		this.liste_groupe = liste_groupe;
	}
	

	/**
	 * Recherche et attribution d'un groupe
	 * @param client
	 * @return
	 */
	public synchronized Groupe attribuerGroupe(Client client) {
		Groupe dispo_groupe = null;
		while(dispo_groupe==null){
			
			dispo_groupe = chercheGroupeDisponible();
			groupeValide(dispo_groupe);
		}
		
		//on ajoute un membre au groupe
		dispo_groupe.addClient(); //Peut etre client en paramètre
		
		//On réveille tous les clients qui sont attente que le groupe soit complet
		notifyAll();
		
		return dispo_groupe;
	}

	/**
	 * Payer la partie prend un certain et doit se faire client par client
	 */
	public synchronized void payerPartie(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
