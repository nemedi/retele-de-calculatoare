package server;

import java.text.MessageFormat;

import common.Contract;
import jakarta.jws.WebService;

@WebService(
		serviceName = "Service",
		portName = "ServicePort",
		targetNamespace = "http://axway.com",
		endpointInterface = "common.Contract")
public class Service implements Contract {

	public String sayHello(String name) {
		return MessageFormat.format("Hello, {0}!", name);
	}

}
