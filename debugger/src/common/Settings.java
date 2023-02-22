package common;

import java.util.ResourceBundle;

public class Settings {

	public static final String SERVER_HOST;
	public static final String CLIENT_HOST;
	public static final int REGISTRY_PORT;
	public static final String SERVER_SERVICE;
	public static final String CLIENT_SERVICE;
	public static final String SERVER_DIRECTORY;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		SERVER_HOST = bundle.getString("serverHost");
		CLIENT_HOST = bundle.getString("clientHost");
		REGISTRY_PORT = Integer.parseInt(bundle.getString("registryPort"));
		SERVER_SERVICE = bundle.getString("serverService");
		CLIENT_SERVICE = bundle.getString("clientService");
		SERVER_DIRECTORY = bundle.getString("serverDirectory");
	}
}
