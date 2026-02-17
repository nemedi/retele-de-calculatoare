package demo;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final byte LOGIN = 1;
	public static final byte LOGOUT = 2;
	public static final byte ATTEMPT = 3;
	public static final byte GUESS = 4;
	public static final byte WIN = 5;
	
	private byte type;
	private Object payload;
	
	private Message(byte type, Object payload) {
		this.type = type;
		this.payload = payload;
	}
	
	public byte getType() {
		return type;
	}
	
	public <T> T getPayload(Class<T> type) {
		return type.cast(payload);
	}
	
	public static Message login(String name) {
		return new Message(LOGIN, name);
	}
	
	public static Message logout() {
		return new Message(LOGOUT, null);
	}
	
	public static Message attempt(String guess) {
		return new Message(ATTEMPT, guess);
	}

	public static Message guess(byte correctDigits, byte correctPositions) {
		return new Message(GUESS, new byte[] {correctDigits, correctPositions});
	}

	public static Message win(String name) {
		return new Message(WIN, name);
	}
	
}
