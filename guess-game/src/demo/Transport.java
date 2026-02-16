package demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Transport {

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T receive(Socket socket)
			throws IOException, ClassNotFoundException {
		byte[] buffer = new byte[socket.getInputStream().read()];
		int count = 0;
		while (count < buffer.length) {
			count += socket.getInputStream().read(buffer, count, buffer.length);
		}
		return (T) new ObjectInputStream(new ByteArrayInputStream(buffer)).readObject();
	}

	public static <T extends Serializable> void send(T object, Socket socket)
			throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
		byte[] buffer = outputStream.toByteArray();
		socket.getOutputStream().write(buffer.length);
		socket.getOutputStream().write(buffer, 0, buffer.length);
		socket.getOutputStream().flush();
	}

}