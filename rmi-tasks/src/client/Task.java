package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import common.ITask;

public class Task implements ITask<List<String>, String> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<String> execute(String command) throws Exception {
		final List<String> lines = new ArrayList<String>();
		for (String segment : command.split("\\|")) {
			ProcessBuilder builder = new ProcessBuilder(segment.trim().split("\\s+"));
			Process process = builder.start();
			if (!lines.isEmpty()) {
				PrintWriter writer = new PrintWriter(process.getOutputStream());
				lines.forEach(line -> writer.println(line));
				writer.close();
			}
			lines.clear();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
		}
		return lines;
	}

}
