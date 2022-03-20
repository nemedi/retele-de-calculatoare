package client;

import java.net.MalformedURLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import common.ClientContract;

public class ClientApplication extends Shell implements ClientContract {
	private Text text;
	private List list;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ClientApplication shell = new ClientApplication(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			System.exit(0);
		} catch (Exception e) {
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 * @throws MalformedURLException 
	 */
	public ClientApplication(Display display) throws MalformedURLException {
		super(display, SWT.SHELL_TRIM);
		final ClientService clientService = new ClientService(this);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					clientService.close();
				} catch (Exception exception) {
				}
			}
		});
		setLayout(new GridLayout(1, false));
		
		list = new List(this, SWT.BORDER);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		text = new Text(this, SWT.BORDER);
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR && !text.getText().trim().isEmpty()) {
					clientService.send(text.getText().trim());
					text.setText("");
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Talk");
		setSize(500, 400);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void onReceive(String message) {
		try {
			getDisplay().asyncExec(() -> {
				list.add(message);
				list.redraw();
			});
		} catch (Exception e) {
		}
	}

}
