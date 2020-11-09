package server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.IChat;
import common.IChatCallback;

public class ChatService implements IChat {

	private Map<String, IChatCallback> users = new HashMap<String, IChatCallback>();

	public ChatService(Registry registry, String name) throws RemoteException {
		registry.rebind(name, UnicastRemoteObject.exportObject(this, 0));
	}

	@Override
	public void login(String name, IChatCallback callback) throws RemoteException {
		if (users.containsKey(name)) {
			callback.onDeny();
		} else {
			final List<String> names = new ArrayList<String>();
			names.add("*");
			users.entrySet().stream().forEach(entry -> {
				try {
					entry.getValue().onAddUser(name);
					names.add(entry.getKey());
				} catch (RemoteException e) {
				}
			});
			users.put(name, callback);
			callback.onAccept(names.toArray(new String[names.size()]));
		}
	}

	@Override
	public void logout(IChatCallback callback) throws RemoteException {
		final Optional<String> name = getName(callback);
		if (name.isPresent()) {
			users.remove(name.get());
			users.entrySet().forEach(entry -> {
				try {
					entry.getValue().onRemoveUser(name.get());
				} catch (RemoteException e) {
				}
			});
			callback.onExit();
		}
	}

	@Override
	public void send(String to, String message, IChatCallback callback) throws RemoteException {
		final Optional<String> name = getName(callback);
		if (name.isPresent()) {
			users.entrySet()
				.stream()
				.filter(entry -> "*".equals(to) 
						|| entry.getKey().equals(to)
						|| entry.getValue().equals(callback))
				.forEach(entry -> {
					try {
						entry.getValue().onReceive(name.get(), message);
					} catch (RemoteException e) {
					}
				});
		}
	}
	
	private Optional<String> getName(IChatCallback callback) {
		return users.entrySet()
				.stream()
				.filter(entry -> entry.getValue().equals(callback))
				.map(entry -> entry.getKey())
				.findAny();
	}

}
