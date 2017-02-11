package mri.socket.tcp.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Partie 2 : Implémentation d'un chat en TCP
 * 
 * 	- Exercice 1: serveur et client Echo
 * 
 * Programme du serveur
 * @author Antoine Mullier & Romain Sadok
 */
public class ServeurTCP {
	//Paramètres généraux du serveur
	private static int numero_port = 9999;
	private static int nb_clients_max = 3; //Longueur maximale de la queue d'attente de connexions
		
    public static void main(String[] args) {
    	ServerSocket serveur_socket;
    	Socket socket_recue;
    	boolean serveur_run = true;
    	
    	try {
    		//On créer le serveur sur le port 9999 pouvant accepter 3 clients
			serveur_socket = new ServerSocket(numero_port,nb_clients_max);
			
			System.out.println("Création du serveur. (" + nb_clients_max + " clients max )");
			
			System.out.println("Ecoute sur le port n°" + numero_port);
			
			//Dans une boucle, pour chaque socket clientes, appeler traiterSocketCliente
			while(serveur_run) {
				socket_recue =  serveur_socket.accept();
				System.out.println("Connexion établie avec la machine d'adresse : " + socket_recue.getInetAddress().getHostAddress());
				try{
					traiterSocketCliente(socket_recue);
				}
				catch(IOException e){
					serveur_run = false;
					System.out.println("[ERREUR] : Un problème est survenue lors du traitement du client " + socket_recue.getInetAddress().getHostAddress() + ".\n Arrêt du serveur");
				}
		    }
			
		} catch (IOException e) {
			System.err.println("[ERREUR] : Un problème est survenu lors de la création du ServerSocket sur le port " + numero_port);
			e.printStackTrace();
		}
    }

    /**
     * Procédure qui traite la totalité d'une socket cliente
     * Lorsque le serveur reçoit un paquet dans le reader de la socket il écrit le même
     * paquet dans le printer de la socket
     * @param socketVersUnClient : socket du client
     * @throws IOException
     */
    public static void traiterSocketCliente(Socket socketVersUnClient) throws IOException{
    	String message;
    	boolean lire_buffer=true;
    	
    	//Création printer et reader associés à la socket
    	PrintWriter printer = creerPrinter(socketVersUnClient);
    	BufferedReader reader = creerReader(socketVersUnClient);
		
        //Tant qu'il y a un message le lire via recevoirMessage
    	while(lire_buffer){
    		try{
    			message = recevoirMessage(reader);
    			if(message==null){
    				lire_buffer = false;
    			}
    			
    			//Envoyer message au client via envoyerMessage
        		try{
        			envoyerMessage(printer, message);
        		}
        		catch(IOException e){
        			System.err.println("[ERREUR] : Problème lors de l'envoi");
        			lire_buffer=false;
        			socketVersUnClient.close();
        		}
    		}
    		catch(IOException e){
    			System.err.println("[ERREUR] : Problème lors de la reception");
    			lire_buffer=false;
    			socketVersUnClient.close();
    		}    		
    	}

        //Si plus de ligne à lire on ferme la socket cliente	
    	System.out.println("Le client " + socketVersUnClient.getInetAddress().getHostAddress() + " a fermé la connexion.");
    	socketVersUnClient.close();
    }

    /**
     * Crée un BufferedReader associé à la socket passée en paramètre
     * @param socketVersClient
     * @return BufferedReader : Le BufferedReader ne prend pas en compte l'encodage de caractères
     * @throws IOException : En cas de problème lors de la récupération de l'InputStream de
     * 						 de la socket passée en paramètre
     */
    public static BufferedReader creerReader(Socket socketVersClient) throws IOException {
    	InputStream input = socketVersClient.getInputStream();
    	BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(input));
		return buffer_reader;
    }

    /**
     * Crée un PrintWriter associé à la socket passée en paramètre
     * @param socketVersClient
     * @return PrintWriter : Le PrintWriter ne prend pas en compte l'encodage de caractères
     * @throws IOException : En cas de problème lors de la récupération de OutputStream de
     * 						 de la socket passée en paramètre
     */
    public static PrintWriter creerPrinter(Socket socketVersClient) throws IOException {
    	OutputStream output = socketVersClient.getOutputStream();
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