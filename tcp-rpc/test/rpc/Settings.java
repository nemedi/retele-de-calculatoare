package rpc;

import java.util.ResourceBundle;

public class Settings {

	public static final String HOST;
	public static final int PORT;
	public static final int TRACE;
	public static final String SERVICE;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle(Settings.class.getName());
		HOST = bundle.getString("host");
		PORT = Integer.parseInt(bundle.getString("port"));
		TRACE = bundle.containsKey("trace")?
				Integer.parseInt(bundle.getString("trace")) : -1;
		SERVICE = bundle.getString("service");
	}
}
