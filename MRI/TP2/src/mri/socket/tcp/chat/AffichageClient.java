package mri.socket.tcp.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class AffichageClient implements Runnable{
	BufferedReader reader;
	
	public AffichageClient(BufferedReader reader) {
		super();
		this.reader = reader;
	}

	public void run() {
		try {
			String message = ClientTCP.recevoirMessage(reader);
			if(message!=null){
				System.out.println(message);
			}
		} catch (IOException e) {
			System.err.println("[ERREUR]:Probleme lors de la lecture du buffer de la socket");
			e.printStackTrace();
		}
	}

}
