package server;

import java.net.InetAddress;
import java.util.Scanner;

import common.Settings;
import jakarta.xml.ws.Endpoint;

public class Server {

	public static void main(String[] args) {
		try {
			String endpoint = String.format("http://%s:%d/greetings",
					InetAddress.getLocalHost().getHostAddress(), Settings.PORT);
			Endpoint.publish(endpoint,
					new Service());
			System.out.println(String.format("Server is listening on '%s',"
					+ " type 'exit' to close it.", endpoint));
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					if (scanner.hasNextLine()
							&& "exit".equalsIgnoreCase(scanner.nextLine())) {
						break;
					}
				}
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
