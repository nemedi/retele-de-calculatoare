package server;

import java.rmi.RemoteException;
import java.util.Scanner;

import common.RegistryUtility;
import common.Settings;

public class ChatConsole {

	public static void main(String[] args) {
		try {
			new ChatService(RegistryUtility.getRegistry(Settings.SERVER_REGISTRY_PORT),
					Settings.SERVER_SERVICE_NAME, Settings.SERVER_SERVICE_PORT);
			System.out.println("Server is runnig, type 'exit' to close it.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equalsIgnoreCase(command)) {
						break;
					}
				}
			}
		} catch (NumberFormatException | RemoteException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
}
