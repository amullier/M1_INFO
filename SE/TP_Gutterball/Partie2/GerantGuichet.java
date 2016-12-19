package leBowling;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Moniteur des guichets
 * Permet de gérer plusieurs guichets
 * @since Partie 2
 * @author Antoine Mullier et Romain Sadok
 */
public class GerantGuichet {

	ArrayList<Guichet> listeGuichet;
	LinkedList<Client> listeClient;	//Liste de priorité des clients
	
	/**
	 * Constructeur de classe
	 * Initialise les listes de guichets et de clients
	 * @param listeGuichet
	 */
	public GerantGuichet(ArrayList<Guichet> listeGuichet){
		this.listeGuichet = listeGuichet;
		this.listeClient = new LinkedList<Client>();
	}
	
	/**
	 * On retourne un guichet de libre pour le client
	 * @return Guichet : guichet pour le client
	 */
	public synchronized Guichet attribuerGuichet(Client client){
		Guichet returnGuichet = null;
		boolean estPrioritaire;
		
		while(returnGuichet==null){
			for(Guichet guichet:listeGuichet){
				if(guichet.estLibre()){
					returnGuichet = guichet;
					break;
				}
			}
			if(returnGuichet==null){
				//Ajouter le client dans la file d'attente pour gérer la priorité
				if(!listeClient.contains(client)){
					listeClient.addLast(client);
				}
				
				do{
					//Le client n'est pour le moment pas prioritaire on le fait attendre
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//tant que le client n'est pas prioritaire
					estPrioritaire = listeClient.getFirst()==client;
					
				}while(!estPrioritaire);
			}
		}
		//Le client est prioritaire on lui donne l'accès au guichet
		returnGuichet.prendreGuichet();
		
		//On enlève le client de la file de priorité
		if(listeClient.contains(client)){
			listeClient.removeFirst();
		}
		return returnGuichet;
	}
	
	/**
	 * Libere le guichet à la fin du traitement
	 * @param Guichet à libérer
	 */
	public synchronized void libererGuichet(Guichet guichet){
		guichet.libererGuichet();
		notifyAll();
	}
}
