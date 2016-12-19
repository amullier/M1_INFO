package leBowling;
/**
 * Classe Groupe qui l'ajout de client, l'attente du
 * groupe sur les différents états d'un processus client 
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */
public class Groupe {
	//Nombre de personnes maximal d'un groupe
	private int tailleGroupe;
	
	//Nombre de personnes effectivement présentes dans le groupe
	private int nbPersonnes;
	
	//Nombre de personnes qui jouent sur la piste 
	private int nbPersonnesEnPartie;
	
	//Nombre de personnes avec des chaussures
	private int nbPersonnesAvecChaussures;
	
	/**
	 * Constructeur de classe Groupe
	 * Initialisation à 0 des
	 * @param tailleGroupe
	 */
	public Groupe(int taille_groupe) {
		this.tailleGroupe = taille_groupe;
		this.nbPersonnesEnPartie = 0;
		this.nbPersonnesAvecChaussures= 0;
	}

	/**
	 * Ajout d'un client dans le groupe SEULEMENT si c'est possible
	 * @return boolean : true si le client a été ajouter
	 * 					 false sinon
	 */
	public synchronized boolean ajoutClientDansGroupe() {
		if(tailleGroupe>nbPersonnes){
			nbPersonnes++;
			return true;
		}
		return false;
	}

	/**
	 * Méthode qui renvoie true qui renvoie si le groupe est complet
	 * @return
	 */
	public synchronized boolean estComplet(){
		return (tailleGroupe==nbPersonnes);
	}

	/**
	 * Le client se met sur la piste et attend que tous les
	 * membres de son groupe arrive
	 */
	public synchronized void attendreMonGroupe() {
		//On ajoute le client sur la piste dans l'attribut nbPersonnesEnPartie
		addClientSurPiste();
		if(!toutLeMondeEstSurLaPiste()){ 
			try {
				wait(); //On attend tous les membres du groupe
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Ajout d'un client sur la piste
	 */
	private synchronized void addClientSurPiste(){
		nbPersonnesEnPartie++;
		if(toutLeMondeEstSurLaPiste()){
			notifyAll();
		}
	}
	

	/**
	 * Test pour savoir si tous les clients sont sur la piste 
	 * @return
	 */
	public synchronized boolean toutLeMondeEstSurLaPiste() {
		return (tailleGroupe==nbPersonnesEnPartie);
	}

	public synchronized void addPersonneChaussures(){
		nbPersonnesAvecChaussures++;	
	}
	public synchronized boolean toutLeMondeADesChaussures(){
		return (tailleGroupe==nbPersonnesAvecChaussures);
	}	
	
}
