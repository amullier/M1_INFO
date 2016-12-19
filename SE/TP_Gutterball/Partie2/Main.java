package leBowling;
import java.util.ArrayList;

/**
 * Programme principal du TP
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */
public class Main {
	/*
	 * Constantes du programme
	 */
	final static int NB_CLIENTS = 12;
	final static int NB_GROUPES = 4;
	final static int TAILLE_GROUPE = 3;
	final static int NB_PISTES = 1;
	final static int NB_CHAUSSURES = NB_PISTES*TAILLE_GROUPE;
	final static int NB_GUICHETS = 3;
	
	/**
	 * Lancement du programme
	 * @param args
	 */
	public static void main(String[] args) {
		Client client;
		Groupe groupe;
		
		//Le bowling
		Bowling bowling = new Bowling(NB_PISTES);		
				
		//Liste de groupes
		ArrayList<Groupe> liste_groupe = new ArrayList<>();
		for (int i = 0; i < NB_GROUPES; i++) {
			groupe = new Groupe(TAILLE_GROUPE);
			liste_groupe.add(groupe);
		}
		
		//Liste de guichets
		ArrayList<Guichet> liste_guichet = new ArrayList<>();
		for (int i = 0; i < NB_GUICHETS; i++) {
			Guichet guichet = new Guichet(i+1,liste_groupe,bowling);
			liste_guichet.add(guichet);
		}
		
		//Le gérant des guichets
		GerantGuichet gerantGuichet = new GerantGuichet(liste_guichet);
		
		//La salle des chaussures
		SalleChaussures sallechaussures = new SalleChaussures(NB_CHAUSSURES,bowling);	
				
		//Liste de clients
		ArrayList<Client> liste_client = new ArrayList<>();
		for (int i = 0; i < NB_CLIENTS; i++) {
			client = new Client(i+1,gerantGuichet,sallechaussures,bowling);
			liste_client.add(client);
		}
		
		//On fait entrer les clients dans le bowling
		for(Client clt:liste_client){
			clt.start();
		}
	}

}
