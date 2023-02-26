package server;

import java.io.IOException;

public interface IServer extends AutoCloseable {

	void start(int port) throws IOException;
	
	void stop() throws IOException;
}
