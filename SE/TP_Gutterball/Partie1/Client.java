package leBowling;

//benjamin.rouxel@inria.fr -> 2décembre
public class Client extends Thread{
	private Groupe mon_groupe;
	private int id;
	private Guichet guichet;
	private SalleChaussures sallechaussures;
	private Bowling bowling;
	private PaireChaussures ma_paire_de_chaussures;
	
	public Client(int id,Guichet guichet, SalleChaussures sallechaussures, Bowling bowling) {
		this.id = id;
		this.guichet = guichet;
		this.sallechaussures = sallechaussures;
		this.bowling = bowling;
	}
	
	/**
	 * Retourne l'identifiant du client
	 * @return
	 */
	public int getIdent(){
		return this.id;
	}
	
	/**
	 * Setter sur la paire de chaussures du client
	 * @param paire
	 */
	public void setPaireChaussure(PaireChaussures paire) {
		ma_paire_de_chaussures = paire;		
	}
	
	/**
	 * Actions du client dans le bowling
	 */
	public void run(){
		
		System.out.println("Un client n°"+ id +" cherche a obtenir un groupe.");
		
		mon_groupe = guichet.attribuerGroupe(this);
		
		System.out.println("Le client n°"+ id +" se voit attribuer le groupe : " + mon_groupe.toString());
		
		System.out.println("Le client n°"+ id +" va dans la salle des chaussures et attend que tout le monde ait des chaussures.");
		
		//On va dans la salle des chaussures avec le groupe attribué
		sallechaussures.donnerChaussures(this,mon_groupe);
		
		System.out.println("Le client n°"+ id +" reserve une piste pour son groupe.");
		
		Piste ma_piste = bowling.reservePiste(mon_groupe);
		
		System.out.println("Le client n°"+ id +" joue.");
		//La partie est en cours
		try {
			sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Le client n°"+ id +" a fini la partie.");
		
		//Attendre tous les membres du groupe
		mon_groupe.attendreMonGroupe();
		
		System.out.println("Le client n°"+ id +" demande la libération de la piste.");
		
		//La partie est finie on libère la piste
		bowling.liberePiste(ma_piste);
		
		System.out.println("Le client n°"+ id +" va payer.");
		//Payer la partie
		guichet.payerPartie();
		
		System.out.println("Le client n°"+ id +" remet ses chaussures.");
		
		//restituer paire de chaussures
		sallechaussures.remettreUnePaire(ma_paire_de_chaussures);
		
		//Le client a fini et sort du bowling
	}
	
}
