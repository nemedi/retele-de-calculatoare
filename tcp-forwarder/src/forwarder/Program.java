package forwarder;

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		try (ForwarderServer server = new ForwarderServer(Settings.rules);
				Scanner scanner = new Scanner(System.in)) {
			System.out.println("Forwarder is running, type 'exit' to close it.");
			while (true) {
				if (scanner.hasNextLine()) {
					String command = scanner.nextLine();
					if ("exit".equals(command)) {
						System.exit(0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
