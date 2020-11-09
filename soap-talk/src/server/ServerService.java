package server;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import com.sun.xml.internal.ws.addressing.WsaPropertyBag;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;

import common.ClientContract;
import common.ServerContract;

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
		clients.forEach(client -> client.onReceive(message));
	}
	
	private ClientContract getClient() {
		WSEndpointReference reference = (WSEndpointReference) context.getMessageContext()
				.get(WsaPropertyBag.WSA_REPLYTO_FROM_REQUEST);
    	return new W3CEndpointReferenceBuilder()
    			.serviceName(new QName("http://axway.com", "Service"))
    			.address(reference.getAddress())
    			.wsdlDocumentLocation(reference.getAddress() + "?wsdl")
    			.build()
    			.getPort(ClientContract.class);
	}

}
