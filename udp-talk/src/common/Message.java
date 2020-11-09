package common;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int SUBSCRIBE = 1;
	public static final int UNSUBSCRIBE = 2;
	public static final int TALK = 3;
	
	private int type;
	private String text;
	
	private volatile InetSocketAddress address;

	private Message(int type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public int getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}
	
	public InetSocketAddress getAddress() {
		return address;
	}
	
	public static Message subscribe() {
		return new Message(SUBSCRIBE, null);
	}
	
	public static Message unsubscribe() {
		return new Message(UNSUBSCRIBE, null);
	}
	
	public static Message talk(String text) {
		return new Message(TALK, text);
	}

}
