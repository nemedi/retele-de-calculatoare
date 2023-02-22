package client;

import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import com.ibm.icu.text.MessageFormat;

import common.IClientService;
import common.IServerService;
import common.RegistryUtility;
import common.Service;
import common.Settings;

public class ClientService extends Service implements IClientService {

	private static final long serialVersionUID = 1L;
	private IServerService serverService;
	private IClientService callbackService;

	protected ClientService(IClientService callbackService) throws RemoteException {
		super(Settings.CLIENT_SERVICE);
		try {
			this.callbackService = callbackService;
			String serverEndpoint = MessageFormat.format("rmi://{0}:{1}/{2}", Settings.SERVER_HOST,
					Integer.toString(Settings.REGISTRY_PORT),
					Settings.SERVER_SERVICE);
			this.serverService = RegistryUtility.getClient(serverEndpoint, IServerService.class);
		} catch (NotBoundException | URISyntaxException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void onBreakpoint(String program, int line, Map<String, Double> variables) throws RemoteException {
		if (callbackService != null) {
			callbackService.onBreakpoint(program, line, variables);
		}
	}
	
	@Override
	public void onEnd(String program) throws RemoteException {
		if (callbackService != null) {
			callbackService.onEnd(program);
		}
	}

	public String[] getProgramms() throws RemoteException {
		return serverService.getProgramms();
	}

	public String getProgram(String name) throws RemoteException {
		return serverService.getProgram(name);
	}

	public void startDebugging(String program, int[] breakpoints) throws RemoteException {
		String clientEndpoint = MessageFormat.format("rmi://{0}:{1}/{2}", Settings.CLIENT_HOST,
				Integer.toString(Settings.REGISTRY_PORT),
				Settings.CLIENT_SERVICE);
		serverService.startDebugging(program, breakpoints, clientEndpoint);
	}

	public void continueDebugging(String program) throws RemoteException {
		serverService.continueDebugging(program);
	}

	public void stopDebugging(String program) throws RemoteException {
		serverService.stopDebugging(program);
	}

}
