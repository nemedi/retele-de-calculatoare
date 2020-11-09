package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.xml.ws.Endpoint;

public class Server {

	public static void main(String[] args) {
		try {
			String endpoint = String.format("http://%s:%d/transformer",
					InetAddress.getLocalHost().getHostAddress(),
					Integer.parseInt(ResourceBundle.getBundle("settings")
							.getString("port")));
			Endpoint.publish(endpoint,
					new Service());
			System.out.println(String.format("Server is listening on '%s',"
					+ " type 'exit' to close it.", endpoint));
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					if (scanner.hasNextLine()
							&& "exit".equalsIgnoreCase(scanner.nextLine())) {
						System.exit(0);
					}
				}
			}
		} catch (NumberFormatException | UnknownHostException e) {
			System.out.println(e.getMessage());
		}
	}

}
