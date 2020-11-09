package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.Transport;

public class Server implements AutoCloseable {
	
	private ServerSocket serverSocket;
	private ExecutorService executorService;

	@Override
	public void close() throws Exception {
		stop();
	}

	public void start(int port) throws IOException {
		stop();
		serverSocket = new ServerSocket(port);
		executorService = Executors.newFixedThreadPool(50 * Runtime.getRuntime().availableProcessors());
		final List<Socket> clients = Collections.synchronizedList(new ArrayList<Socket>());
		executorService.execute(() -> {
			while (serverSocket != null && !serverSocket.isClosed()) {
				try {
					final Socket socket = serverSocket.accept();
					executorService.submit(() -> {
						try {
							clients.add(socket);
							while (socket != null && !socket.isClosed()) {
								try {
									String message = Transport.receive(socket);
									clients.forEach(client -> {
										try {
											Transport.send(message, client);
										} catch (Exception e) {
										}
									});
								} catch (Exception e) {
								}
							}
						} catch (Exception e) {
						} finally {
							clients.remove(socket);
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

}
