package classes;
/**
 * Classe Selection gerant la selection du mini-editeur
 * @author Antoine Mullier et Romain Sadok
 *
 */
public class Selection {
	private int debut,fin;
	
	/**
	 * Constructeur de classe
	 * @param deb
	 * @param fin
	 */
	public Selection (int deb, int fin){
		this.debut = deb;
		this.fin = fin;
	}
	
	/**
	 * Constructeur par recopie d'une selection
	 * @param selection
	 */
	public Selection(Selection selection) {
		this.debut = selection.getDebut();
		this.fin =  selection.getFin();
	}

	/**
	 * Getter du debut de selection
	 * @return
	 */
	public int getDebut(){
		return debut;
	}
	
	/**
	 * Getter de la fin de selection
	 * @return
	 */
	public int getFin(){
		return fin;
	}
	
	/**
	 * Setter du debut de selection
	 * @param deb
	 */
	public void setDebut(int deb){
		this.debut = deb;
	}
	
	/**
	 * Setter de la fin de selection
	 * @param fin
	 */
	public void setFin(int fin){
		this.fin = fin;
	}
	
}
