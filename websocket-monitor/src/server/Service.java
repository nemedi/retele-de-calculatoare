package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class Service extends Endpoint {

	private static final List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());
	
	@Override
	public void onOpen(Session session, EndpointConfig configuration) {
		sessions.add(session);
	}
	
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		sessions.remove(session);
		super.onClose(session, closeReason);
	}
	
	public static void send(String text) {
		sessions.forEach(session -> {
			try {
				session.getBasicRemote().sendText(text);
			} catch (IOException e) {
			}
		});
	}
}
