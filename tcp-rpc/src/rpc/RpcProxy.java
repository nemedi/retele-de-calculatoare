package rpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;

public class RpcProxy {

	private static class Worker implements InvocationHandler {
		
		private String host;
		private int port;
		private String service;
		private String session;
		
		private Worker(String endpoint) {
			URI uri = URI.create(endpoint);
			host = uri.getHost();
			port = uri.getPort();
			service = uri.getPath();
			if (service.startsWith("/")) {
				service = service.substring(1);
			}
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
				throws Throwable {
			return invoke(method.getName(), arguments, method.getReturnType());
		}
		
		private Object invoke(String method, Object[] arguments, Class<?> resultType)
				throws Exception {
			try (Socket socket = new Socket(InetAddress.getByName(host), port)) {
				RpcRequest request = new RpcRequest(service, method, arguments, session);
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println(RpcTransport.serialize(request));
				writer.flush();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				RpcResponse response = RpcTransport.deserialize(reader.readLine(), RpcResponse.class); 
				if (response.getFault() != null) {
					throw new Exception(response.getFault());
				}
				session = response.getSession();
				Object result = response.getResult();
				if (!void.class.equals(resultType)) {
					result = RpcTransport.deserialize((String) result, resultType);
				}
				return result;
			}
		}

		@Override
		protected void finalize() throws Throwable {
			invoke(RpcRequest.DESTROY_METHOD, new Object[]{}, void.class);
		}
	}
	
	public static <T> T createProxy(Class<T> type, String endpoint) {
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(),
				new Class<?>[] {type},
				new Worker(endpoint)));
	}
	
}