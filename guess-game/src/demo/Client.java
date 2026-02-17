package demo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static demo.Message.GUESS;
import static demo.Message.WIN;
import static demo.Message.login;
import static demo.Message.logout;
import static demo.Message.attempt;

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
						int index = command.indexOf(':');
						if (index > 0) {
							String type = command.substring(0, index).trim();
							String payload = command.substring(index + 1).trim();
							switch (type) {
							case "login":
								client.send(login(payload));
								break;
							case "logout":
								client.send(logout());
								break;
							case "attempt":
								client.send(attempt(payload));
								break;
							}
						}
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
					Message message = Transport.receive(socket);
					switch (message.getType()) {
					case GUESS:
						byte cd = message.getPayload(byte[].class)[0]; 
						byte cp = message.getPayload(byte[].class)[1];
						System.out.println("Correct digits: " + cd + "; Correct positions: " + cp);
						break;
					case WIN:
						if (name.get().equals(message.getPayload(String.class))) {
							System.out.println("You won the game. Congratulations!");
						} else {
							System.out.println("Player '" + message.getPayload(String.class) + "' won the game.");
						}
						break;
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	public void send(Message message) throws IOException {
		Transport.send(message, socket);
	}

	@Override
	public void close() throws Exception {
		if (socket != null) {
			socket.close();
		}
	}
}
