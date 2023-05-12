package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements AutoCloseable {

	private ServerSocket commandServerSocket;
	private ServerSocket reportServerSocket;
	private ExecutorService executorService;
	private List<PrintWriter> writers = Collections.synchronizedList(new ArrayList<PrintWriter>());
	private AtomicInteger yesVotes = new AtomicInteger(0);
	private AtomicInteger noVotes = new AtomicInteger(0);
	private AtomicInteger maybeVotes = new AtomicInteger(0);
	
	public Server(int commandPort, int reportPort) throws IOException {
		commandServerSocket = new ServerSocket(commandPort);
		reportServerSocket = new ServerSocket(reportPort);
		executorService = Executors.newFixedThreadPool(
				50 * Runtime.getRuntime().availableProcessors());
		executorService.execute(handleCommandServerSocket());
		executorService.execute(handleReportServerSocket());
	}
	
	private Runnable handleCommandServerSocket() {
		return () -> {
			while (!commandServerSocket.isClosed()) {
				try {
					final Socket socket = commandServerSocket.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(socket.getOutputStream());
					writer.println("Enter your vote ('yes' / 'no' / 'maybe') or 'exit' to close.");
					writer.flush();
					executorService.submit(() -> {
						while (!socket.isClosed()) {
							try {
								String vote = reader.readLine();
								switch (vote) {
								case "yes":
									yesVotes.incrementAndGet();
									break;
								case "no":
									noVotes.incrementAndGet();
									break;
								case "maybe":
									maybeVotes.incrementAndGet();
									break;
								case "exit":
									socket.close();
									break;
								}
								printVoteReport();
							} catch (IOException e) {
							}
						}
					});
				} catch (IOException e) {
				}
			}
		};
	}
	
	private Runnable handleReportServerSocket() {
		return () -> {
			while (!reportServerSocket.isClosed()) {
				try {
					final Socket socket = reportServerSocket.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(socket.getOutputStream());
					writer.println("Please wait to see the voting report, or type 'exit' to close.");
					writer.flush();
					executorService.submit(() -> {
						writers.add(writer);
						while (!socket.isClosed()) {
							try {
								if ("exit".equals(reader.readLine())) {
									socket.close();
								}
							} catch (Exception e) {
							}
						}
						writers.remove(writer);
					});
				} catch (IOException e) {
				}
			}
		};
	}
	
	private void printVoteReport() {
		writers.forEach(writer -> {
			writer.println("===========================");
			writer.println("Votes:");
			writer.println("---------------------------");
			writer.println("Yes: " + yesVotes.get());
			writer.println("No: " + noVotes.get());
			writer.println("Maybe: " + maybeVotes.get());
			writer.println("===========================");
			writer.flush();
		});
	}
	
	@Override
	public void close() throws Exception {
		commandServerSocket.close();
		reportServerSocket.close();
	}
	
	
}
