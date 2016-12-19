package classes;

/**
 * Classe contenant le main du projet
 * @author Antoine Mullier et Romain Sadok
 */
public class Editeur
{
	

	/**
	 * Programme lancant le projet mini-editeur
	 * @param args
	 */
	public static void main(String[] args)
	{
		IHM ihm = new IHM();
		ihm.execute();
	}
}
