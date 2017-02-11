package mri.socket.address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Partie 1 : Adressage et rappels
 * @author Antoine Mullier & Romain Sadok
 */
public class AfficheInterfaces {
	
	/**
	 * Affichage d'une interface
	 * @param netint
	 */
	static void afficherDetailsInterface(NetworkInterface netint){
		//Affichage du nom de l'interface
        System.out.println(netint.getName() + ":" + netint.getDisplayName());
        
        //Affichage de toutes les adresses liées à l'interface
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        	System.out.println("->" + inetAddress);
        }
     }
	
	/**
	 * Programme principal : Affichage de toutes les interfaces et des adresses liées
	 * de la machine
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			
			for(NetworkInterface net_interface : Collections.list(interfaces)){
				afficherDetailsInterface(net_interface);
			}
			
		} catch (SocketException e) {
			System.out.println("[ERREUR] : Un problème est survenu lors de la détection des interfaces réseau de la machine");
			e.printStackTrace();
		}

	}

}
