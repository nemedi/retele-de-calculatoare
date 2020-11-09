package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class WebRequest {
	
	private String method;
	private String path;
	private Map<String, String> query;
	private String contentType;
	private byte[] body;

	public WebRequest(HttpExchange exchange) throws IOException {
		method = exchange.getRequestMethod();
		path = exchange.getRequestURI().getPath().substring(1);
		query = new HashMap<String, String>();
		if (exchange.getRequestURI().getQuery() != null) {
			for (String part : exchange.getRequestURI().getQuery().split("&")) {
				String[] segments = part.split("=");
				if (segments.length == 2) {
					query.put(segments[0], segments[1]);
				}
			}
		}
		if (exchange.getRequestHeaders().containsKey("content-type")) {
			contentType = exchange.getRequestHeaders().getFirst("content-type");
		}
		if (exchange.getRequestHeaders().containsKey("content-length")) {
			body = new byte[Integer.parseInt(exchange.getRequestHeaders()
					.getFirst("content-length"))];
			exchange.getRequestBody().read(body, 0, body.length);
		} else {
			body = new byte[0];
		}
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public Map<String, String> getQuery() {
		return query;
	}
	
	public byte[] getBody() {
		return body;
	}

}
