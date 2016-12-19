package classes;

/**
 * Classe gerant le contenu et les operations de base du mini-editeur
 * @author Antoine Mullier et Romain Sadok
 *
 */
public class Buffer {
	
	private String contenu;
	private Selection selc;
	private PressePapier pp;
	
	/**
	 * Constructeur du Buffer
	 * Initialise le cursuer au début du texte et le presse papier à vide 
	 * @param contenu
	 */
	public Buffer (String contenu){
		this.contenu = contenu;
		this.selc = new Selection(0,0);
		this.pp = new PressePapier();
	}
	
	/**
	 * Getter 
	 * @return le texte du buffer
	 */
	public String getContenu(){
		return this.contenu;
	}
	
	/**
	 * Change le contenu du texte
	 * @param s
	 */
	public void setContenu (String s){
		this.contenu = s ;
	}
	
	/**
	 * Getter 
	 * @return les indices de la selection
	 */
	public Selection getSelection(){
		return selc;
	}
	
	/**
	 * 
	 * @return retourne le texte du presse papier
	 */
	public PressePapier getPressePapier() {
		return pp;
	}
	
	/**
	 * Change le contenu du presse papier
	 * @param contenu 
	 */
	public void setPressePapier(String contenu) {
		this.pp.setContenu(contenu);
	}
	
	/**
	 * Insere un contenu a l'endroit où se trouve le curseur ou sur la selection
	 * @param subString
	 */
	public void insertContenu(String subString){
		deleteSelection();/* Si le texte n'est pas selectionner on le supprime pas  */
		
		int deb = selc.getDebut();
		int fin = selc.getFin();
		if(contenu==""){
			contenu = subString;
		}
		else{
			contenu = contenu.substring(0,deb)+ subString + contenu.substring(fin,contenu.length());
		}
		
		selc.setFin(selc.getFin() + subString.length());
		selc.setDebut(selc.getFin());
	}

	/**
	 * Selectionne le texte
	 * @param start : Curseur de début de selection
	 * @param stop : Curseur de fin de selection
	 */
	public void setSelection(int start,int stop) {
		selc.setDebut(start);
		selc.setFin(stop);
	}
	
	/**
	 * Copie le contenu de la selection dans le presse papier
	 */
	public void copy(){
		int deb = selc.getDebut();
		int fin = selc.getFin();
		
		String s = contenu.substring(deb,fin);
		pp.setContenu(s);
	}

	/**
	 *	supprime la selection et la met dans la presse papier
	 */
	public void cut(){
		int deb = selc.getDebut();
		int fin = selc.getFin();
		if (deb!=fin){ /* si la selection est vide on touche pas au presse papier*/
			copy();
			contenu = contenu.substring(0,deb)+contenu.substring(fin,contenu.length());
			selc.setFin(deb);/* On met le curseur au debut */
		}
	}
	
	/**
	 * Suppression de la selection actuelle
	 */
	private void deleteSelection(){
		int deb = selc.getDebut();
		int fin = selc.getFin();
		if (deb!=fin){ 
			contenu = contenu.substring(0,deb)+contenu.substring(fin,contenu.length());
			selc.setFin(deb);
		}
	}
	
	/**
	 * colle le texte du presse papier dans le texte
	 */
	public void paste(){
		String contenu = pp.getContenu();
		deleteSelection();
		insertContenu(contenu);
	}
	
}
