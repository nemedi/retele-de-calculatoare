package server;

import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.server.ServerEndpointConfig;

public class Program {

	public static void main(String[] args) {
		ServiceLoader.load(ServerEndpointConfig.Configurator.class);
		ServerEndpointConfig.Builder.create(Service.class, "/monitor").build();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				Service.send("xxx");
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				String command = scanner.nextLine();
				if (command == null || "exit".equalsIgnoreCase(command)) {
					break;
				}
			}
		}
		timer.cancel();
		System.exit(0);
	}

}
