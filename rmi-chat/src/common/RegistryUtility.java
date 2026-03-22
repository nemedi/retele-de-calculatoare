package common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class RegistryUtility {

	public static Registry getRegistry(int port) throws RemoteException {
		try {
			return LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			return LocateRegistry.getRegistry(port);
		}
	}
	
	public static Registry getRegistry(String host, int port) throws RemoteException {
		return LocateRegistry.getRegistry(host, port);
	}
}
