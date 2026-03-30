package client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import common.CallbackContract;
import common.Contract;
import rpc.RpcProxy;

public class Client implements CallbackContract {
	
	private static final String RUNTIME = "runtime";
	private Contract proxy;
	private File root;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		proxy = RpcProxy.createProxy(Contract.class,
				String.format("tcp://%s:%d/service", host, port),
				this);
	}
	
	public void process(String command) throws IOException {
		if (command.startsWith("login")) {
			String name = command.substring("login".length()).trim();
			this.root = Paths.get(RUNTIME, name).toFile();
			List<String> files = Arrays.stream(root.listFiles(file -> file.isFile()))
					.map(file -> file.getName())
					.collect(Collectors.toList());
			proxy.login(name, files);
		} else if ("logout".equals(command)) {
			proxy.logout();
		} else if ("list".equals(command)) {
			proxy.getFilesByUser();
		} else if (command.startsWith("transfer")) {
			String[] arguments = command.substring("transfer".length()).trim().split("\\s+");
			if (arguments.length == 2) {
				String user = arguments[0];
				String file = arguments[1];
				proxy.transferFile(user, file);
			} else {
				System.out.println(String.format("Wrong number of arguments: %d.", arguments.length));
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		if (args.length != 2) {
			System.out.println("Missing arguments, please run with host and port.");
			System.exit(1);
		} else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			Client client = new Client(host, port);
			System.out.println("Type login <name> to login or 'exit' to leave.");
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equals(command)) {
						break;
					} else {
						client.process(command);
					}
				}
				System.exit(0);
			}
		}
	}

	@Override
	public void onDeny(String message) {
		System.out.println(String.format("Your action was denied by the server: %s.", message));
	}

	@Override
	public void onLogin() {
		System.out.println("You are logged-in. Type 'list' to list user files or 'logout' to end session, or 'transfer <user> <file>' to download file.");
	}

	@Override
	public void onLogout() {
		this.root = null;
		System.out.println("Your session has ended, type 'exit' to leave or 'login <name>' to start a new session.");
	}

	@Override
	public void onGetFilesByUser(Map<String, List<String>> filesByUser) {
		for (Entry<String, List<String>> entry : filesByUser.entrySet()) {
			System.out.println("User: " + entry.getKey());
			System.out.println("Files:");
			for (String file : entry.getValue()) {
				System.out.println("   " + file);
			}
			System.out.println();
		}
	}

	@Override
	public void onReadFile(String user, String file) throws IOException {
		byte[] content = Files.readAllBytes(Paths.get(root.getAbsolutePath(), file));
		proxy.onReadFile(user, file, new String(content));
	}

	@Override
	public void onWriteFile(String file, String content) throws IOException {
		Files.write(Paths.get(root.getAbsolutePath(), file), content.getBytes());
		System.out.println(String.format("File was saved to '%s'.", file));
	}

}
