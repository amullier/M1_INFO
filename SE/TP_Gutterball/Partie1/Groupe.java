package leBowling;


public class Groupe {
	private int taille_groupe;
	private int nb_personnes;
	private int nb_personnes_en_partie;	//Le nombre de joueur qui jouent
	private int nb_personnes_avec_chaussures;
	
	public Groupe(int taille_groupe) {
		this.taille_groupe = taille_groupe;
		this.nb_personnes_en_partie = 0;
		this.nb_personnes_avec_chaussures= 0;
	}

	public synchronized boolean estComplet() {
		return (taille_groupe==nb_personnes);
	}

	public synchronized void addClient() {
		//TODO :Peut etre ajouter l'objet client à une liste client du groupe
		nb_personnes++;		
	}

	/**
	 * Le client se met sur la piste et attend que tous les
	 * membres de son groupe arrive	
	 */
	public synchronized void attendreMonGroupe() {
		addClientSurPiste();
		while(!toutLeMondeEstSurLaPiste()){
			System.out.println("On attend " + (taille_groupe-nb_personnes_en_partie) + "clients");
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
	private void addClientSurPiste(){
		nb_personnes_en_partie++;
		if(toutLeMondeEstSurLaPiste()){
			notifyAll();
		}
	}
	

	/**
	 * Test pour savoir si tous les clients sont sur la piste 
	 * @return
	 */
	public synchronized boolean toutLeMondeEstSurLaPiste() {
		return (taille_groupe==nb_personnes_en_partie);
	}

	public synchronized boolean addPersonneChaussures(){
		nb_personnes_avec_chaussures++;	
		System.out.println("Ajout d'une personne avec chaussures au groupe" + toString() + " => " + nb_personnes_avec_chaussures);
		return (nb_personnes_avec_chaussures==nb_personnes);
	}
	
	public synchronized int getNbCHaussures(){
		return nb_personnes_avec_chaussures;
	}
	public synchronized boolean toutLeMondeADesChaussures(){
		return (taille_groupe==nb_personnes_avec_chaussures);
	}
}
