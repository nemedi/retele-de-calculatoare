package processors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import server.WebProcessor;
import server.WebRequest;
import server.WebResponse;

public class FilesProcessor implements WebProcessor {

	@Override
	public void process(WebRequest request, WebResponse response) throws Exception {
		switch (request.getMethod().toUpperCase()) {
		case "GET":
			if (request.getQuery().containsKey("name")) {
				Path path = Paths.get("files", request.getQuery().get("name"));
				if (path.toFile().exists()) {
					if (path.toFile().isFile()) {
						byte[] bytes = Files.readAllBytes(path);
						response.getStream().write(bytes, 0, bytes.length);
					} else {
						response.setStatus(403);
					}
				} else {
					response.setStatus(404);
				}
			} else {
				response.getWriter().print("<ul>");
				Arrays.stream(Paths.get("files").toFile().listFiles(file -> file.isFile()))
					.forEach(file ->
						response.getWriter().printf(String.format("<li>%s</li>", file.getName())));
				response.getWriter().print("</ul>");
				response.setContentType("text/html");
			}
			break;
		case "POST":
			if (request.getQuery().containsKey("name")) {
				Files.write(Paths.get("files", request.getQuery().get("name")),
						request.getBody());
				response.setStatus(201);
			} else {
				response.setStatus(400);
			}
			break;
		case "DELETE":
			if (request.getQuery().containsKey("name")) {
				Path path = Paths.get("files", request.getQuery().get("name"));
				if (path.toFile().exists()) {
					path.toFile().delete();
					response.setStatus(204);
				} else {
					response.setStatus(404);
				}
			} else {
				response.setStatus(400);
			}
			break;
		default:
			response.setStatus(405);
		}
	}

}
