package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerService extends Remote {

	String[] getProgramms() throws RemoteException;
	
	String getProgram(String name) throws RemoteException;
	
	void startDebugging(String program, int[] breakpoints, String clientEndpoint) throws RemoteException;
	
	void continueDebugging(String program) throws RemoteException;
	
	void stopDebugging(String program) throws RemoteException;
	
}
