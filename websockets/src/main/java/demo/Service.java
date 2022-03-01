package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/talk")
@Component
public class Service {
	
	private static List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());

	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);
	}
	
	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
	}
	
	@OnMessage
	public void onMessage(String text, Session session) {
		send(text);
	}

	public static void send(String text) {
		sessions.stream().forEach(session -> {
			try {
				session.getBasicRemote().sendText(text);
			} catch (IOException e) {
			}
		});
	}
}
