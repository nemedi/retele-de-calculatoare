package common;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RegistryUtility {
	
	private static Registry registry;

	private static Registry getRegistry(int port) throws RemoteException {
		if (registry == null) {
			try {
				registry = LocateRegistry.createRegistry(port);
			} catch (RemoteException e) {
				registry = LocateRegistry.getRegistry(port);
			}
		}
		return registry;
	}
	
	private static Registry getRegistry(URI uri) throws RemoteException {
		return LocateRegistry.getRegistry(uri.getHost(), uri.getPort());
	}
	
	public static <T extends UnicastRemoteObject> void publishService(String name, T service) throws AccessException, RemoteException {
		getRegistry(Settings.REGISTRY_PORT).rebind(name, service);
	}
	
	public static void unpublishService(String name) throws AccessException, RemoteException, NotBoundException {
		getRegistry(Settings.REGISTRY_PORT).unbind(name);
	}
	
	public static <T extends Remote> T getClient(String endpoint, Class<T> contract) throws AccessException, RemoteException, NotBoundException, URISyntaxException {
		URI uri = new URI(endpoint);
		return contract.cast(getRegistry(uri).lookup(uri.getPath().substring(1)));
	}
}
