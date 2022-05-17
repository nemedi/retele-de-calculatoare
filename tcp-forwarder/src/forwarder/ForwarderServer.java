package forwarder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForwarderServer implements AutoCloseable {
	
	private List<ServerSocket> serverSockets = new ArrayList<ServerSocket>();
	private ExecutorService executorService;
	
	public ForwarderServer(Map<Integer, String> rules) throws IOException {
		executorService = Executors.newFixedThreadPool(20 * Runtime.getRuntime().availableProcessors());
		for (final Entry<Integer, String> entry : rules.entrySet()) {
			final ServerSocket serverSocket = new ServerSocket(entry.getKey());
			serverSockets.add(serverSocket);
			executorService.submit(() -> {
				while (true) {
					try {
						final Socket incomingSocket = serverSocket.accept();
						int index = entry.getValue().indexOf(':');
						final Socket outgoingSocket = new Socket(entry.getValue().substring(0, index),
								Integer.parseInt(entry.getValue().substring(index + 1)));
						final InputStream incomingInputStream = incomingSocket.getInputStream();
						final OutputStream incomingOutputStream = incomingSocket.getOutputStream();
						final InputStream outgoingInputStream = outgoingSocket.getInputStream();
						final OutputStream outgoingOutputStream = outgoingSocket.getOutputStream();
						executorService.submit(() -> {
							while (true) {
								try {
									forwardStream(incomingInputStream, outgoingOutputStream);
								} catch (IOException e) {
									try {
										outgoingSocket.close();
									} catch (IOException e1) {
									}
									break;
								}
								
							}
						});
						executorService.submit(() -> {
							while (true) {
								try {
									forwardStream(outgoingInputStream, incomingOutputStream);
								} catch (IOException e) {
									try {
										outgoingSocket.close();
									} catch (IOException e1) {
									}
									break;
								}
								
							}
						});						
					} catch (IOException e) {
						break;
					}
				}
			});
		}
	}
	
	private void forwardStream(InputStream inputStream, OutputStream outputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int count = inputStream.read(buffer);
		if (count == -1) {
			throw new SocketException("Socket is closed.");
		}
		outputStream.write(buffer, 0, count);
		outputStream.flush();
	}

	@Override
	public void close() throws Exception {
		for (ServerSocket serverSocket : serverSockets) {
			serverSocket.close();
		}
		executorService.shutdown();
	}

}
