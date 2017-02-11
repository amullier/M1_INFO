package mri.socket.tcp.thread;

import java.io.IOException;
import java.net.Socket;

public class TraiteUnClient implements Runnable{
	Socket socketVersUnClient;
	String encodage_caracteres;
	

	/**
	 * Conctructeur de classe
	 * @param socketVersUnClient
	 * @param encodage_caracteres
	 */
	public TraiteUnClient(Socket socketVersUnClient, String encodage_caracteres) {
		super();
		this.socketVersUnClient = socketVersUnClient;
		this.encodage_caracteres = encodage_caracteres;
	}
	
	/**
	 * Procédure d'execution lors de l'appel de thread
	 */
	public void run() {
		try {
			ServeurTCP.traiterSocketCliente(socketVersUnClient, encodage_caracteres);
		} catch (IOException e) {
			System.err.println("[ERREUR]: Un probleme est survenu lors de la gestion de la socket cliente");
			e.printStackTrace();
		}		
	}
	

}
