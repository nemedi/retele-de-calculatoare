package server;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.ws.addressing.WsaPropertyBag;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import common.ClientContract;
import common.ServerContract;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.soap.Addressing;
import jakarta.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

@Addressing(required = true)
@WebService(
		serviceName = "Service",
		portName = "ServicePort",
		targetNamespace = "http://axway.com",
		endpointInterface = "common.ServerContract")
public class ServerService implements ServerContract {
	
	private static final List<ClientContract> clients =
			synchronizedList(new ArrayList<ClientContract>());
	
	@Resource
	private WebServiceContext context;

	@Override
	public void subscribe() {
		clients.add(getClient());
	}

	@Override
	public void unsubscribe() {
		clients.remove(getClient());
	}

	@Override
	public void send(String message) {
		clients.forEach(client -> {
			try {
				client.onReceive(message);
			} catch (Exception e) {
			}
		});
	}
	
	private ClientContract getClient() {
		try {
			WSEndpointReference reference = (WSEndpointReference) context.getMessageContext()
					.get(WsaPropertyBag.WSA_REPLYTO_FROM_REQUEST);
			return new W3CEndpointReferenceBuilder()
					.serviceName(new QName("http://axway.com", "Service"))
					.address(reference.getAddress())
					.wsdlDocumentLocation(reference.getAddress() + "?wsdl")
					.build()
					.getPort(ClientContract.class);
		} catch (Exception e) {
			return null;
		}
	}

}
