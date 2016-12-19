package leBowling;

/**
 * Classe Piste qui gère l'état d'une piste
 * @since Partie 1
 * @author Antoine Mullier et Romain Sadok
 */
public class Piste {
	private boolean libre;
	private Groupe groupe;
	
	/**
	 * Constructeur de classe
	 * Initialisation de la Piste
	 * -> libre 
	 * -> pas de groupe affecté à la piste
	 */
	public Piste(){
		libre=true;
		groupe=null;
	}
	
	/**
	 * Getter de l'état de la piste pour un groupe
	 * @param g : Groupe du client
	 * @return
	 */
	public boolean isLibre(Groupe g) {
		return libre||(groupe==g);
	}

	/**
	 * Méthode qui permet de réserver une piste pour un groupe
	 * @param groupe
	 */
	public void reserve(Groupe groupe) {
		libre = false;
		this.groupe = groupe;
	}
	
	/**
	 * Méthode qui permet de libérer la piste pour la rendre accessible
	 * par d'autres groupes
	 */
	public void liberer(){
		libre = true;
		groupe = null;
	}
}
