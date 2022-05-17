package forwarder;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Settings {

	public static final Map<Integer, String> rules = new HashMap<Integer, String>();
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		for (Enumeration<String> e = bundle.getKeys(); e.hasMoreElements(); ) {
			String key = e.nextElement();
			String value = bundle.getString(key);
			int index = value.indexOf(':');
			if (key.startsWith("tcp.")
					&& index > 0
					&& index < value.length() - 1) {
				int port = Integer.parseInt(key.substring("tcp.".length()));
				rules.put(port, value);
			}
		}
	}

}
