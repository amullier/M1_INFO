package classes;

import java.util.Stack;
import commandes.CommandeDefaireRefaire;
import commandes.SelectionnerDefaireRefaire;
import memento.Memento;
import memento.MementoEtatMoteur;

/**
 * Gestionnaire de pile pour les actions defaire/refaire
 * Receiver de notre pattern Commande 3
 * Caretaker memento 2
 * @since Version 3
 * @author Antoine Mullier et Romain Sadok
 */
public class GestionDefaireRefaire {

	private Stack<EtatDefaireRefaire> pile_defaire;
	private Stack<EtatDefaireRefaire> pile_refaire;
	private Moteur moteur;
	private final int FREQUENCE_ENREG_MEMENTO = 1;
	
	/**
	 * Constructeur de classe qui cr�e les deux piles defaire et refaire
	 * @param Moteur : moteur du mini-editeur
	 */
	public GestionDefaireRefaire(Moteur moteur) {
		super();
		this.moteur = moteur;
		pile_defaire = new Stack<EtatDefaireRefaire>();
		pile_refaire = new Stack<EtatDefaireRefaire>();
	}

	/**
	 * Fonction defaire : on revient dans l'�tat pr�c�dent
	 */
	public void defaire(){
		if(!pile_defaire.isEmpty()){
			pile_refaire.push(pile_defaire.pop());
			update_moteur();
		}
	}
	
	/**
	 * Fonction refaire : on revient dans le premier etat de la pile refaire
	 */
	public void refaire(){
		if(!pile_refaire.isEmpty()){
			pile_defaire.push(pile_refaire.pop());
			update_moteur();
		}		
	}
	
	/**
	 * Ajout d'une commande dans la pile de l'historique du mini-editeur
	 * On ajoute le m�mento qu'une fois sur FREQUENCE_ENREG_MEMENTO
	 * 
	 * @param CommandeDefaireRefaire : Commande a ajouter
	 */
	public void ajoutCommandeDefaireRefaire(CommandeDefaireRefaire c){
		Memento mementoAEnregistrer;
		
		if((pile_defaire.size()%FREQUENCE_ENREG_MEMENTO==0)||(c instanceof SelectionnerDefaireRefaire)){	//1 fois sur FREQUENCE_ENREG_MEMENTO on ajoute le memento
			mementoAEnregistrer  = c.getMemento();
		}
		else{
			mementoAEnregistrer = null;
		}
		
		pile_defaire.add(new EtatDefaireRefaire(c, mementoAEnregistrer));
		
		//On supprime l'ancien "futur"
		pile_refaire.clear();
	}
	
	/**
	 * Fonction qui met � jour le moteur avec l'�tat de sommet de pile
	 * de la pile defaire
	 */
	private Memento update_moteur(){
		MementoEtatMoteur dernierMementoEnregistre = null;
		
		//On regarde l'�tat de la pile defaire
		if(!pile_defaire.isEmpty()){
			EtatDefaireRefaire etat = pile_defaire.peek();
			
			dernierMementoEnregistre = (MementoEtatMoteur)etat.getMemento();
			
			//Si le memento est null on va chercher l'�tat avant et executer la commande de l'etat
			if(dernierMementoEnregistre==null){
				CommandeDefaireRefaire commandeAExecuter = (CommandeDefaireRefaire)etat.getCommande();
				etat = pile_defaire.pop();
				dernierMementoEnregistre = (MementoEtatMoteur) update_moteur();
				commandeAExecuter.setMemento(dernierMementoEnregistre);
				commandeAExecuter.execute();
			}
			else{
				//On met � jour le moteur
				((MoteurImpl) moteur).setContenu(dernierMementoEnregistre.getContenu());
				
				((MoteurImpl) moteur).setSelection(dernierMementoEnregistre.getSelection());
			}
		}
		else{
			((MoteurImpl) moteur).setContenu("");
			((MoteurImpl) moteur).setSelection(new Selection(0,0));
		}
		
		return dernierMementoEnregistre;
	}
}
