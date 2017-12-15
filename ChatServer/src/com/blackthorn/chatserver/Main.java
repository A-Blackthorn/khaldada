package com.blackthorn.chatserver;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Server server = new Server(Values.PORT);
		server.start();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		do{
			String line = scanner.nextLine();
			server.reply(line);
		}while(true);
	}

}
