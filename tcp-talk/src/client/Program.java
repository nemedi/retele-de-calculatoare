package client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import common.Settings;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class Program extends Shell {
	
	private List list;
	private Text text;
	private Client client;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Program shell = new Program(display);
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
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Program(Display display) throws UnknownHostException, IOException {
		super(display, SWT.SHELL_TRIM);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					client.close();
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
				try {
					if (e.keyCode == SWT.CR && text.getText().trim().length() > 0) {
						client.send(text.getText().trim());
						text.setText("");
					}
				} catch (Exception exception) {
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		createContents();
		client = new Client(Settings.HOST, Settings.PORT, message -> {
			Display.getDefault().asyncExec(() -> {
				list.add(message);
				list.redraw();
			});
		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Talk");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
