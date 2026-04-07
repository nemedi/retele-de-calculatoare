package rpc;

class RpcRequest {

	private String service;
	private String method;
	private Object[] arguments;
	
	public RpcRequest(String service, String method, Object[] arguments) {
		this.service = service;
		this.method = method;
		this.arguments = arguments;
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

}
