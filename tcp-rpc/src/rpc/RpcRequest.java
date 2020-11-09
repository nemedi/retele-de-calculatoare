package rpc;

class RpcRequest {

	public static final String DESTROY_METHOD = ".destroy";
	private String service;
	private String method;
	private Object[] arguments;
	private String session;
	
	public RpcRequest(String service,
			String method, Object[] arguments, String session) {
		this.service = service;
		this.method = method;
		this.arguments = arguments;
		this.session = session;
	}

	public String getService() {
		return service;
	}

	public String getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public String getSession() {
		return session;
	}
	
	
}
