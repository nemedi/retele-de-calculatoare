package rpc;

import java.text.MessageFormat;
import java.util.UUID;

public class Note {

	private String id;
	private String title;
	private String content;
	
	private Note() {
	}
	
	public Note(String title, String content) {
		this();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.content = content;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("id = \"{0}\", title = \"{1}\", content = \"{2}\"",
				id, title, content);
	}
	
}
