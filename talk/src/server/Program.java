package server;

import java.util.Scanner;

import common.Settings;

public class Program {

	public static void main(String[] args) {
		try (IServer server = createServer()) {
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
	
	private static IServer createServer() {
		if ("TCP".equalsIgnoreCase(Settings.PROTOCOL)) {
			return new TcpServer();
		} else if ("UDP".equalsIgnoreCase(Settings.PROTOCOL)) {
			return new UdpServer();
		} else {
			return null;
		}
	}

}
