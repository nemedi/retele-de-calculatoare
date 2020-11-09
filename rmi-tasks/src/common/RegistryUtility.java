package common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryUtility {

	public static Registry getRegistry(int port) throws RemoteException {
		try {
			return LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			return LocateRegistry.getRegistry(port);
		}
	}
}
