package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class IHM {

	int debut_selection;
	
	int fin_selection;
	
	String texte_saisi;
	
	Enregistreur enregistreur = new EnregistreurImpl();

	/**
	 * Getter du debut de selection
	 * @return
	 */
	public int getDebut_selection() {
		return debut_selection;
	}

	/**
	 * Setter du debut de selection
	 * @param debut_selection
	 */
	public void setDebut_selection(int debut_selection) {
		this.debut_selection = debut_selection;
	}

	/**
	 * Getter de la fin de selection
	 * @return
	 */
	public int getFin_selection() {
		return fin_selection;
	}

	/**
	 * Setter de la fin de selection
	 * @param fin_selection
	 */
	public void setFin_selection(int fin_selection) {
		this.fin_selection = fin_selection;
	}

	/**
	 * Getter du texte de la selection
	 * @return
	 */
	public String getTexte_saisi() {
		return texte_saisi;
	}

	/**
	 * Setter du texte de la selection
	 * @param texte_saisi
	 */
	public void setTexte_saisi(String texte_saisi) {
		this.texte_saisi = texte_saisi;
	}
	
	/**
	 * Lance l'interface graphique
	 */
	public void execute(){
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		/**	
		 * Attribut moteur : instanciation du moteur du mini-editeur
		 */
		Moteur moteur = new MoteurImpl() ;
		String inputLine;
		char commandLetter;		
		
		//Gestion des defaire refaire
		GestionDefaireRefaire gestion_defaire_refaire = new GestionDefaireRefaire(moteur);
		
		//Déclarations des classes de commandes
		Commande command_inserer = new Inserer(this,moteur);
		Commande command_copier  = new Copier(moteur);
		Commande command_couper  = new Couper(moteur);
		Commande command_coller  = new Coller(moteur);
		Commande command_selection  = new Selectionner(this,moteur);
		Commande command_demarrer_enregistrement = new DemarrerEnregistrement(enregistreur);
		Commande command_fin_enregistrement = new FinirEnregistrement(enregistreur);
		Commande command_rejouer_enregistrement = new RejouerEnregistrement(enregistreur);
		
		//Commande rejouables
		Commande commande_inserer_enreg = new InsererEnreg(this,moteur,enregistreur);
		Commande commande_copier_enreg = new CopierEnreg(moteur,enregistreur);
		Commande commande_couper_enreg = new CouperEnreg(moteur,enregistreur);
		Commande commande_coller_enreg = new CollerEnreg(moteur,enregistreur);
		
		//Commande defaire/refaire
		Commande commande_defaire = new Defaire(gestion_defaire_refaire);
		Commande commande_refaire = new Refaire(gestion_defaire_refaire);
		
		Commande commande_inserer_defaire_refaire = new InsererDefaireRefaire(command_inserer,gestion_defaire_refaire);
		Commande commande_copier_defaire_refaire = new CopierDefaireRefaire(command_copier, gestion_defaire_refaire);
		Commande commande_couper_defaire_refaire = new CouperDefaireRefaire(command_couper, gestion_defaire_refaire);
		Commande commande_coller_defaire_refaire = new CollerDefaireRefaire(command_coller, gestion_defaire_refaire);
		Commande commande_selectionner_defaire_refaire = new SelectionnerDefaireRefaire(command_selection, gestion_defaire_refaire);
		
		
		
		boolean en_cours_enreg = false;
		
		System.out.println("Welcome to MiniEditor 9.99 (c) 2015 EIT Digital Rennes") ;
		System.out.println("-----------------------------------------------------------") ;
				
		System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Z/Y/Q) > ") ;
		try
		{
			inputLine = keyboard.readLine();
		} catch (IOException e)
		{
			System.out.println("Unable to read standard input");
			inputLine = "W";
		} 
		commandLetter = Character.toUpperCase(inputLine.charAt(0)) ;
		while (commandLetter != 'Q') /* Quit */
		{
			switch (commandLetter)
			{
				case 'I': /* Insert */
					if(inputLine.length() > 2){
						setTexte_saisi(inputLine.substring(2));
						if(en_cours_enreg){
							commande_inserer_enreg.execute();
						}
						else{
							commande_inserer_defaire_refaire.execute();
						}
					}				
					
					break;
				case 'S': /* Select */
					String numberString="";
					try
					{
						String[] arguments = inputLine.substring(2).split("\\s+");
						numberString = arguments[0];
						setDebut_selection(Integer.parseInt(numberString));
						numberString = arguments[1];
						setFin_selection(Integer.parseInt(numberString));
						
						commande_selectionner_defaire_refaire.execute();
					}
					catch (Exception e)
					{
						System.out.println("Invalid number: " + numberString);
					}
					break;
				case 'C':
					if(en_cours_enreg){
						commande_copier_enreg.execute();
					}
					else{
						commande_copier_defaire_refaire.execute();
					}
					break;
				case 'X':
					if(en_cours_enreg){
						commande_couper_enreg.execute();
					}
					else{
						commande_couper_defaire_refaire.execute();
					}
					break;
				case 'V':
					if(en_cours_enreg){
						commande_coller_enreg.execute();
					}
					else{
						commande_coller_defaire_refaire.execute();
					}
					break;
				case 'D': //La suppression revient a inserer une chaine vide
					setTexte_saisi("");
					if(en_cours_enreg){
						commande_inserer_enreg.execute();
					}
					else{
						command_inserer.execute();
					}
					
					break;
				case 'R':
					command_demarrer_enregistrement.execute();
					en_cours_enreg = true;
					break;
					
				case 'E': /* End recording */
					command_fin_enregistrement.execute();
					en_cours_enreg = false;
					break;
				
				case 'P': /* Play recording */
					if(!en_cours_enreg){
						en_cours_enreg = true;
						command_rejouer_enregistrement.execute();
						en_cours_enreg = false;
					}
					break;
				case 'Z': /* undo */
					commande_defaire.execute();
					break;
					
				case 'Y': /* redo */
					commande_refaire.execute();
					break;
				default: System.out.println("Unrecognized command, please try again:") ;
					break;
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("Buffer : [" + moteur.getBufferContenu() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("Selection : [" + moteur.getSelection() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("Presse Papier: [" + moteur.getClipboard() + "]");
			System.out.println("-----------------------------------------------------");

			System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Z/Y/Q) > ") ;	
			try
			{
				inputLine = keyboard.readLine();
			} catch (IOException e)
			{
				System.out.println("Unable to read standard input");
				inputLine = "W";
			} 
			commandLetter = Character.toUpperCase(inputLine.charAt(0)) ;
		}
		System.out.println ("Goodbye") ;
	}
}
