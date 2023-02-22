package client;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import common.IClientService;

public class ClientShell extends Shell {
	
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ClientShell shell = new ClientShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ClientShell(Display display) throws RemoteException {
		super(display, SWT.SHELL_TRIM);
		final Map<String, DebuggerComposite> debuggerComposites = new HashMap<String, DebuggerComposite>();
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.exit(0);
			}
		});
		setLayout(new GridLayout(1, false));
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setBounds(0, 0, 147, 52);
		IClientService callbackService = new IClientService() {
			
			@Override
			public void onEnd(String program) throws RemoteException {
				if (debuggerComposites.containsKey(program)) {
					Display.getDefault().asyncExec(() -> {
						debuggerComposites.get(program).onEnd();
					});
				}
			}
			
			@Override
			public void onBreakpoint(String program, int line, Map<String, Double> variables) throws RemoteException {
				if (debuggerComposites.containsKey(program)) {
					Display.getDefault().asyncExec(() -> {
						debuggerComposites.get(program).onBreakpoint(line, variables);
					});
				}
			}
		};
		ClientService clientService = new ClientService(callbackService);
		Arrays.asList(clientService.getProgramms()).stream().sorted().forEach(program -> {
			try {
				TabItem item = new TabItem(tabFolder, SWT.NONE);
				item.setText(program);
				DebuggerComposite debuggerComposite = new DebuggerComposite(tabFolder, program, clientService);
				debuggerComposites.put(program, debuggerComposite);
				item.setControl(debuggerComposite);
			} catch (RemoteException e) {
			}
		});
		createContents();
	}

	protected void createContents() {
		setText("Debugger");
		setSize(1280, 720);
	}
	
	@Override
	protected void checkSubclass() {
	}
	
}
