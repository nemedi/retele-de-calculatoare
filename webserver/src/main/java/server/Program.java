package server;

import java.net.InetAddress;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		try (WebServer server = new WebServer()) {
			InetAddress ip = InetAddress.getLocalHost();
			int port = Settings.PORT;
			server.open(ip, port);
			System.out.println(String.format("Server is running on '%s:%d', type 'exit' to stop it.",
					ip.getHostAddress().toString(), port));
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
