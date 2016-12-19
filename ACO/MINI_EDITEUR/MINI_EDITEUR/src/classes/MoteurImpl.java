package classes;

/**
 * Classe Moteur
 * @author Antoiner Mullier et Romain Sadok
 *
 */
public class MoteurImpl implements Moteur
{
	private Buffer buffer;
	
	/**
	 * Constructeur de classe
	 * Initialise un nouveau buffer vide
	 */
	public MoteurImpl(){
		buffer = new Buffer("");
	}
	
	/**
	 * Retourne le buffer du moteur
	 * @return Buffer
	 */
	public Buffer getBuffer(){
		return buffer;
	}
	
	/**
	 * Getter sur le contenu du buffer
	 */
	@Override
	public String getBufferContenu()
	{
		return buffer.getContenu() ;
	}

	/**
	 * Getter sur le contenu de la selection
	 */
	@Override
	public String getSelection()
	{
		Selection s = buffer.getSelection();
		int deb = s.getDebut();
		int fin = s.getFin();
		
		return buffer.getContenu().substring(deb,fin);
	}
	
	/**
	 * Setter de la selection 
	 * @param s : Selection
	 */
	public void setSelection(Selection s){
		buffer.setSelection(s.getDebut(), s.getFin());
	}
	
	/**
	 * Setter du contenu du buffer
	 * @param contenu
	 */
	public void setContenu(String contenu){
		buffer.setContenu(contenu);
	}

	/**
	 * Getter du contenu du presse papier
	 */
	@Override
	public String getClipboard()
	{
		return buffer.getPressePapier().getContenu();
		
	}

	/**
	 * Commande inserer du buffer
	 * @param : String à insérer
	 */
	@Override
	public void inserer(String substring)
	{		
		buffer.insertContenu(substring);
		
	}

	/**
	 * Commande seletionner 
	 * @param start : debut de la selection
	 * @param stop : fin de la selection
	 */
	@Override
	public void selectionner(int start, int stop)
	{
		int tmp;
		
		//Dans le cas ou la selection est "a l'envers"
		if(start > stop){
			tmp = stop;
			stop = start;
			start = tmp;
		}
		
		//
		if(stop > buffer.getContenu().length()){
			stop = buffer.getContenu().length();
			
			if(start > buffer.getContenu().length()){
				start = buffer.getContenu().length();
			}
		}
		
		buffer.setSelection(start,stop);
	}

	/**
	 * Commande Copier
	 */
	@Override
	public void copier()
	{
		buffer.copy();
	}

	/**
	 * Commande couper
	 */
	@Override
	public void couper() 
	{
		buffer.cut();
	}

	/**
	 * Commande coller
	 */
	@Override
	public void coller()
	{		
		buffer.paste();
	}
}
