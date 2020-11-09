package server;

import java.net.InetAddress;
import java.util.Scanner;

import javax.xml.ws.Endpoint;

import common.Settings;

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
						System.exit(0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

