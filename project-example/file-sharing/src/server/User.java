package server;

import java.util.List;

public class User {

	private String name;
	private List<String> files;
	
	public User(String name, List<String> files) {
		this.name = name;
		this.files = files;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getFiles() {
		return files;
	}
}
