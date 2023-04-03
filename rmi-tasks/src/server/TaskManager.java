package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.ITask;
import common.ITaskManager;

public class TaskManager<T, V> implements ITaskManager<T, V> {
	
	public TaskManager(Registry registry, String name)
			throws RemoteException, MalformedURLException {
		registry.rebind(name, UnicastRemoteObject.exportObject(this, 0));
	}

	@Override
	public T execute(ITask<T, V> task, V argument) throws RemoteException {
		try {
			return task.execute(argument);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
