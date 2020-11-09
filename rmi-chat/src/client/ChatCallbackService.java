package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import common.IChat;
import common.IChatCallback;

public class ChatCallbackService extends UnicastRemoteObject implements IChatCallback {

	private static final long serialVersionUID = 1L;
	private IChat stub;
	private IChatCallback callback;

	public ChatCallbackService(String host, int port, IChatCallback callback)
			throws RemoteException, MalformedURLException, NotBoundException {
		int localPort = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(localPort);
		} catch (RemoteException e) {
			registry = LocateRegistry.getRegistry(localPort);
		}
		if (registry != null) {
			registry.rebind("chatCallback", this);
		}
		this.stub = (IChat) Naming.lookup(String.format("rmi://%s:%d/chat", host, port));
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
