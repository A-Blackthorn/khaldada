package com.blackthorn.chatserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server extends Thread{

	private int port;
	private ServerSocket socket;
	private Vector<ResponseThread> clients;

	public Server(int port) {
		this.port = port;
		clients = new Vector<>();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public ServerSocket getSocket() {
		return socket;
	}

	public void setSocket(ServerSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try{
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Serveur démarré");
			do {
				System.out.println("Serveur à l'écoute");
				Socket socket = ss.accept();
				System.out.println("Demande de connexion");
				ResponseThread p = new ResponseThread(this, socket);
				clients.addElement(p);
				p.start();
			} while (true);
		}catch(Exception e){
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	public void reply(String request){
		for (ResponseThread p : clients) {
			p.reply(request);
		}
	}
	
}
