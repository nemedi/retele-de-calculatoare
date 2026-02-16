package demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Server implements AutoCloseable {

	private static final int LENGTH = 4;

	private ServerSocket serverSocket;
	private ExecutorService executorService;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Missing argument port.");
			System.exit(1);
		}
		try (Server server = new Server()) {
			server.start(Integer.parseInt(args[0]));
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

	private static String generateSecret() {
		List<Integer> digits = new ArrayList<>();
		Random random = new Random();
		while (digits.size() < LENGTH) {
			int d = random.nextInt(10);
			if (!digits.contains(d)) {
				digits.add(d);
			}
		}
		StringBuilder secret = new StringBuilder();
		for (int digit : digits) {
			secret.append(digit);
		}
		System.out.println("A new secret has been generated: " + secret);
		return secret.toString();
	}

	public void start(int port) throws IOException {
		stop();
		serverSocket = new ServerSocket(port);
		executorService = Executors.newFixedThreadPool(50 * Runtime.getRuntime().availableProcessors());
		final ConcurrentMap<Socket, String> players = new ConcurrentHashMap<>();
		executorService.execute(() -> {
			final AtomicReference<String> secret = new AtomicReference<String>(generateSecret());
			while (serverSocket != null && !serverSocket.isClosed()) {
				try {
					final Socket socket = serverSocket.accept();
					Transport.send("Type 'login:<name>', then 'attempt:<number> or 'logout:'", socket);
					executorService.submit(() -> {
						try {
							while (socket != null && !socket.isClosed()) {
								try {
									String data = Transport.receive(socket);
									String[] arguments = data.split(":");
									switch (arguments[0]) {
									case "login":
										players.put(socket, arguments[1].trim());
										break;
									case "attempt":
										if (players.containsKey(socket) && !players.get(socket).isEmpty()) {
											String guess = arguments[1].trim();
											int cd = 0, cp = 0;
											for (int i = 0; i < LENGTH; i++) {
												if (secret.get().indexOf(guess.charAt(i)) > -1) {
													cd++;
												}
												if (secret.get().charAt(i) == guess.charAt(i)) {
													cp++;
												}
											}
											if (cp == 4) {
												players.keySet().forEach(s -> {
													try {
														Transport.send("win:" + players.get(socket), s);
													} catch (IOException e) {
													}
												});
												secret.set(generateSecret());
											} else {
												Transport.send("guess:" + cd + "-" + cp, socket);
											}
										} else {
											Transport.send("deny:", socket);
										}
										break;
									case "logout":
										players.remove(socket);
										break;
									default:
										Transport.send("unknown:" + arguments[0], socket);
										break;
									}
								} catch (Exception e) {
								}
							}
						} catch (Exception e) {
						} finally {
							players.remove(socket);
						}
					});
				} catch (Exception e) {
				}
			}
		});
	}

	public void stop() throws IOException {
		if (executorService != null) {
			executorService.shutdown();
			executorService = null;
		}
		if (serverSocket != null) {
			serverSocket.close();
			serverSocket = null;
		}
	}

	@Override
	public void close() throws Exception {
		stop();
	}

}
