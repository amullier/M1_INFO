package mri.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Partie 3 - Chat UDP MULTICAST 
 * @author Antoine Mullier & Romain Sadok
 */
public class ChatMultiCast {
	static int TAILLE_MESSAGE = 1024;
	static Scanner INPUT_CLIENT = new Scanner(System.in);
	static InetAddress group ;
	static int port = 9999;
	static String plage_ip = "225.0.4.255";
	static int nb_threads_max = 1;
	static String nom_client = "Invité";
	
	public static void main(String[] args) {
		
		if(args.length > 0){
			nom_client = args[0];
		}


		Executor service_reception = null;
		try {
			group = InetAddress.getByName(plage_ip);
			try {
				MulticastSocket socket = new MulticastSocket(port);
				socket.joinGroup(group);

				System.out.println("Connecté en tant que " + nom_client + " sur le port n°" + port + " à l'adresse " + plage_ip);
				
				//Reception
				//Creation du service de reception
				service_reception = Executors.newScheduledThreadPool(nb_threads_max);
				
				//On lance le thread d'affichage toutes les 100ms
				((ScheduledExecutorService) service_reception).scheduleAtFixedRate(new AffichageClient(socket),0,100, TimeUnit.MICROSECONDS);
				
				//Boucle d'envoi
				String message;
				do{
					message = lireMessageAuClavier();
					if(message!=null){
						envoyerMessage(socket, message);
					}
				}
				while(message!=null&&!message.equals("fin"));
				
				((ScheduledExecutorService) service_reception).shutdown();
				
				//On quitte le groupe
				socket.leaveGroup(group);
				
				//Fermeture de la socket
				socket.close();
				
			} catch (IOException e) {
				System.err.println("[ERREUR]:Le port "+ port +" n'est pas disponible ");
				((ScheduledExecutorService) service_reception).shutdown();
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			System.err.println("[ERREUR]: La plage " + plage_ip + " n'est pas disponible.");
			e.printStackTrace();
		}
	}

	/**
	 * Envoi d'un message MULTICAST sur la socket passée en paramètre
	 * @param s : socket sur laquelle envoyée les données
	 * @param message : message a envoyé sur la socket
	 * @throws UnknownHostException
	 */
	public static void envoyerMessage(MulticastSocket s, String message) throws UnknownHostException{
		String message_envoi  = new String(nom_client + ">" + message);
		DatagramPacket datagramme = new DatagramPacket(message_envoi.getBytes(), message_envoi.length(),group, port);
		try {
			s.send(datagramme);
		} catch (IOException e) {
			System.err.println("[ERREUR]: Probleme lors de l'envoi du datagramme.");
		}
	}

	/**
	 * Reception d'un message sur une socket passée en paramètre
	 * @param s : socket sur laquelle lire
	 * @return String : ligne lue sur la socket
	 */
	public static String recevoirMessage(MulticastSocket s){
		byte[] buf = new byte[TAILLE_MESSAGE];
		DatagramPacket recv = new DatagramPacket(buf, buf.length);
		try {
			s.receive(recv);
		} catch (IOException e) {
			System.err.println("[ERREUR]: Probleme lors de la reception du datagramme.");
			e.printStackTrace();
		}
		return new String(recv.getData());
	}

	/**
     * Lit une ligne sur le scanner static du client : INPUT_CLIENT
     * @return String : ligne lue sur le scanner ou null
     */
    public static String lireMessageAuClavier(){
    	return  INPUT_CLIENT.nextLine();
    }
}
