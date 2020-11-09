package server;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class Settings {

	public static final int PORT;
	private static final Map<String, Class<?>> PROCESSOR_TYPES = new HashMap<String, Class<?>>();
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		PORT = Integer.parseInt(bundle.getString("port"));
		for (Enumeration<String> e = bundle.getKeys();e.hasMoreElements();) {
			String key = e.nextElement();
			if (key.startsWith("processors[") && key.endsWith("]")) {
				try {
					PROCESSOR_TYPES.put(key.substring("processors[".length(), key.indexOf("]")),
							Class.forName(bundle.getString(key)));
				} catch (ClassNotFoundException e1) {
				}
			}
		}
	}
	
	public static Optional<Class<?>> getProcessorType(String path) {
		return PROCESSOR_TYPES.containsKey(path)
				? Optional.of(PROCESSOR_TYPES.get(path))
				: Optional.empty();
	}
}
