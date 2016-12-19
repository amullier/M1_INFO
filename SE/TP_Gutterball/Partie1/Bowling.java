package leBowling;

import java.util.ArrayList;
import java.util.Queue;

/**
 * En charge de la gestion des pistes en jeu (libre/occupé)
 * 
 */
public class Bowling {
	ArrayList<Piste> liste_piste;
	Queue<Groupe> ordre_groupe;
	
	public Bowling(int nb_piste){
		Piste p;
		liste_piste = new ArrayList<Piste>();
		for (int i = 0; i < nb_piste; i++) {
			p = new Piste();
			liste_piste.add(p);
		}
	}

	public synchronized Piste reservePiste(Groupe groupe) {
		Piste ma_piste;
		
		//On cherche une piste libre pour le groupe
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
		
		//Le client a trouvé une piste pour son groupe -> il appelle son groupe
		notifyAll();
		
		return ma_piste;
				
	}
	
	/**
	 * Renvoie null si pas de piste libre pour le groupe
	 */
	private Piste cherchePiste(Groupe groupe){
		Piste res_piste = null;
		for (Piste p:liste_piste){
			if(p.isLibre(groupe)){
				res_piste = p;
				break;
			}
		}
		return res_piste;
	}
	
	/**
	 * On libère la piste en réveillant un client d'un autre groupe
	 * potentiellement en attente de piste pour jouer
	 * @param ma_piste
	 */
	public synchronized void liberePiste(Piste ma_piste){
		ma_piste.liberer();
		
		notifyAll();
	}

}
