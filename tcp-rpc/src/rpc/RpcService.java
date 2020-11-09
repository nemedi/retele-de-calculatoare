package rpc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class RpcService {
	
	private Class<?> type;
	private Map<String, Object> instances;
	private Object instance;
	
	public RpcService(Class<?> type) {
		this.type = type;
		this.instances = new HashMap<String, Object>();
	}

	public RpcService(Object instance) {
		this.instance = instance;
		this.type = instance.getClass();
	}

	public void process(RpcRequest request, RpcResponse response)
			throws Exception {
		String session = request.getSession();
		if (RpcRequest.DESTROY_METHOD.equals(request.getMethod())) {
			if (instances.containsKey(session)) {
				instances.remove(session);
				session = null;
			}
		} else {
			Object instance = null;
			Optional<Method> method = getMethod(request);
			if (method.isPresent()) {
				if (!Modifier.isStatic(method.get().getModifiers())) {
					if (this.instance != null) {
						instance = this.instance;
					} else if (session != null && instances.containsKey(session)) {
						instance = instances.get(session);
					} else {
						instances.put(session = UUID.randomUUID().toString(),
								instance = type.getConstructor().newInstance());
					}
				}
				try {
					Class<?>[] types = method.get().getParameterTypes();
					Object[] arguments = request.getArguments();
					for (int i = 0; i < arguments.length; i++) {
						arguments[i] = RpcTransport.deserialize((String) arguments[i],
								types[i]);
					}
					Object result = method.get().invoke(instance, arguments);
					response.setResult(result);
				} catch (Exception exception) {
					response.setFault(exception.getMessage());
				} finally {
					response.setSession(session);
				}
			} else {
				response.setFault("Method not allowed.");
			}
		}
	}
	
	private Optional<Method> getMethod(RpcRequest request) {
		return Arrays.asList(type.getMethods())
				.stream()
				.filter(m -> m.getName().equals(request.getMethod())
						&& m.getParameterTypes().length == request.getArguments().length)
				.findFirst();
	}

}
