package server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import common.CallbackContract;
import common.Contract;
import rpc.RpcContext;

public class Service implements Contract {

	private Map<Socket, User> users = new HashMap<Socket, User>();
	
	@Override
	public void login(String name, String[] files) {
		CallbackContract callback = RpcContext.getCallback();
		if (users.values().stream()
				.filter(user -> name.equals(user.getName()))
				.findAny().isPresent()) {
		 	callback.onDeny("Another user with the same name already exists.");
		} else {
			User user = new User(name, files);
			users.put(RpcContext.getSocket(), user);
			callback.onLogin();
		}
	}

	@Override
	public void logout() {
		CallbackContract callback = RpcContext.getCallback();
		if (users.containsKey(RpcContext.getSocket())) {
			callback.onDeny("You must be logged-in.");
		} else {
			users.remove(RpcContext.getSocket());
			callback.onLogout();
		}
	}

	@Override
	public void getFilesByUser() {
		CallbackContract callback = RpcContext.getCallback();
		if (!users.containsKey(RpcContext.getSocket())) {
			callback.onDeny("You must be logged-in.");
		} else {
			Map<String, String[]> filesByUser = new HashMap<String, String[]>();
			for (Entry<Socket, User> entry : users.entrySet()) {
				filesByUser.put(entry.getValue().getName(), entry.getValue().getFiles());
			}
			callback.onGetFilesByUser(filesByUser);
		}
	}

	@Override
	public void transferFile(String user, String file) {
	}

	@Override
	public void onReadFile(String name, byte[] data) {
	}

}
