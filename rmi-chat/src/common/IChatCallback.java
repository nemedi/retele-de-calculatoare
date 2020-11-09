package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatCallback extends Remote {

	void onAccept(String[] names) throws RemoteException;
	
	void onDeny() throws RemoteException;
	
	void onAddUser(String name) throws RemoteException;
	
	void onRemoveUser(String name) throws RemoteException;
	
	void onReceive(String from, String message) throws RemoteException;
	
	void onExit() throws RemoteException;
}
