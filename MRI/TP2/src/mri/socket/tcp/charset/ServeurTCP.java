package mri.socket.tcp.charset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Partie 2 : Implémentation d'un chat en TCP
 * 
 *  - Exercice 3 : Encodage de caractères
 * 
 * Programme du serveur 
 * @author Antoine Mullier & Romain Sadok
 */
public class ServeurTCP {
	//Paramètres généraux du serveur
    private static int numero_port = 9999;
    private static int nb_clients_max = 3; //Longueur maximale de la queue d'attente de connexions
    private static String default_charset = "UTF-8";

    public static void main(String[] args) {
        ServerSocket serveur_socket;
        Socket socket_recue;
        boolean serveur_run = true;

        //On définit l'encodage des caractères du serveur
        String encodage_caracteres;
        if(args.length>0){
            encodage_caracteres = args[0];
        }
        else{
            encodage_caracteres = default_charset;
        }
        System.out.println("[CONFIGURATION_SERVEUR] Encodage des caractères : " + encodage_caracteres);
        
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
                    traiterSocketCliente(socket_recue,encodage_caracteres);
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
     * @param encodage_caracteres : encodage des caractères du serveur
     * @throws IOException
     */
    public static void traiterSocketCliente(Socket socketVersUnClient, String encodage_caracteres) throws IOException{
    	String message;
        boolean lire_buffer=true;
        String nom_client = null;
    	
        //Création printer et reader associés à la socket
        PrintWriter printer = creerPrinter(socketVersUnClient,encodage_caracteres);
		BufferedReader reader = creerReader(socketVersUnClient,encodage_caracteres);
		
        //Pour le premier paquet on récupère le nom du client
        try{
            nom_client = avoirNom(reader);
        }
        catch(IOException e){
            System.err.println("[ERREUR] : Le nom du client " + socketVersUnClient.getInetAddress().getHostAddress() + "n'est pas déterminable");
            lire_buffer=false;
            socketVersUnClient.close();
        }
        catch(NullPointerException e){
            System.err.println("[ERREUR] : Le nom du client " + socketVersUnClient.getInetAddress().getHostAddress() + "n'est pas déterminable");
            lire_buffer=false;
            socketVersUnClient.close();
        }
        
        while(lire_buffer){
            try{
                message = recevoirMessage(reader);
                if(message==null){
                    lire_buffer = false;
                }
                
                //Envoyer message au client via envoyerMessage
                try{
                    envoyerMessage(printer,nom_client, message);
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
    public static String recevoirMessage(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * Envoie d'un message sur le printer passé en paramètre
     * @param printer
     * @param message : si le message est null le message ne sera pas écrit
     * @throws IOException : dans le cas où il est impossible d'écrire dans le printer
     */
    public static void envoyerMessage(PrintWriter printer,String nom_client, String message)
    throws IOException {
        if(message!=null){
            printer.println(nom_client + ">" + message);
        }
    }
    
    /**
     * Retourne le nom du client avec le reader de la socket
     * @pre le client a envoyé dans le reader de la socket serveur son nom
     *      avec le protocole "NAME:<nom>"
     * @param reader : Reader de la socket serveur
     * @return String nom du client
     * @throws IOException
     */
    public static String avoirNom(BufferedReader reader) throws IOException,NullPointerException
    {
        String input = reader.readLine();
        String[] reponse = input.split(":");
        if(reponse.length>1){
            return reponse[1];
        }
        else{
            //Problème le nom n'est pas consultable
            throw new IOException("Le nom du client n'est pas récupérable");
        }
        
    }
}