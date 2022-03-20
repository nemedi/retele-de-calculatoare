package client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.namespace.QName;

import common.Contract;
import common.Settings;
import jakarta.xml.ws.Service;

public class Client {

	public static void main(String[] args) {
        try {
			Contract proxy = Service.create(new URL(String.format("http://%s:%d/greetings?wsdl",
					Settings.HOST, Settings.PORT)),
					new QName("http://axway.com", "Service"))
					.getPort(new QName("http://axway.com", "ServicePort"),
							Contract.class);
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					System.out.print("Enter your name or 'exit' to quit: ");
					if (scanner.hasNextLine()) {
						String argument = scanner.nextLine();
						if ("exit".equalsIgnoreCase(argument)) {
							break;
						} else {
							System.out.println(proxy.sayHello(argument));
						}
					}
				}
				System.exit(0);
			}
		} catch (MalformedURLException e) {
		}
	}

}
