package common;

import java.util.ResourceBundle;

public class Settings {

	public static final String PROTOCOL;
	public static final String HOST;
	public static final int PORT;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		PROTOCOL = bundle.getString("protocol");
		HOST = bundle.getString("host");
		PORT = Integer.parseInt(bundle.getString("port"));
	}
}
