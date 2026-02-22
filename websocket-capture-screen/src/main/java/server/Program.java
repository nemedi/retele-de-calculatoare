package server;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.imageio.ImageIO;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Program {

	public static void main(String[] args){
		try {
			final Robot robot = new Robot();
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			int port = Integer.parseInt(ResourceBundle.getBundle("application").getString("port"));
			Javalin.create(config -> {
	            config.staticFiles.add("/", Location.CLASSPATH);
	        })
			.get("/", (context) -> context.redirect("/index.html"))
			.ws("/screen", ws -> {
			    ws.onMessage((handler) -> {
			    	Dimension dimension = toolkit.getScreenSize();
					try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
						ImageIO.write(robot.createScreenCapture(
									new Rectangle(0, 0, dimension.width, dimension.height)),
								"jpg",
								stream);
						handler.session.getRemote().sendBytes(
								ByteBuffer.wrap(stream.toByteArray()));
					}
			    	
			    });
			}).start(port);
			System.out.println(String.format("Server is running on port %d, type 'exit' to stop it.", port));
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equalsIgnoreCase(command)) {
						break;
					}
				}
			}
		} catch (NumberFormatException | AWTException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			System.exit(0);
		}
	}

}
