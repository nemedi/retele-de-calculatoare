package server;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import common.IClientService;
import common.IServerService;
import common.RegistryUtility;
import common.Service;
import common.Settings;
import server.runtime.DebuggerTask;

public class ServerService extends Service implements IServerService {

	private static final long serialVersionUID = 1L;
	private ExecutorService executorService;
	private Map<String, DebuggerTask> debuggerTasks;
	
	private File directory;
	
	protected ServerService() throws RemoteException {
		super(Settings.SERVER_SERVICE);
		this.directory = new File(Settings.SERVER_DIRECTORY);
		executorService = Executors.newFixedThreadPool(20 * Runtime.getRuntime().availableProcessors());
		debuggerTasks = new HashMap<String, DebuggerTask>();
	}

	@Override
	public String[] getProgramms() throws RemoteException {
		List<String> programs = Arrays.asList(directory.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".prg");
			}
		})).stream().map(name -> new File(name).getName())
		.sorted()
		.collect(Collectors.toList());
		return programs.toArray(new String[programs.size()]);
	}

	@Override
	public String getProgram(String name) throws RemoteException {
		try {
			return new String(Files.readAllBytes(Paths.get(directory.getName(), name)));
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void startDebugging(String program, int[] breakpoints, String clientEndpoint) throws RemoteException {
		try {
			debuggerTasks.put(program, new DebuggerTask(Paths.get(directory.getAbsolutePath(), program),
					breakpoints,
					executorService,
					RegistryUtility.getClient(clientEndpoint, IClientService.class)));
		} catch (NotBoundException | URISyntaxException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void continueDebugging(String program) throws RemoteException {
		if (debuggerTasks.containsKey(program)) {
			debuggerTasks.get(program).getEvaluator().continueDebugging();
		} else {
			throw new RemoteException("Program not found.");
		}
	}

	@Override
	public void stopDebugging(String program) throws RemoteException {
		if (debuggerTasks.containsKey(program)) {
			debuggerTasks.get(program).getEvaluator().stopDebugging();
		} else {
			throw new RemoteException("Program not found.");
		}
	}

}
