package common;

import java.util.ResourceBundle;

public class Settings {

	public static final String REGISTRY_HOST;
	public static final int REGISTRY_PORT;
	public static final int SERVICE_PORT;
	public static final String SERVICE_NAME;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		REGISTRY_HOST = bundle.getString("registry.host");
		REGISTRY_PORT = Integer.parseInt(bundle.getString("registry.port"));
		SERVICE_PORT = Integer.parseInt(bundle.getString("service.port"));
		SERVICE_NAME = bundle.getString("service.name");
	}
}
