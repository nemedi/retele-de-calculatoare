package rpc;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RpcContext {
	
	private static Map<RpcService, Map<Socket, Object>> callbacks =
			Collections.synchronizedMap(new HashMap<RpcService, Map<Socket, Object>>());
	private static RpcService service;
	private static Socket socket;
	private static Object callback;
	
	public static void process(RpcService service, RpcRequest request, Socket socket) throws Exception {
		synchronized (RpcContext.callbacks) {
			if (!callbacks.containsKey(service)) {
				callbacks.put(service, new HashMap<Socket, Object>());
			}
			if (!callbacks.get(service).containsKey(socket)) {
				callbacks.get(service).put(socket, RpcProxy.createProxy(service.getCallbackType(), socket));
			}
			RpcContext.service = service;
			RpcContext.socket = socket;
			RpcContext.callback = callbacks.get(service).get(socket);
			service.process(request);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getCallback() {
		return (T) callback;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getCallback(Socket socket) {
		return (T) callbacks.get(service).get(socket);
	}
	
	public static Socket getSocket() {
		return socket;
	}
}
