package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server implements AutoCloseable {
	
	private volatile boolean running;
	
	@SuppressWarnings("deprecation")
	private Server(int port, String address) throws IOException {
		final MulticastSocket socket = new MulticastSocket(port);
		final InetAddress group = InetAddress.getByName(address);
		socket.joinGroup(group);
		new Thread(() -> {
			running = true;
			while (!socket.isClosed() && running) {
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
					int number = Integer.parseInt(new String(packet.getData(), packet.getOffset(), packet.getLength()));
					buffer = MessageFormat.format("{0}: {1}",
							Integer.toString(number),
							Arrays.stream(getPrimeNumbers(number))
								.boxed()
								.map(i -> Integer.toString(i))
								.collect(Collectors.joining(",")))
						.getBytes();
					packet = new DatagramPacket(buffer, buffer.length,
							packet.getAddress(), packet.getPort());
					socket.send(packet);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			if (!socket.isClosed()) {
				try {
					socket.leaveGroup(group);
				} catch (IOException e) {
				}
				socket.close();
			}
		}).start();
	}
	
	public int[] getPrimeNumbers(int n) {
		boolean[] isNotPrime = new boolean[n + 1];
		int found = 0;
		for (int i = 2; i <= n - 1; i++)
			if (isNotPrime[i] == false) {
				found++;
				for (int j = 2; j * i <= n; j++)
					if (j * i < isNotPrime.length) {
						isNotPrime[j * i] = true;
					}
			}
		int[] numbers = new int[found];
		int k = 0;
		for (int i = 2; i <= n; i++)
			if (isNotPrime[i] == false)
				numbers[k++] = i;
		return numbers;
	}

	@Override
	public void close() throws Exception {
		running = false;
	}

	public static void main(String[] args) {
		try (Server server = new Server(Integer.parseInt(args[0]), args[1]);
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
