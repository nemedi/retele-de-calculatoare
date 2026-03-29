package server;

public class User {

	private String name;
	private String[] files;
	
	public User(String name, String[] files) {
		this.name = name;
		this.files = files;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getFiles() {
		return files;
	}
}
