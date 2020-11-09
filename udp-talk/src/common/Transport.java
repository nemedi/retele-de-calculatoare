package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Transport {

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T receive(DatagramSocket socket)
			throws IOException, ClassNotFoundException {
		byte[] buffer = new byte[10 * 1024 * 1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.setSoTimeout(60000);
		socket.receive(packet);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				new ByteArrayInputStream(packet.getData()));
		T object = (T) objectInputStream.readObject();
		if (object instanceof Message) {
			((Message) object).setAddress(
					new InetSocketAddress(packet.getAddress(), packet.getPort()));
		}
		return object;
	}

	public static <T extends Serializable> void send(T object,
			DatagramSocket socket,
			InetSocketAddress address) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
		byte[] buffer = byteArrayOutputStream.toByteArray();
		DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length, address);
		socket.send(packet);
	}

}
