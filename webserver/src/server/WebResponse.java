package server;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class WebResponse {

	private int status = 200;
	private String contentType = "text/plain";
	private ByteArrayOutputStream stream = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(stream);
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public ByteArrayOutputStream getStream() {
		return stream;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
}
