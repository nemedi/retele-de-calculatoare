package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IClientService extends Remote {

	void onBreakpoint(String program, int line, Map<String, Double> variables) throws RemoteException;
	
	void onEnd(String program) throws RemoteException;
}
