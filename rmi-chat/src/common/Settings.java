package common;

import java.util.ResourceBundle;

public class Settings {

	public static final String SERVER_REGISTRY_HOST;
	public static final int SERVER_REGISTRY_PORT;
	public static final int SERVER_SERVICE_PORT;
	public static final String SERVER_SERVICE_NAME;
	
	public static final String CLIENT_REGISTRY_HOST;
	public static final int CLIENT_REGISTRY_PORT;
	public static final int CLIENT_SERVICE_PORT;
	public static final String CLIENT_SERVICE_NAME;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		SERVER_REGISTRY_HOST = bundle.getString("server.registry.host");
		SERVER_REGISTRY_PORT = Integer.parseInt(bundle.getString("server.registry.port"));
		SERVER_SERVICE_PORT = Integer.parseInt(bundle.getString("server.service.port"));
		SERVER_SERVICE_NAME = bundle.getString("server.service.name");
		
		CLIENT_REGISTRY_HOST = bundle.getString("client.registry.host");
		CLIENT_REGISTRY_PORT = Integer.parseInt(bundle.getString("client.registry.port"));
		CLIENT_SERVICE_PORT = Integer.parseInt(bundle.getString("client.service.port"));
		CLIENT_SERVICE_NAME = bundle.getString("client.service.name");

	}
}
