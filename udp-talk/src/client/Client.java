package client;

import static common.Message.subscribe;
import static common.Message.talk;
import static common.Message.unsubscribe;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import common.Message;
import common.Transport;

public class Client implements AutoCloseable {
	
	public interface ClientCallback {
		void onTalk(Message message);
	}
	
	private DatagramSocket socket;
	private InetSocketAddress address;

	public Client(String host, int port, ClientCallback callback)
			throws IOException {
		socket = new DatagramSocket();
		socket.setSendBufferSize(10 * 1024 * 1024);
		address = new InetSocketAddress(host, port);
		Transport.send(subscribe(), socket, address);
		new Thread(() -> {
			while (socket != null && !socket.isClosed()) {
				try {
					callback.onTalk(Transport.receive(socket));
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	public void send(String text) throws IOException {
		Transport.send(talk(text), socket, address);
	}

	@Override
	public void close() throws Exception {
		if (socket != null && !socket.isClosed()) {
			Transport.send(unsubscribe(), socket, address);
			socket.close();
			socket = null;
		}
	}
}
