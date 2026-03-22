package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IChat;
import common.IChatCallback;
import common.RegistryUtility;
import common.Settings;

public class ChatCallbackService implements IChatCallback {

	private IChat stub;
	private IChatCallback callback;

	public ChatCallbackService(IChatCallback callback)
			throws RemoteException, MalformedURLException, NotBoundException {
		RegistryUtility.getRegistry(Settings.CLIENT_REGISTRY_PORT)
			.rebind(Settings.CLIENT_SERVICE_NAME,
					UnicastRemoteObject.exportObject(this, Settings.CLIENT_SERVICE_PORT));
		this.stub = (IChat) RegistryUtility.getRegistry(Settings.SERVER_REGISTRY_HOST, Settings.SERVER_REGISTRY_PORT)
				.lookup(Settings.SERVER_SERVICE_NAME);
		this.callback = callback;
	}

	@Override
	public void onAccept(String[] names) throws RemoteException {
		callback.onAccept(names);
	}

	@Override
	public void onDeny() throws RemoteException {
		callback.onDeny();
	}

	@Override
	public void onAddUser(String name) throws RemoteException {
		callback.onAddUser(name);
	}

	@Override
	public void onRemoveUser(String name) throws RemoteException {
		callback.onRemoveUser(name);
	}

	@Override
	public void onReceive(String from, String message) throws RemoteException {
		callback.onReceive(from, message);
	}

	@Override
	public void onExit() throws RemoteException {
		callback.onExit();
	}

	public void login(String name) throws RemoteException {
		stub.login(name, this);
	}

	public void logout() throws RemoteException {
		stub.logout(this);
	}

	public void send(String to, String message) throws RemoteException {
		stub.send(to, message, this);
	}

}
