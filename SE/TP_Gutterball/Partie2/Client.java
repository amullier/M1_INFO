package leBowling;

//benjamin.rouxel@inria.fr -> 2dÃ©cembre
/**
 * Classe client
 * Thread qui traite le processus client au sein du bowling
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */
public class Client extends Thread{
	private Groupe monGroupe;
	private int id;
	private GerantGuichet gerantGuichet;
	private SalleChaussures salleChaussures;
	private Bowling bowling;
	private PaireChaussures maPaireDeChaussures;
	
	/**
	 * Constructeur de la classe Client
	 * @param id : Identifiant numérique du client
	 * @param gerantGuichet : Gérant des guichets pour pouvoir obtenir un guichet
	 * @param salleChaussures : Salle des chaussures
	 * @param bowling
	 */
	public Client(int id,GerantGuichet gerantGuichet, SalleChaussures sallechaussures, Bowling bowling) {
		this.id = id;
		this.gerantGuichet = gerantGuichet;
		this.salleChaussures = sallechaussures;
		this.bowling = bowling;
	}
	
	/**
	 * Retourne l'identifiant numérique du client
	 * @return id
	 */
	public int getIdent(){
		return this.id;
	}
	
	/**
	 * Setter sur la paire de chaussures du client
	 * seulement pour la salle des chaussures
	 * @param paire
	 */
	public void setPaireChaussure(PaireChaussures paire) {
		maPaireDeChaussures = paire;		
	}
	
	/**
	 * Actions du client dans le bowling
	 * méthode exécutée par l'appel Thread.start()
	 */
	public void run(){
		System.out.println("Un client n°"+ id +" est entré dans le bowling");
		System.out.println("Un client n°"+ id +" cherche un guichet de disponible.");
		
		Guichet mon_guichet = gerantGuichet.attribuerGuichet(this);
		
		System.out.println("Un client n°"+ id +" est au guichet " + mon_guichet.getIdent());
		
		monGroupe = mon_guichet.attribuerGroupe(this);
		
		System.out.println("Le client n°"+ id +" se voit attribuer le groupe : " + monGroupe.toString());
		
		gerantGuichet.libererGuichet(mon_guichet);
		
		System.out.println("Un client n°"+ id +" quitte le guichet " + mon_guichet.getIdent());
				
		
		System.out.println("Le client n°"+ id +" va dans la salle des chaussures");
		
		//Le client va dans la salle des chaussures avec le groupe attribué
		salleChaussures.donnerChaussures(this,monGroupe);
		
		System.out.println("Un client n°"+ id +" est sorti de la salle des chaussures");
		
		System.out.println("Le client n°"+ id +" reserve une piste pour son groupe.");
		
		Piste ma_piste = bowling.reservePiste(monGroupe);
		
		System.out.println("Un client n°"+ id +" attend tous les membres de son groupe avant de jouer");

		//Attendre tous les membres du groupe
		monGroupe.attendreMonGroupe();
		
		//on peut retirer le groupe de la file de priorité
		bowling.retirerGroupeDeFilePriorite(monGroupe);
				
		System.out.println("Le client n°"+ id +" joue.");

		//Jouer au bowling prend un certain temps
		try {
			sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Le client n°"+ id +" a fini la partie.");
				
		System.out.println("Le client n°"+ id +" va libérer la piste.");
		
		//La partie est finie le client libère la piste
		bowling.liberePiste(ma_piste);
		
		System.out.println("Le client n°"+ id +" attend d'avoir un guichet pour payer.");
		//Payer la partie
		mon_guichet = gerantGuichet.attribuerGuichet(this);

		System.out.println("Un client n°"+ id +" est au guichet " + mon_guichet.getIdent());
		
		mon_guichet.payerPartie();
		
		gerantGuichet.libererGuichet(mon_guichet);
		
		System.out.println("Un client n°"+ id +" libere le guichet " + mon_guichet.getIdent());
		
		System.out.println("Le client nÂ°"+ id +" remet ses chaussures.");
		
		salleChaussures.remettreUnePaire(maPaireDeChaussures);
		
		//Le client a fini et sort du bowling
		System.out.println("Un client n°"+ id +" sort du bowling.");
	}
	
}
