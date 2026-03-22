package client;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import common.ITaskManager;
import common.RegistryUtility;
import common.Settings;

public class Client {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			Registry registry = RegistryUtility.getRegistry(Settings.REGISTRY_HOST, Settings.REGISTRY_PORT);
			ITaskManager<List<String>, String> taskManager =
					(ITaskManager<List<String>, String>) registry.lookup(Settings.SERVICE_NAME);
			Task task = new Task();
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equalsIgnoreCase(command)) {
						break;
					} else {
						taskManager.execute(task, command)
							.forEach(System.out::println);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
