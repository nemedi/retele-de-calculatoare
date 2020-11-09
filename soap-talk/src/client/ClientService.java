package client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Service;
import javax.xml.ws.soap.Addressing;
import javax.xml.ws.soap.AddressingFeature;

import com.sun.xml.internal.ws.api.addressing.OneWayFeature;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;

import common.ClientContract;
import common.ServerContract;
import common.Settings;

@Addressing(required = true)
@WebService(
		serviceName = "Service",
		portName = "ServicePort",
		targetNamespace = "http://axway.com",
		endpointInterface = "common.ClientContract")
public class ClientService implements ClientContract, AutoCloseable {
	
	private ClientContract callback;
	private ServerContract proxy;

	public ClientService(ClientContract callback) throws MalformedURLException {
		this.callback = callback;
		int port = Settings.PORT;
		EndpointReference reference = null;
		while (reference == null && port < Short.MAX_VALUE) {
			try {
				reference = Endpoint.publish(String.format("http://%s:%d/talk",
						InetAddress.getLocalHost().getHostAddress(), port), this)
	        		.getEndpointReference();
			} catch (Exception e) {
				port++;
			}
		}
		if (reference != null) {
	        this.proxy = Service.create(new URL(String.format("http://%s:%d/talk?wsdl",
	        		Settings.HOST, Settings.PORT)),
	        		new QName("http://axway.com", "Service"))
	        		.getPort(new QName("http://axway.com", "ServicePort"),
	        				ServerContract.class,
	        				new AddressingFeature(),
	        				new OneWayFeature(true, new WSEndpointReference(reference)));
	        this.proxy.subscribe();
		}
	}

	@Override
	public void onReceive(String message) {
		callback.onReceive(message);
	}
	
	public void send(String message) {
		if (proxy != null) {
			proxy.send(message);
		}
	}
	
	@Override
	public void close() throws Exception {
		if (proxy != null) {
			proxy.unsubscribe();
		}
	}

}
