package leBowling;

import java.util.ArrayList;

/**
 * Programme principal du TP3
 */
public class Main {
	final static int nb_client = 12;
	final static int nb_groupe = 4;
	final static int taille_groupe = 3;
	final static int nb_chaussures = 15;
	final static int nb_pistes = 1;
	public static void main(String[] args) {
		Client client;
		Groupe groupe;
		
		//Liste de groupes
		ArrayList<Groupe> liste_groupe = new ArrayList<>();
		for (int i = 0; i < nb_groupe; i++) {
			groupe = new Groupe(taille_groupe);
			liste_groupe.add(groupe);
		}
				
		Guichet guichet = new Guichet(liste_groupe);
		SalleChaussures sallechaussures = new SalleChaussures(nb_chaussures);
		Bowling bowling = new Bowling(nb_pistes);
				
				
		//Liste de clients
		ArrayList<Client> liste_client = new ArrayList<>();
		for (int i = 0; i < nb_client; i++) {
			client = new Client(i+1,guichet,sallechaussures,bowling);
			liste_client.add(client);
		}
		
		
		//On start les clients
		for(Client clt:liste_client){
			clt.start();
		}
		
		
		
		

	}

}
