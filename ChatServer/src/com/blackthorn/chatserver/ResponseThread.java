package com.blackthorn.chatserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ResponseThread extends Thread{

	private Socket socket;
	private Server server;
	private InputStream in;
	private OutputStream out;

	public ResponseThread(Server server, Socket socket) {
		super();
		this.socket = socket;
		this.server = server;
		try{
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		}catch(Exception e){
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		try{
			String request;
			do {
				if(in.available() > 0){
					byte [] t = new byte[1024];
					in.read(t);
					request = new String(t, StandardCharsets.UTF_8);
					request = request.trim();
					System.out.println("Message received : " + request);
					server.reply(request);
				}
			} while (!socket.isClosed());
			System.out.println("Thread terminated!");
		}catch(Exception e){
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	public void reply(String reply){
		try {
			byte[] t = reply.trim().getBytes();
			//System.out.println("Sending message : " + reply);
			//Add UI
			out.write(t);
			out.flush();
			System.out.println("Message sent.");
		} catch (Exception e) {
			System.out.println("Erreur in RT.reply : " + e.getMessage());
		}
	}
	
}
