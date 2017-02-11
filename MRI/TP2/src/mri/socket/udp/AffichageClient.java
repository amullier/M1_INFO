package mri.socket.udp;

import java.net.MulticastSocket;

public class AffichageClient implements Runnable{
	MulticastSocket socket;
	
	public AffichageClient(MulticastSocket socket) {
		super();
		this.socket = socket;
	}

	public void run() {
		String message = ChatMultiCast.recevoirMessage(socket);
		if(message!=null){
			System.out.println(message);
		}
	}

}
