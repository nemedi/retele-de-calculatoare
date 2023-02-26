package client;

import static common.UdpMessage.subscribe;
import static common.UdpMessage.talk;
import static common.UdpMessage.unsubscribe;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import common.UdpTransport;

public class UdpClient extends Client {
	
	private DatagramSocket socket;
	private InetSocketAddress address;

	public UdpClient(String host, int port, IClientCallback callback) throws Exception {
		super(host, port, callback);
	}
	
	@Override
	protected void open(String host, int port) throws Exception {
		socket = new DatagramSocket();
		socket.setSendBufferSize(10 * 1024 * 1024);
		address = new InetSocketAddress(host, port);
		UdpTransport.send(subscribe(), socket, address);		
	}

	@Override
	protected boolean isClosed() {
		return socket == null || socket.isClosed();
	}

	@Override
	protected String receive() throws Exception {
		return UdpTransport.receive(socket).toString();
	}
	
	@Override
	public void send(String text) throws IOException {
		UdpTransport.send(talk(text), socket, address);
	}

	@Override
	public void onClose() throws Exception {
		if (socket != null && !socket.isClosed()) {
			UdpTransport.send(unsubscribe(), socket, address);
			socket.close();
			socket = null;
		}
	}
}
