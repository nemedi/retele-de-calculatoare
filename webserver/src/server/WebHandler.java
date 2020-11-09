package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WebHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			WebRequest request = new WebRequest(exchange);
			Optional<Class<?>> processorType = Settings.getProcessorType(request.getPath());
			if (processorType.isPresent()) {
				WebResponse response = new WebResponse();
				WebProcessor.class.cast(processorType.get().getConstructor().newInstance())
					.process(request, response);
				if (response.getContentType() != null) {
					exchange.getResponseHeaders().add("content-type", response.getContentType());
				}
				response.getStream().close();
				response.getWriter().close();
				byte[] bytes = response.getStream().toByteArray();
				exchange.sendResponseHeaders(response.getStatus(), bytes.length);
				if (bytes.length > 0) {
					exchange.getResponseBody().write(bytes, 0, bytes.length);
				}
			} else {
				Path path = Paths.get("web", request.getPath());
				if (path.toFile().exists()) {
					if (path.toFile().isDirectory()) {
						exchange.sendResponseHeaders(403, 0);
					} else {
						byte[] bytes = Files.readAllBytes(path);
						exchange.sendResponseHeaders(200, bytes.length);
						exchange.getResponseBody().write(bytes, 0, bytes.length);
					}
				} else {
					exchange.sendResponseHeaders(404, 0);
				}
			}
		} catch (Exception e) {
			exchange.sendResponseHeaders(500, 0);
		} finally {
			exchange.close();
		}
	}

}

