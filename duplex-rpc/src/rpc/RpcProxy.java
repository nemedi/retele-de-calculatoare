package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

public class RpcProxy {

	private static class Worker implements InvocationHandler, Runnable {
		
		private Socket socket;
		private String service;
		private RpcService callbackService;
		private PrintWriter writer;
		private BufferedReader reader;
		
		private Worker(String service, Socket socket) throws UnknownHostException, IOException {
			this.socket = socket;
			this.service = service;
			writer = new PrintWriter(socket.getOutputStream());
		}
		
		private Worker(String endpoint, Object callback) throws UnknownHostException, IOException {
			URI uri = URI.create(endpoint);
			socket = new Socket(InetAddress.getByName(uri.getHost()), uri.getPort());
			service = uri.getPath();
			if (service.startsWith("/")) {
				service = this.service.substring(1);
			}
			callbackService = new RpcService(callback, false); 
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(this).start();
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
				throws Throwable {
			return invoke(method.getName(), arguments, method.getReturnType());
		}
		
		private Object invoke(String method, Object[] arguments, Class<?> resultType)
				throws Exception {
				RpcRequest request = new RpcRequest(service, method, arguments);
				writer.println(RpcTransport.serialize(request));
				writer.flush();
				return null;
		}

		@Override
		public void run() {
			if (callbackService != null) {
				while (socket != null && !socket.isClosed()) {
					try {
						RpcRequest request = RpcTransport.deserialize(reader.readLine(), RpcRequest.class);
						callbackService.process(request);
					} catch (Exception e) {
					}
				}
			}
		}
	}
	
	private RpcProxy() {
		
	}
	
	public static <T, U> T createProxy(Class<T> type, String endpoint, U callback) throws UnknownHostException, IOException {
		for (Method method : type.getDeclaredMethods()) {
			if (!void.class.equals(method.getReturnType())) {
				throw new IllegalArgumentException(
						String.format("Only methods that return void are allowed. (method '%s' is returining '%s'.)",
								method.getName(), method.getReturnType().getName()));
			}
		}
		Worker worker = new Worker(endpoint, callback);
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(),
				new Class<?>[] {type},
				worker));
	}
	
	public static <T, U> T createProxy(Class<T> type, Socket socket) throws UnknownHostException, IOException {
		for (Method method : type.getDeclaredMethods()) {
			if (!void.class.equals(method.getReturnType())) {
				throw new IllegalArgumentException(
						String.format("Only methods that return void are allowed. (method '%s' is returining '%s'.)",
								method.getName(), method.getReturnType().getName()));
			}
		}
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(),
				new Class<?>[] {type},
				new Worker(type.getName(), socket)));
	}
	
}