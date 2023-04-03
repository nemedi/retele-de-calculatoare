package common;

import java.util.ResourceBundle;

public class Settings {

	public static final String HOST;
	public static final int PORT;
	public static final String SERVICE;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		HOST = bundle.getString("host");
		PORT = Integer.parseInt(bundle.getString("port"));
		SERVICE = bundle.getString("service");
	}
}
