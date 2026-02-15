
package rpc;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Client {
	
	private interface CommandHandler<T> {
		void handle(T proxy, Scanner scanner);
	}

	public static void main(String[] args) {
		Map<String, CommandHandler<Contract>> commandHandlers =
				new HashMap<String, CommandHandler<Contract>>();
		commandHandlers.put("help", (proxy, scanner) -> {
			System.out.println("Available commands:");
			commandHandlers.keySet()
				.stream().sorted().forEach(System.out::println);
		});
		commandHandlers.put("list", (proxy, scanner) -> {
			Arrays.asList(proxy.getNotes()).forEach(System.out::println);
		});
		commandHandlers.put("get", (proxy, scanner) -> {
			System.out.print("id = ");
			String id = scanner.nextLine();
			Optional<Note> note = proxy.getNote(id);
			if (note.isPresent()) {
				System.out.println(note.get());
			} else {
				System.out.println("Not Found.");
			}			
		});
		commandHandlers.put("add", (proxy, scanner) -> {
			System.out.print("title = ");
			String title = scanner.nextLine();
			System.out.print("content = ");
			String content = scanner.nextLine();
			Optional<Note> note = proxy.addNote(title, content);
			if (note.isPresent()) {
				System.out.println("Added:" + note.get());
			} else {
				System.out.println("Not Added.");
			}			
		});
		commandHandlers.put("change", (proxy, scanner) -> {
			System.out.print("id = ");
			String id = scanner.nextLine();
			System.out.print("title = ");
			String title = scanner.nextLine();
			System.out.print("content = ");
			String content = scanner.nextLine();
			Optional<Note> note = proxy.changeNote(id, title, content);
			if (note.isPresent()) {
				System.out.println("Changed:" + note.get());
			} else {
				System.out.println("Not Added.");
			}			
		});
		commandHandlers.put("remove", (proxy, scanner) -> {
			System.out.print("id = ");
			String id = scanner.nextLine();
			if (proxy.removeNote(id)) {
				System.out.println("Deleted.");
			} else {
				System.out.println("Not Deleted.");
			}			
		});
		String host = Settings.TRACE > 0 ? "127.0.0.1" : Settings.HOST;
		int port = Settings.TRACE > 0 ? Settings.TRACE : Settings.PORT;
		Contract proxy = RpcProxy.createProxy(Contract.class,
				MessageFormat.format("tcp://{0}:{1}/{2}",
						host,
						Integer.toString(port),
						Settings.SERVICE));
		try (Scanner scanner = new Scanner(System.in)) {
			commandHandlers.get("help").handle(proxy, scanner);
			while (true) {
				String command = scanner.nextLine();
				if (command == null || "exit".equals(command.trim())) {
					break;
				} else if (commandHandlers.containsKey(command.trim())) {
					commandHandlers.get(command.trim()).handle(proxy, scanner);
				} else {
					System.out.println("Unknown command, type 'help' for available commands.");
				}
			}
			System.exit(0);
		}
	}

}
