package mailer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MailData {

	public static final String EMAIL = "email";
	private String[] fields;
	private List<String[]> values;

	public MailData(String csv) throws FileNotFoundException, IOException {
		this.fields = null;
		this.values = null;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(csv)))) {
			while (true) {
				try {
					String line = reader.readLine();
					if (line == null) {
						break;
					}
					if (fields == null) {
						fields = line.split(",");
						values = new ArrayList<String[]>();
					} else {
						values.add(line.split(","));
					}
				} catch (Exception e) {
					break;
				}
			}
		}
	}
	
	public String[] getFields() {
		return fields;
	}
	
	public int getLength() {
		return values != null ? values.size() : 0;
	}
	
	public String getValue(int i, int j) {
		return values != null
				&& values.size() > i
				&& values.get(i) != null
				&& values.get(i).length > j ?
				values.get(i)[j] : null;
	}
	
	public boolean isEmpty() {
		return fields == null || values == null;
	}
}
