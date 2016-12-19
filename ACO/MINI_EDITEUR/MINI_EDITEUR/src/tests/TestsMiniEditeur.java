package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import classes.Enregistreur;
import classes.EnregistreurImpl;
import classes.GestionDefaireRefaire;
import classes.IHM;
import classes.Moteur;
import classes.MoteurImpl;
import classes.Selection;
import commandes.Coller;
import commandes.CollerDefaireRefaire;
import commandes.CollerEnreg;
import commandes.Commande;
import commandes.Copier;
import commandes.CopierDefaireRefaire;
import commandes.CopierEnreg;
import commandes.Couper;
import commandes.CouperDefaireRefaire;
import commandes.CouperEnreg;
import commandes.Defaire;
import commandes.DemarrerEnregistrement;
import commandes.FinirEnregistrement;
import commandes.Inserer;
import commandes.InsererDefaireRefaire;
import commandes.InsererEnreg;
import commandes.Refaire;
import commandes.RejouerEnregistrement;
import commandes.Selectionner;
import commandes.SelectionnerDefaireRefaire;

public class TestsMiniEditeur {
	IHM ihm;
	Moteur moteur;
	Enregistreur enregistreur;
	
	Commande command_inserer;
	Commande command_copier;
	Commande command_couper;
	Commande command_coller;
	Commande command_selection;
	
	
	Commande command_debut_enregistrement;
	Commande command_fin_enregistrement;
	Commande command_rejouer_enregistrement;
	
	//Commande rejouables
	Commande commande_inserer_enreg;
	Commande commande_copier_enreg;
	Commande commande_couper_enreg;
	Commande commande_coller_enreg;
	
	//Commande defaire/refaire
	Commande commande_defaire;
	Commande commande_refaire;
	
	Commande commande_inserer_defaire_refaire;
	Commande commande_copier_defaire_refaire;
	Commande commande_couper_defaire_refaire;
	Commande commande_coller_defaire_refaire;
	Commande commande_selectionner_defaire_refaire;
	
	
	/**
	 * Generate
	 * @param length
	 * @return
	 */
	private static String generateString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
        StringBuffer pass = new StringBuffer();
        for(int x=0;x<length;x++)   {
           int i = (int)Math.floor(Math.random() * (chars.length() -1));
           pass.append(chars.charAt(i));
        }
        return pass.toString();
	}
	
	/**
	 * Genere une selection aléatoire
	 * @param borneSuperieure
	 * @param ecartMaximum
	 * @return
	 */
	private static Selection generateSelection(int borneSuperieure,int ecartMaximum){
		int debutSelection = (int)Math.random()*borneSuperieure;
		int finSelection = debutSelection + ((int)Math.random()*ecartMaximum);
		return new Selection(debutSelection, finSelection);
	}
	
	/**
	 * Insère un texte aleatoire de longueur 
	 * @param longueurTexte
	 */
	private void remplirBuffer(int longueurTexte){
		String textInserer = generateString(longueurTexte);
		ihm.setTexte_saisi(textInserer);
		command_inserer.execute();
	}
	
	
	/**
	 * Execute une selection 
	 * @param selection
	 * */
	private void executeSelec( Selection selection ){
		ihm.setDebut_selection(selection.getDebut());
		ihm.setFin_selection(selection.getFin());
		command_selection.execute();
	}
	/****************************************/
	
	
	@Before
	public void initTest(){
		ihm = new IHM();
		moteur = new MoteurImpl();
		
		//Gestion des defaire refaire
		GestionDefaireRefaire gestion_defaire_refaire = new GestionDefaireRefaire(moteur);
		
		//Déclarations des classes de commandes
		command_inserer = new Inserer(ihm,moteur);
		command_copier  = new Copier(moteur);
		command_couper  = new Couper(moteur);
		command_coller  = new Coller(moteur);
		command_selection  = new Selectionner(ihm,moteur);
		
		enregistreur = new EnregistreurImpl();
		
		//Commmandes d'actions de l'enregistreur
		command_debut_enregistrement = new DemarrerEnregistrement(enregistreur);
		command_fin_enregistrement = new FinirEnregistrement(enregistreur);
		command_rejouer_enregistrement = new RejouerEnregistrement(enregistreur);
		
		//Commande rejouables
		commande_inserer_enreg = new InsererEnreg(ihm,moteur,enregistreur);
		commande_copier_enreg = new CopierEnreg(moteur,enregistreur);
		commande_couper_enreg = new CouperEnreg(moteur,enregistreur);
		commande_coller_enreg = new CollerEnreg(moteur,enregistreur);
		
		//Commande defaire/refaire
		commande_defaire = new Defaire(gestion_defaire_refaire);
		commande_refaire = new Refaire(gestion_defaire_refaire);
		
		commande_inserer_defaire_refaire = new InsererDefaireRefaire(command_inserer,gestion_defaire_refaire);
		commande_copier_defaire_refaire = new CopierDefaireRefaire(command_copier, gestion_defaire_refaire);
		commande_couper_defaire_refaire = new CouperDefaireRefaire(command_couper, gestion_defaire_refaire);
		commande_coller_defaire_refaire = new CollerDefaireRefaire(command_coller, gestion_defaire_refaire);
		commande_selectionner_defaire_refaire = new SelectionnerDefaireRefaire(command_selection, gestion_defaire_refaire);
	}
	
	/***********************************************************************************
	 *                                VERSION 1
	 ***********************************************************************************/
	
	/**
	 * Test Insertion simple et Insertion multiple
	 */
	@Test 
 	public void testInsertionSimple() {
		String textInserer = "hello";
		ihm.setTexte_saisi(textInserer);
		command_inserer.execute();
 		assertEquals(moteur.getBufferContenu(),textInserer);	
	}
	
	@Test
	public void testInsertionMultiple(){
		String input = "";
		String textInserer = generateString(2);
		ihm.setTexte_saisi(textInserer);
		command_inserer.execute();
		for (int i = 0; i < 15; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			command_inserer.execute();
			textInserer = textInserer + input;
		}
		assertEquals(moteur.getBufferContenu(),textInserer);
		
	}

	/**
	 * Test de selection
	 */
	@Test
	public void testSelection(){
		int borneSuperieure = 100;
		int nbTests = 15;
		boolean correctTest = true;
		
		remplirBuffer(borneSuperieure);
		
		//On teste plusieurs selections positive
		for (int i = 0; i < nbTests; i++) {
			Selection testSelection = generateSelection(borneSuperieure, 10);
			
			ihm.setDebut_selection(testSelection.getDebut());
			ihm.setFin_selection(testSelection.getFin());
			
			command_selection.execute();
			
			correctTest = correctTest&&(testSelection.getFin() - testSelection.getDebut()== moteur.getSelection().length());
		}

		//On teste plusieurs selections negatif
		for (int i = 0; i < nbTests; i++) {
			Selection testSelection = generateSelection(borneSuperieure, -10);
			
			ihm.setDebut_selection(testSelection.getDebut());
			ihm.setFin_selection(testSelection.getFin());
			
			command_selection.execute();
			
			correctTest = correctTest&&(testSelection.getFin() - testSelection.getDebut()== moteur.getSelection().length());
		}
		
		assertTrue(correctTest);
		
	}

	/**
	 * Test Copier : on regarde si le contenu du presse papier est correct 
	 */
	
	@Test
	public void testCopier(){
		int borneSuperieure = 100;
		remplirBuffer(borneSuperieure);
		
		Selection selectionACopier = generateSelection(borneSuperieure, 16);
		executeSelec(selectionACopier);
		
		command_copier.execute();
		
		assertEquals(moteur.getClipboard(),moteur.getSelection());
		
	}

	/**
	 * Test Couper1 : on regarde si le contenu du presse papier est correct
	 */
	@Test
	public void testCouper(){
		int borneSuperieure = 100;
		remplirBuffer(borneSuperieure);
		
		Selection selectionACouper = generateSelection(borneSuperieure, 16);
		executeSelec(selectionACouper);
		
		command_couper.execute();
		
		assertEquals(moteur.getClipboard(),moteur.getSelection());
	}
	
	/**
	 * Test Couper2 : on regarde si la selection a ete supprimee dans le buffer
	 */
	@Test
	public void testCouper2(){
		int borneSuperieure = 100;
		remplirBuffer(borneSuperieure);
		
		int longueurBufferAvantCouper = moteur.getBufferContenu().length();
		
		Selection selectionACouper = generateSelection(borneSuperieure, 16);
		executeSelec(selectionACouper);
		
		int texteSelectionneLongueur = moteur.getSelection().length();
		command_couper.execute();
		
		assertEquals(moteur.getBufferContenu().length(),longueurBufferAvantCouper - texteSelectionneLongueur);
	}
	
	/**
	 * Test Coller : On regarde si le couper/coller ne change pas le buffer
	 */
	@Test
	public void testCollerSansChangementSelection(){
		int borneSuperieure = 100;
		remplirBuffer(borneSuperieure);
		
		Selection selectionACouper = generateSelection(borneSuperieure, 16);
		executeSelec(selectionACouper);
		
		String contenuBufferAvant = moteur.getBufferContenu();
		command_couper.execute();
		
		command_coller.execute();
		
		String contenuBufferApres = moteur.getBufferContenu();
		
		assertEquals(contenuBufferAvant,contenuBufferApres);
	}
	
	/**
	 * Test Coller : On regarde si un couper/coller en changeant le curseur ne change pas la longueur du buffer
	 */
	@Test
	public void testCollerAvecChangementSelection(){
		int borneSuperieure = 100;
		remplirBuffer(borneSuperieure);
		
		Selection selectionACouper = generateSelection(borneSuperieure, 16);
		executeSelec(selectionACouper);
		
		int longueurBufferAvant = moteur.getBufferContenu().length();
		command_couper.execute();
		
		//On place le curseur a la fin
		int longueurBuffer = moteur.getBufferContenu().length();
		executeSelec(new Selection(longueurBuffer,longueurBuffer));
		
		command_coller.execute();
		
		int longueurBufferApres = moteur.getBufferContenu().length();
		
		assertEquals(longueurBufferAvant,longueurBufferApres);
	}
	
	/***********************************************************************************
	 *                                VERSION 2
	 ***********************************************************************************/
	
	/**
	 * Enregistrement
	 */
	@Test
	public void EnregistrementScenarioInserer(){
		int borneMax = 10;
		
		command_debut_enregistrement.execute();
		String input = "";
		String textInserer = generateString(2);
		ihm.setTexte_saisi(textInserer);
		commande_inserer_enreg.execute();
		for (int i = 0; i < borneMax; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			commande_inserer_enreg.execute();
			textInserer = textInserer + input;
		}
		
		String contenu_copy = new String(moteur.getBufferContenu());
		
		command_fin_enregistrement.execute();
		
		command_rejouer_enregistrement.execute();
		
		String contenu_copy2 = new String(moteur.getBufferContenu());
		
		assertEquals((contenu_copy.length()*2),contenu_copy2.length() );
	}
	
	/**
	 * Enregistrement
	 * On enregistre une sequence de commande inserer puis on vide le buffer
	 * On vide le buffer
	 * On rejoue le scenario pour voir si on obtient le meme buffer
	 */
	@Test
	public void EnregistrementScenarioInserer2(){
		int borneMax = 10;
		
		command_debut_enregistrement.execute();
		String input = "";
		String textInserer = generateString(2);
		ihm.setTexte_saisi(textInserer);
		commande_inserer_enreg.execute();
		for (int i = 0; i < borneMax; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			commande_inserer_enreg.execute();
			textInserer = textInserer + input;
		}
		
		String contenu_copy = new String(moteur.getBufferContenu());
		
		command_fin_enregistrement.execute();
		
		moteur.getBuffer().setSelection(0, 0);
		moteur.getBuffer().setContenu("");
		
		command_rejouer_enregistrement.execute();
		
		String contenu_copy2 = new String(moteur.getBufferContenu());
		
		assertEquals(contenu_copy,contenu_copy2);
	}
	
	
	/***********************************************************************************
	 *                                VERSION 3
	 ***********************************************************************************/
	
	/**
	 * Test Defaire sur une liste d'instruction vide
	 */
	@Test
	public void testDefaireSurMoteurVide(){
		int borneMax = 10;
		
		String contenu_copy = moteur.getBufferContenu();
		
		for (int i = 0; i <= borneMax; i++) {
			commande_defaire.execute();
		}
		
		
		String contenu_copy2 = moteur.getBufferContenu();
		
		assertEquals(contenu_copy,contenu_copy2);
	}
	
	
	/**
	 * Test refaire sur une liste d'instruction vide
	 */
	@Test
	public void testRefaireSurMoteurVide(){
		int borneMax = 10;
		
		String contenu_copy = moteur.getBufferContenu();
		
		for (int i = 0; i <= borneMax; i++) {
			commande_refaire.execute();
		}
		
		
		String contenu_copy2 = moteur.getBufferContenu();
		
		assertEquals(contenu_copy,contenu_copy2);
	}
	
	/**
	 * Defaire Refaire
	 * On remplit le buffer vide avec une suite de commande que l'on va defaire integralement
	 */
	@Test
	public void testDefaire1(){
		int borneMax = 10;
		
		String contenu_copy = moteur.getBufferContenu();
		
		String input = "";
		String textInserer = generateString(2);
		ihm.setTexte_saisi(textInserer);
		commande_inserer_defaire_refaire.execute();
		for (int i = 0; i < borneMax; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			commande_inserer_defaire_refaire.execute();
			textInserer = textInserer + input;
		}
		
		for (int i = 0; i <= borneMax; i++) {
			commande_defaire.execute();
		}
		
		
		String contenu_copy2 = moteur.getBufferContenu();
		
		assertEquals(contenu_copy,contenu_copy2);
	}
	
	/**
	 * D�faire Refaire
	 * Meme test que pr�c�demment sauf que le buffer n'est pas vide
	 */
	@Test
	public void testDefaire2(){
		int borneMax = 10;
		

		String input = generateString(2);
		ihm.setTexte_saisi(input);
		commande_inserer_defaire_refaire.execute();
		for (int i = 0; i < borneMax; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			commande_inserer_defaire_refaire.execute();
		}
		
		String contenu_copy = moteur.getBufferContenu();

		input = generateString(2);
		ihm.setTexte_saisi(input);
		commande_inserer_defaire_refaire.execute();
		for (int i = 0; i < borneMax; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			commande_inserer_defaire_refaire.execute();
		}
		
		for (int i = 0; i <= borneMax; i++) {
			commande_defaire.execute();
		}
		
		
		String contenu_copy2 = moteur.getBufferContenu();
		
		assertEquals(contenu_copy,contenu_copy2);
	}
	
	/**
	 * Test refaire
	 * On effectue un sc�nario on le d�fait et on le refait
	 */
	@Test
	public void testRefaire1(){
		int borneMax = 100;
		
		String input = generateString(2);
		ihm.setTexte_saisi(input);
		commande_inserer_defaire_refaire.execute();
		for (int i = 0; i < borneMax; i++) {
			input = generateString(2);
			ihm.setTexte_saisi(input);
			commande_inserer_defaire_refaire.execute();
		}
		
		String contenu_copy = moteur.getBufferContenu();
		
		for (int i = 0; i <= borneMax; i++) {
			commande_defaire.execute();
		}
		
		for (int i = 0; i <= borneMax; i++) {
			commande_refaire.execute();
		}
		
		String contenu_copy2 = moteur.getBufferContenu();
		
		assertEquals(contenu_copy,contenu_copy2);
	}
	
	
}
