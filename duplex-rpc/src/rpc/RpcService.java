package rpc;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

class RpcService {
	
	private Class<?> type;
	private Class<?> callbackType;
	private Object instance;
	
	public RpcService(Object instance, boolean mustHaveCallback) {
		this.instance = instance;
		this.type = instance.getClass();
		if (mustHaveCallback) {
			Optional<RpcCallback> callback = Arrays.stream(this.type.getInterfaces())
					.filter(type -> type.getAnnotation(RpcCallback.class) != null)
					.map(type -> type.getAnnotation(RpcCallback.class))
					.findFirst();
			if (callback.isPresent()) {
				this.callbackType = callback.get().value();
			} else {
				throw new IllegalArgumentException(String.format("Type '%s' must implement an interface annotated with '@%s'.",
						instance.getClass().getName(),
						RpcCallback.class.getName())); 
			}
		}
	}

	public void process(RpcRequest request)
			throws Exception {
		Optional<Method> method = getMethod(request);
		if (method.isPresent()) {
			try {
				Class<?>[] types = method.get().getParameterTypes();
				Object[] arguments = request.getArguments();
				for (int i = 0; i < arguments.length; i++) {
					arguments[i] = RpcTransport.deserialize((String) arguments[i],
							types[i]);
				}
				method.get().invoke(instance, arguments);
			} catch (Exception exception) {
			}
		}
	}
	
	public Class<?> getCallbackType() {
		return callbackType;
	}
	
	private Optional<Method> getMethod(RpcRequest request) {
		return Arrays.asList(type.getMethods())
				.stream()
				.filter(m -> m.getName().equals(request.getMethod())
						&& m.getParameterTypes().length == request.getArguments().length)
				.findFirst();
	}

}
