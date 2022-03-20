package common;

import java.util.ResourceBundle;

public final class Settings {

	public static final String HOST;
	public static final int PORT;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		HOST = bundle.getString("host");
		PORT = Integer.parseInt(bundle.getString("port"));
	}
}
