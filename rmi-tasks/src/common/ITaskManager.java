package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITaskManager<T, V> extends Remote {

	T execute(ITask<T, V> task, V argument) throws RemoteException;
}
