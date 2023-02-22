package common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Service extends UnicastRemoteObject implements AutoCloseable {

	private static final long serialVersionUID = 1L;
	private String name;

	protected Service(String name) throws RemoteException {
		this.name = name;
		RegistryUtility.publishService(name, this);
	}

	@Override
	public void close() throws Exception {
		RegistryUtility.unpublishService(name);
	}

}
