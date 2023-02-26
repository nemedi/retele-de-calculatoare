package common;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class UdpMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int SUBSCRIBE = 1;
	public static final int UNSUBSCRIBE = 2;
	public static final int TALK = 3;
	
	private int type;
	private String text;
	
	private volatile InetSocketAddress address;

	private UdpMessage(int type, String text) {
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
	
	public static UdpMessage subscribe() {
		return new UdpMessage(SUBSCRIBE, null);
	}
	
	public static UdpMessage unsubscribe() {
		return new UdpMessage(UNSUBSCRIBE, null);
	}
	
	public static UdpMessage talk(String text) {
		return new UdpMessage(TALK, text);
	}
	
	@Override
	public String toString() {
		return getText();
	}

}
