package server;

import java.util.Scanner;

import rpc.RpcServer;

public class Server {

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 6969;
		if (args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		try (RpcServer server = new RpcServer()) {
			server.start(host, port);
			server.publish("service", new Service());
			System.out.println(
					String.format("Server is running on host '%s' and port '%d', type 'exit' to stop it.",
							host, port));
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equals(command)) {
						break;
					}
				}
				System.exit(0);
			}
		}
	}

}
