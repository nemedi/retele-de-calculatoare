package mailer;

import java.util.ResourceBundle;

public class Settings {

	public static final String ID;
	public static final String HOST;
	public static final int PORT;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle(Settings.class.getName().toLowerCase());
		ID = bundle.getString("id");
		HOST = bundle.getString("host");
		PORT = bundle.containsKey("port") ?
				Integer.parseInt(bundle.getString("port")) : 25;
	}
}
