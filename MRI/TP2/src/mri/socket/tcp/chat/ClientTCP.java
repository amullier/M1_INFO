package mri.socket.tcp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Partie 2 : Implémentation d'un chat en TCP
 * 
 * 	- Exercice 5 : Un vrai chat
 * 
 * Programme du client 
 * @author Antoine Mullier & Romain Sadok
 */
public class ClientTCP {
	//Paramètres généraux du client
	private static Scanner INPUT_CLIENT = new Scanner(System.in);
	private static int numero_port = 9999;
	private static String server_name = "127.0.0.1";
	private static String default_name = "Invité";	//Nom par défaut : pour éviter de passer un args[0] au programme
    private static String default_charset = "UTF-8"; //Encodage par défaut : pour éviter de passer un args[1] au programme
	private static int nb_threads = 1; //Nombre de threads maximal pour l'affichage en parallèle de la saisie
    public static void main(String[] args) {
    	//Création de la socket cliente
    	try {
			Socket socket = new Socket(server_name, numero_port);
			
			//Création printer et reader associés avec l'encodage défini
			String encodage_caracteres;
			if(args.length>1){
				encodage_caracteres = args[1];
	    	}
	    	else{
	    		encodage_caracteres = default_charset;
	    	}
        	System.out.println("[CONFIGURATION_CLIENT] Encodage des caractères : " + encodage_caracteres);
	    	PrintWriter printer = creerPrinter(socket,encodage_caracteres);
	    	BufferedReader reader = creerReader(socket,encodage_caracteres);
	    	
	    	
	    	//Envoi du nom par le client
	    	String nom;
	    	if(args.length>1){
	    		nom = args[0];
	    	}
	    	else{
	    		//Si pas de nom 
	    		//nom = null;
	    		nom = default_name; //Pour les tests c'est plus simple
	    	}
	    	envoyerNom(printer,nom);
	    	
	    	//Création d'un service pour l'affichage
	    	Executor service = Executors.newScheduledThreadPool(nb_threads);
	    	
	    	//Recevoir et afficher la réponse du serveur détachée dans un thread
	    	//((ScheduledExecutorService) service).scheduleAtFixedRate(new AffichageClient(reader),0,10, TimeUnit.SECONDS); //Délai TRES Grand pour voir le détachement de traitement du thread
	    	((ScheduledExecutorService) service).scheduleAtFixedRate(new AffichageClient(reader),0,100, TimeUnit.MICROSECONDS);
	    	
	    	//Lire un message au clavier
    		String saisie_clavier = lireMessageAuClavier();
	    	
	    	//Tant que le mot �fin� n�est pas lu sur le clavier,
	    	while(!saisie_clavier.toLowerCase().equals(new String("fin"))){
	    		//envoyer le message au serveur
	    		envoyerMessage(printer, saisie_clavier);
	    		
	    		//Lire un message au clavier
	    		saisie_clavier = lireMessageAuClavier();
	    	}
	    	
		} catch (SocketException e) {
			System.err.println("[ERREUR] : Probl�me lors de la connexion au serveur");
		} catch (IOException e) {
			System.err.println("[ERREUR] : Probl�me IO");
		}        
    }

    /**
     * Lit une ligne sur le scanner static du client : INPUT_CLIENT
     * @return String : ligne lue sur le scanner ou null
     */
    public static String lireMessageAuClavier(){
    	return  INPUT_CLIENT.nextLine();
    }

    /**
     * Crée un BufferedReader associé à la socket passée en paramètre
     * @param socketVersClient
     * @param encodage_caracteres : encodage des caractères du reader créé
     * @return BufferedReader : Le BufferedReader ne prend pas en compte l'encodage de caractères
     * @throws IOException : En cas de problème lors de la récupération de l'InputStream de
     *                       de la socket passée en paramètre
     */
    public static BufferedReader creerReader(Socket socketVersUnClient,String encodage_caracteres) throws IOException{
        InputStream input = socketVersUnClient.getInputStream();
        BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(input,encodage_caracteres));
        return buffer_reader;
    }

    /**
     * Crée un PrintWriter associé à la socket passée en paramètre
     * @param socketVersClient
     * @param encodage_caracteres : encodage des caractères du reader créé
     * @return PrintWriter : Le PrintWriter ne prend pas en compte l'encodage de caractères
     * @throws IOException : En cas de problème lors de la récupération de OutputStream de
     *                       de la socket passée en paramètre
     */
    public static PrintWriter creerPrinter(Socket socketVersUnClient,String encodage_caracteres) throws IOException{
        OutputStream output = socketVersUnClient.getOutputStream();
        PrintWriter buffer_writer = new PrintWriter(new OutputStreamWriter(output, encodage_caracteres), true);
        return buffer_writer;
    }

    /**
     * Récupère une ligne dans le BufferedReader passé en paramètre
     * @param reader : BufferedReader dans lequel on va lire la ligne
     * @return String : ligne lue ou null si il n'y a pas de ligne à lire
     * @throws IOException
     */
    public static String recevoirMessage(BufferedReader reader) throws
    IOException {
		return reader.readLine();
    }

    /**
     * Envoie d'un message sur le printer passé en paramètre
     * @param printer
     * @param message : si le message est null le message ne sera pas écrit
     * @throws IOException : dans le cas où il est impossible d'écrire dans le printer
     */
    public static void envoyerMessage(PrintWriter printer, String message)
    throws IOException {
    	if(message!=null){
    		printer.println(message);
    	}
    }
    
    /**
     * Envoie le nom du client au printer passé en paramètre en prenant en compte le protocole du nom utilisé
     * @param printer
     * @param nom
     * @throws IOException
     */
    public static void envoyerNom(PrintWriter printer, String nom) throws
    IOException {
        //envoi « NAME:nom » au serveur
    	if(nom!=null){
    		System.out.println("Bonjour " + nom + ",");
        	printer.println("NAME :" + nom);
    	}
    	else{
    		//Pas de nom = pas de chat
    		throw new IOException("Vous n'êtes pas authentifié, le service ne peut vous être fourni.");
    	}
    	
    }

}