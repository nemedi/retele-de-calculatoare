package client;

import java.net.Socket;

import common.TcpTransport;

public class TcpClient extends Client {
	
	private Socket socket;

	public TcpClient(String host, int port, IClientCallback callback) throws Exception {
		super(host, port, callback);
		new Thread(() -> {
			while (socket != null && !socket.isClosed()) {
				try {
					callback.onTalk(TcpTransport.receive(socket));
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	@Override
	protected void open(String host, int port) throws Exception {
		socket = new Socket(host, port);
	}

	@Override
	protected boolean isClosed() {
		return socket == null || socket.isClosed();
	}

	@Override
	protected String receive() throws Exception {
		return TcpTransport.receive(socket);
	}
	
	@Override
	public void send(String message) throws Exception {
		TcpTransport.send(message, socket);
	}

	@Override
	protected void onClose() throws Exception {
		if (!isClosed()) {
			socket.close();
		}
	}
}
