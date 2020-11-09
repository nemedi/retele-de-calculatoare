package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import common.ITaskManager;

public class Client {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NotBoundException {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("settings");
			String host = bundle.getString("host");
			int port = Integer.parseInt(bundle.getString("port"));
			String service = bundle.getString("service");
			ITaskManager<List<String>, String> taskManager =
					(ITaskManager<List<String>, String>) Naming.lookup(String.format("rmi://%s:%d/%s",
							host, port, service));
			Task task = new Task();
			System.out.println("Enter command to be executed on server or 'exit' to quit.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					if (scanner.hasNextLine()) {
						String command = scanner.nextLine();
						if (command == null || "exit".equals(command)) {
							break;
						} else {
							taskManager.execute(task, command)
								.forEach(System.out::println);
						}
					}
				}
			}
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
