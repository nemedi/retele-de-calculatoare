package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote {

	void login(String name, IChatCallback callback) throws RemoteException;
	
	void logout(IChatCallback callback) throws RemoteException;
	
	void send(String to, String message, IChatCallback callback) throws RemoteException;
}
