package server;

public interface WebProcessor {

	void process(WebRequest request, WebResponse response)
			throws Exception;
}

