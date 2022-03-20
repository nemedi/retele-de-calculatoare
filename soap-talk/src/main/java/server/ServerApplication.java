package server;

import java.net.InetAddress;
import java.util.Scanner;

import common.Settings;
import jakarta.xml.ws.Endpoint;

public class ServerApplication {

	public static void main(String[] args) {
		try {
			String endpoint = String.format("http://%s:%d/talk",
					InetAddress.getLocalHost().getHostAddress(), Settings.PORT);
			Endpoint.publish(endpoint, new ServerService());
			System.out.println(String.format(
					"Server is listening on '%s', type 'exit' to stop it.",
					endpoint));
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					if (scanner.hasNextLine()
							&& "exit".equalsIgnoreCase(
									scanner.nextLine())) {
						break;
					}
				}
				System.exit(0);
			}
		} catch (Exception e) {
		}
	}

}

