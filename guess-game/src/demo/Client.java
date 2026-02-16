package demo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Client implements AutoCloseable {
	
	private Socket socket;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Missing argument host or port.");
			System.exit(1);
		}
		try (Client client = new Client()) {
			client.start(args[0], Integer.parseInt(args[1]));
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equalsIgnoreCase(command)) {
						break;
					} else {
						client.send(command);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			System.exit(0);
		}
	}
	
	public void start(String host, int port)
			throws UnknownHostException, IOException {
		final AtomicReference<String> name = new AtomicReference<String>("");
		socket = new Socket(host, port);
		new Thread(() -> {
			while (socket != null && !socket.isClosed()) {
				try {
					String data = Transport.receive(socket);
					String[] arguments = data.split(":");
					switch (arguments[0]) {
					case "deny":
						System.out.println("You must login first. Type 'login:<name>'.");
						break;
					case "guess":
						int position = arguments[1].indexOf('-');
						if (position > 1) {
							String cd = arguments[1].substring(0, position);
							String cp = arguments[1].substring(position) + 1;
							System.out.println("Correct digits: " + cd + "; Correct positions: " + cp);
						}
						break;
					case "win":
						if (name.get().equals(arguments[1])) {
							System.out.println("You won the game. Congratulations!");
						} else {
							System.out.println("Player '" + arguments[1] + "' won the game.");
						}
						break;
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	public void send(String message) throws IOException {
		Transport.send(message, socket);
	}

	@Override
	public void close() throws Exception {
		if (socket != null) {
			socket.close();
		}
	}
}
