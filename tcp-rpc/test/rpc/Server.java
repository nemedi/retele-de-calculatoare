package rpc;

import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		try (RpcServer server = new RpcServer()) {
			server.publish(Settings.SERVICE, new Service());
			server.start(Settings.HOST, Settings.PORT);
			System.out.println("Server is running, type 'exit' to stop it.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equals(command.trim())) {
						break;
					}
				}
			}
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
