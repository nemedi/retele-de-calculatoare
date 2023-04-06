package server;

import java.io.File;
import java.util.Scanner;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

public class Program {

	public static void main(String[] args) {
		try {
			int port = 21;
			String usersFile = "users.properties";
			if (args.length == 2) {
				port = Integer.parseInt(args[0]);
				usersFile = args[1];
			}
			FtpServerFactory serverFactory = new FtpServerFactory();
			ListenerFactory factory = new ListenerFactory();
			factory.setPort(port);
			serverFactory.addListener("default", factory.createListener()); 
			PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
			userManagerFactory.setFile(new File(usersFile));
			userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
			UserManager userManager = userManagerFactory.createUserManager();
			serverFactory.setUserManager(userManager);
			FtpServer server = serverFactory.createServer(); 
			server.start();
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					String command = scanner.nextLine();
					if (command == null || "exit".equals(command)) {
						break;
					}
				}
			}
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException("FTP server start-up failed", e);
		}
	}

}
