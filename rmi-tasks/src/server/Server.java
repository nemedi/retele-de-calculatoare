package server;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import common.RegistryUtility;
import common.Settings;

public class Server {

	public static void main(String[] args) {
		try {
			Registry registry = RegistryUtility.getRegistry(Settings.REGISTRY_PORT);
			new TaskManager<List<String>, String>(registry, Settings.SERVICE_NAME, Settings.SERVICE_PORT);
			System.out.println("Server is running, type 'exit' to stop it.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String commamd = scanner.nextLine();
					if (commamd == null || "exit".equalsIgnoreCase(commamd)) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
