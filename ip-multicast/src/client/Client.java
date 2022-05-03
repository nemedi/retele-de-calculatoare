package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client implements AutoCloseable {
	
	private boolean running;

	private Client(int port, String[] addresses) throws SocketException {
		DatagramSocket socket = new DatagramSocket();
		final List<InetAddress> groups = Arrays.stream(addresses)
				.map(address -> {
					try {
						return InetAddress.getByName(address);
					} catch (UnknownHostException e) {
						return null;
					}
				})
				.filter(address -> address != null)
				.collect(Collectors.toList());
		final Random random = new Random();
		new Thread(() -> {
			running = true;
			while (!socket.isClosed() && running) {
				try {
					int number = random.nextInt(100);
					byte[] buffer = Integer.toString(number).getBytes();
					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length,
							groups.get(number % groups.size()),
							port);
					socket.send(packet);
					Thread.sleep(100000);
				} catch (Exception e) {
				}
			}
			if (!socket.isClosed()) {
				socket.close();
			}
		}).start();
		new Thread(() -> {
			while (!socket.isClosed()) {
				try {
					byte[] buffer = new byte[256];
					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length);
					socket.receive(packet);
					System.out.println(MessageFormat.format(
							"received {0} from {1}:{2}",
							new String(packet.getData()),
							packet.getAddress(),
							Integer.toString(packet.getPort())));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	@Override
	public void close() throws Exception {
		running = false;
	}

	public static void main(String[] args) {
		try (Client client = new Client(Integer.parseInt(args[0]),
				args[1].split(","));
			Scanner scanner = new Scanner(System.in)) {
			while (true) {
				if (scanner.hasNextLine() && "exit".equals(scanner.nextLine())) {
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			System.exit(0);
		}
	}

}
