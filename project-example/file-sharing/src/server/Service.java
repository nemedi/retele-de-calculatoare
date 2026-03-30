package server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import common.CallbackContract;
import common.Contract;
import rpc.RpcContext;

public class Service implements Contract {

	private Map<Socket, User> users = new HashMap<Socket, User>();
	
	@Override
	public void login(String name, List<String> files) {
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
		if (!users.containsKey(RpcContext.getSocket())) {
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
			Map<String, List<String>> filesByUser = new HashMap<String, List<String>>();
			for (Entry<Socket, User> entry : users.entrySet()) {
				filesByUser.put(entry.getValue().getName(), entry.getValue().getFiles());
			}
			callback.onGetFilesByUser(filesByUser);
		}
	}

	@Override
	public void transferFile(String user, String file) throws IOException {
		CallbackContract callback = RpcContext.getCallback();
		if (!users.containsKey(RpcContext.getSocket())) {
			callback.onDeny("You must be logged-in.");
		} else {
			Optional<Socket> socket = users.entrySet().stream()
					.filter(entry -> user.equals(entry.getValue().getName()))
					.map(entry -> entry.getKey())
					.findAny();
			if (socket.isEmpty()) {
				callback.onDeny(String.format("User '%s' not found.", user));
			} else {
				CallbackContract socketCallback = (CallbackContract) RpcContext.getCallback(socket.get());
				socketCallback.onReadFile(getUser().get().getName(), file);
			}
		}
	}
	
	private Optional<User> getUser() {
		final Socket socket = RpcContext.getSocket();
		return users.entrySet().stream()
				.filter(entry -> entry.getKey().equals(socket))
				.map(entry -> entry.getValue())
				.findAny();
	}

	@Override
	public void onReadFile(String user, String file, String content) throws IOException {
		CallbackContract callback = RpcContext.getCallback();
		if (!users.containsKey(RpcContext.getSocket())) {
			callback.onDeny("You must be logged-in.");
		} else {
			Optional<Socket> socket = users.entrySet().stream()
					.filter(entry -> user.equals(entry.getValue().getName()))
					.map(entry -> entry.getKey())
					.findAny();
			if (socket.isEmpty()) {
				callback.onDeny(String.format("User '%s' not found.", user));
			} else {
				CallbackContract socketCallback = (CallbackContract) RpcContext.getCallback(socket.get());
				socketCallback.onWriteFile(file, content);
			}
		}
	}

}
