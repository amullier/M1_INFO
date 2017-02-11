package mri.socket.tcp.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Partie 2 : Implémentation d'un chat en TCP
 * 
 * 	- Exercice 1: serveur et client Echo
 * 
 * Programme du client 
 * @author Antoine Mullier & Romain Sadok
 */
public class ClientTCP {
	//Paramètres généraux du client
	private static Scanner INPUT_CLIENT = new Scanner(System.in);
	private static int numero_port = 9999;
	private static String server_name = "127.0.0.1";
	
    public static void main(String[] args) {    	
        //Création de la socket cliente
    	try {
			Socket socket = new Socket(server_name, numero_port);
			
			System.out.println("Connecté à la machine "+ server_name + " sur le port n°" + numero_port);
			
			//Création printer et reader associés à la socket
	    	PrintWriter printer = creerPrinter(socket);
	    	BufferedReader reader = creerReader(socket);
	    	
    		String saisie_clavier , message_recu;
    		System.out.println("Vous pouvez saisir du texte :");
    		do{
    			//Récupération de la saisie clavier du client
    			saisie_clavier = lireMessageAuClavier();
    			if(saisie_clavier!=null){
    				//Envoi du message au serveur
    				envoyerMessage(printer, saisie_clavier);
    			}
    			
    			//Reception du message du serveur
    			message_recu = recevoirMessage(reader);
    			if(message_recu!=null){
    				System.out.println("SERVEUR>" + message_recu);
    			}
    		}
	    	while(saisie_clavier!=null&&!saisie_clavier.toLowerCase().equals("fin"));
    		
    		//Fermeture de la socket
    		socket.close();
	    	
		} catch (SocketException e) {
			System.err.println("[ERREUR] : Probl�me lors de la connexion au serveur");
		} catch (IOException e) {
			System.err.println("[ERREUR] : Probleme IO");
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
     * @param socketDuClient
     * @return BufferedReader : Le BufferedReader ne prend pas en compte l'encodage de caractères
     * @throws IOException : En cas de problème lors de la récupération de l'InputStream de
     * 						 de la socket passée en paramètre
     */
    public static BufferedReader creerReader(Socket socketDuClient) throws IOException {
    	InputStream input = socketDuClient.getInputStream();
    	BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(input));
		return buffer_reader;
    }

    /**
     * Crée un PrintWriter associé à la socket passée en paramètre
     * @param socketDuClient
     * @return PrintWriter : Le PrintWriter ne prend pas en compte l'encodage de caractères
     * @throws IOException : En cas de problème lors de la récupération de OutputStream de
     * 						 de la socket passée en paramètre
     */
    public static PrintWriter creerPrinter(Socket socketDuClient) throws IOException {
    	OutputStream output = socketDuClient.getOutputStream();
    	PrintWriter buffer_writer = new PrintWriter(output,true);
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
}