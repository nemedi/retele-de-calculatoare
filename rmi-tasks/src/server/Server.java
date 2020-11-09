package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("settings");
			int port = Integer.parseInt(bundle.getString("port"));
			String service = bundle.getString("service");
			new TaskManager<List<String>, String>(getRegistry(port), service);
			System.out.println("Server is running, type 'exit' to close it.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					if (scanner.hasNextLine()) {
						String command = scanner.nextLine();
						if (command == null || "exit".equals(command)) {
							break;
						}
					}
				}
			}
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	private static Registry getRegistry(int port) throws RemoteException {
		try {
			return LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			return LocateRegistry.getRegistry(port);
		}
	}
}
