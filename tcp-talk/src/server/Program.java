package server;

import java.util.Scanner;

import common.Settings;

public class Program {

	public static void main(String[] args) {
		try (Server server = new Server()) {
			server.start(Settings.PORT);
			System.out.println("Server is running, type 'exit' to stop it.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equalsIgnoreCase(command)) {
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			System.exit(0);
		}
	}

}
