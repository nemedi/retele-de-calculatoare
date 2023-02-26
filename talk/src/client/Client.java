package client;

public abstract class Client implements AutoCloseable {

	
	public Client(String host, int port, IClientCallback callback) throws Exception {
		open(host, port);
		new Thread(() -> {
			while (!isClosed()) {
				try {
					callback.onTalk(receive());
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	protected abstract void open(String host, int port) throws Exception;
	
	protected abstract boolean isClosed();
	
	protected abstract String receive() throws Exception;
	
	protected abstract void send(String message) throws Exception;
	
	protected abstract void onClose() throws Exception;
	
	@Override
	public final void close() throws Exception {
		onClose();
	}
}
