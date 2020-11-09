package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.IChatCallback;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class ChatShell extends Shell implements IChatCallback {
	private Text txtName;
	private Text txtMessage;
	private List lstUsers;
	private List lstMessages;
	private ChatCallbackService client;
	private Button btnLogin;

	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ChatShell shell = new ChatShell(display);
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

	/**
	 * Create the shell.
	 * @param display
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	public ChatShell(Display display) throws RemoteException, MalformedURLException, NotBoundException {
		super(display, SWT.SHELL_TRIM);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					client.logout();
					System.exit(0);
				} catch (RemoteException exception) {
				}
			}
		});
		setLayout(new GridLayout(4, false));
		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		
		txtName = new Text(this, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		this.btnLogin = new Button(this, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (btnLogin.getText().equalsIgnoreCase("Login")) {
						if (!txtName.getText().trim().isEmpty()) {
							client.login(txtName.getText().trim());
						}
					} else {
						client.logout();
					}
					
				} catch (RemoteException e1) {
				}
			}
		});
		btnLogin.setText("Login");
		new Label(this, SWT.NONE);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
		this.lstUsers = new List(sashForm, SWT.BORDER);
		lstUsers.setEnabled(false);
		
		this.lstMessages = new List(sashForm, SWT.BORDER);
		lstMessages.setEnabled(false);
		sashForm.setWeights(new int[] {1, 2});
		new Label(this, SWT.NONE);
		
		Label lblMessage = new Label(this, SWT.NONE);
		lblMessage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMessage.setText("Message");
		
		txtMessage = new Text(this, SWT.BORDER);
		txtMessage.setEnabled(false);
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (e.keyCode == SWT.CR
							&& lstUsers.getSelectionCount() == 1
							&& !txtMessage.getText().trim().isEmpty()) {
						client.send(lstUsers.getSelection()[0], txtMessage.getText().trim());
						txtMessage.setText("");
					}
				} catch (RemoteException e1) {
				}
			}
		});
		txtMessage.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));


		createContents();
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		String host = bundle.getString("host");
		int port = Integer.parseInt(bundle.getString("port"));
		this.client = new ChatCallbackService(host, port, this);
		new Label(this, SWT.NONE);
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Chat");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void onAccept(String[] names) throws RemoteException {
		getDisplay().asyncExec(() -> {
			btnLogin.setText("Logout");
			txtName.setEnabled(false);
			lstUsers.setEnabled(true);
			lstMessages.setEnabled(true);
			txtMessage.setEnabled(true);
			lstUsers.setItems(names);
			lstUsers.setSelection(0);
			txtMessage.setFocus();
		});		
	}

	@Override
	public void onDeny() throws RemoteException {
		getDisplay().asyncExec(() -> {
			
		});		
	}

	@Override
	public void onAddUser(String name) throws RemoteException {
		getDisplay().asyncExec(() -> {
			lstUsers.add(name);
		});		
	}

	@Override
	public void onRemoveUser(String name) throws RemoteException {
		getDisplay().asyncExec(() -> {
			lstUsers.remove(name);
		});
	}

	@Override
	public void onReceive(String from, String message) throws RemoteException {
		getDisplay().asyncExec(() -> {
			lstMessages.add(String.format("%s: %s", from, message));
		});		
	}

	@Override
	public void onExit() throws RemoteException {
		getDisplay().asyncExec(() -> {
			btnLogin.setText("Login");
			lstUsers.removeAll();
			lstMessages.removeAll();
			txtMessage.setText("");
			lstUsers.setEnabled(true);
			lstMessages.setEnabled(true);
			txtMessage.setEnabled(true);
			txtName.setEnabled(true);
			txtName.setFocus();
		});
	}
}
