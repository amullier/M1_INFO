package classes;
/**
 * Presse papier su mini-editeur
 * @author Antoine Mullier et Romain Sadok
 *
 */
public class PressePapier {
	
	private String contenu;
	
	
	/**
	 * Constructeur du presse papier
	 * Initialise le contenu a vide
	 */
	public PressePapier (){
		this.setContenu("");
	}

	/**
	 * Getter du contenu du presse papier
	 * @return
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * Setter du contenu du papier
	 * @param contenu
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	
}
