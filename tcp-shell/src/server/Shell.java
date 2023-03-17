package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Shell implements Runnable {

	private File directory;
	private int exitValue;
	private Map<String, String> variables;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	public Shell(Socket socket) throws IOException {
		this.socket = socket;
		this.reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(socket.getOutputStream());
		this.directory = new File("./");
		this.variables = new HashMap<String, String>();
	}

	public void run() {
		writer.print("Welcome to Remote Shell, type command ot 'exit' to close it.\r\n");
		writer.flush();
		while (!socket.isClosed()) {
			try {
				String command = reader.readLine();
				if ("exit".equals(command)) {
					socket.close();
				} else {
					this.execute(command).forEach(line -> writer.print(line + "\r\n"));
					writer.flush();
				}
			} catch (Exception e) {
				writer.println(e.getLocalizedMessage());
				writer.flush();
			}
		}
	}

	public List<String> execute(String input)
			throws IOException, InterruptedException {
		final List<String> lines = new ArrayList<String>();
		String[] commands = input.split("\\|");
		for (String command : commands) {
			command = command.trim().replace("$?", exitValue + "");
			for (Entry<String, String> entry : variables.entrySet()) {
				command = command.replace("$" + entry.getKey() , entry.getValue());
			}
			if (command.matches("^[a-zA-Z][_a-zA-Z0-9]*=.+$")) {
				int index = command.indexOf("=");
				variables.put(command.substring(0, index), command.substring(index + 1).trim());
				exitValue = 0;
			} else {
				String[] arguments = command.split("\\s+");
				if ("unset".equals(arguments[0])) {
					if (arguments.length == 2) {
						variables.remove(arguments[1]);
					}
				} else if ("cd".equals(arguments[0])) {
					if (arguments.length == 2) {
						String path = arguments[1]; 
						if (!path.startsWith("/")) {
							path = this.directory.getAbsolutePath() + "/" + path;
						}
						if (new File(path).isDirectory()) {
							this.directory = new File(arguments[1]);
						}
					}
					lines.clear();
				} else {
					ProcessBuilder builder = new ProcessBuilder(arguments);
					builder.directory(this.directory);
					Process process = builder.start();
					if (!lines.isEmpty()) {
						PrintWriter writer = new PrintWriter(
								process.getOutputStream());
						lines.forEach(line -> writer.println(line));
						writer.close();
					}
					lines.clear();
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					Thread thread = new Thread(() -> {
						try {
							while (true) {
								String line = reader.readLine();
								if (line == null) {
									break;
								} else {
									lines.add(line);
								}
							}
						} catch (IOException e) {
						}
					});
					thread.start();
					thread.join();
					process.waitFor();
					exitValue = process.exitValue();
				}
			}
		}
		return lines;
	}

}

