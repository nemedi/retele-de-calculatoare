package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements AutoCloseable {
	
	private ServerSocket socket;
	
	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
		ExecutorService executorService = Executors.newFixedThreadPool(
				50 * Runtime.getRuntime().availableProcessors());
		executorService.execute(() -> {
			while (!socket.isClosed()) {
				try {
					executorService.submit(new Shell(socket.accept()));
				} catch (Exception e) {
				}
			}
		});
	}

	@Override
	public void close() throws Exception {
		socket.close();
 	}

}
