package server;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		int commandPort = bundle.containsKey("command.port")
				? Integer.parseInt(bundle.getString("command.port")) : 6969;
		int reportPort = bundle.containsKey("report.port")
				? Integer.parseInt(bundle.getString("report.port")) : 7979;
		try (Server server = new Server(commandPort, reportPort);
				Scanner scanner = new Scanner(System.in)) {
			System.out.println("Command server is running on port: " + commandPort);
			System.out.println("Report server is running on port: " + reportPort);
			System.out.println("Type 'exit' to close it.");
			while (true) {
				String command = scanner.nextLine();
				if (command == null || "exit".equals(command)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
