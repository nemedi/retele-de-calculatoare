package trace;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class TraceShell extends Shell {

	private ServerSocket serverSocket;
	private Map<String, StringBuilder[]> data;
	private AtomicReference<String> selectedItem = new AtomicReference<String>(null);
	private StyledText[] styledTexts;

	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			TraceShell shell = new TraceShell(display);
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

	public TraceShell(Display display) {
		super(display, SWT.SHELL_TRIM);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					if (serverSocket != null && !serverSocket.isClosed()) {
						serverSocket.close();
					}
					System.exit(0);
				} catch (IOException exception) {
					MessageDialog.openError(TraceShell.this, "Error", exception.getMessage());
				}
			}
		});
		setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(7, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label listenLabel = new Label(composite, SWT.NONE);
		listenLabel.setText("Listen");
		
		final Text listenText = new Text(composite, SWT.BORDER | SWT.RIGHT);
		listenText.setText("7070");
		listenText.setBounds(0, 0, 64, 19);
		Label forwardToLabel = new Label(composite, SWT.NONE);
		forwardToLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		forwardToLabel.setBounds(0, 0, 59, 14);
		forwardToLabel.setText("Forward To");
		
		final Text forwardToHostText = new Text(composite, SWT.BORDER);
		forwardToHostText.setText("127.0.0.1");
		forwardToHostText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		final Text forwardToPortText = new Text(composite, SWT.BORDER | SWT.RIGHT);
		forwardToPortText.setText("8080");
		forwardToPortText.setSize(64, 19);
		
		Button button = new Button(composite, SWT.NONE);
		button.setText("Start");
		new Label(composite, SWT.NONE);
		
		SashForm outerSashForm = new SashForm(this, SWT.NONE);
		outerSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		final List list = new List(outerSashForm, SWT.BORDER);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionCount() == 1) {
					selectedItem.set(list.getSelection()[0]);
					if (data.containsKey(selectedItem.get())) {
						styledTexts[0].setText(data.get(selectedItem.get())[0].toString());
						styledTexts[1].setText(data.get(selectedItem.get())[1].toString());
					}
				} else {
					selectedItem.set(null);
					styledTexts[0].setText("");
					styledTexts[1].setText("");
				}
			}
		});
		list.setEnabled(false);
		
		SashForm innerSashForm = new SashForm(outerSashForm, SWT.VERTICAL);
		
		final StyledText inboundStyledText = new StyledText(innerSashForm, SWT.BORDER | SWT.READ_ONLY);
		
		final StyledText outboundStyledText = new StyledText(innerSashForm, SWT.BORDER | SWT.READ_ONLY);
		innerSashForm.setWeights(new int[] {1, 1});
		outerSashForm.setWeights(new int[] {1, 3});
		data = new HashMap<String, StringBuilder[]>();
		styledTexts = new StyledText[] {inboundStyledText, outboundStyledText};
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (serverSocket == null) {
						listenText.setEnabled(false);
						forwardToHostText.setEnabled(false);
						forwardToPortText.setEnabled(false);
						list.setEnabled(true);
						button.setText("Stop");
						serverSocket = new ServerSocket(Integer.parseInt(listenText.getText().trim()));
						final String host = forwardToHostText.getText().trim();
						final int port = Integer.parseInt(forwardToPortText.getText().trim());
						final ExecutorService executorService = Executors.newFixedThreadPool(
								20 * Runtime.getRuntime().availableProcessors());
						executorService.execute(() -> {
							while (serverSocket != null && !serverSocket.isClosed()) {
								try {
									final Socket inboundSocket = serverSocket.accept();
									final Socket outboundSocket = new Socket(host, port);
									final String item = inboundSocket.getRemoteSocketAddress()
											.toString().substring(1);
									data.put(item, new StringBuilder[]{
											new StringBuilder(), new StringBuilder()});
									getDisplay().asyncExec(() -> {
										list.add(item);
										list.redraw();
									});
									executorService.submit(() -> {
										forward(item, inboundSocket, outboundSocket, 0);
									});
									executorService.submit(() -> {
										forward(item, outboundSocket, inboundSocket, 1);
									});
								} catch (IOException exception) {
									getDisplay().asyncExec(() -> {
										MessageDialog.openError(TraceShell.this,
												"Error", exception.getMessage());
									});
								}
							}
						});
					} else {
						serverSocket.close();
						serverSocket = null;
						list.removeAll();
						inboundStyledText.setText("");
						outboundStyledText.setText("");
						listenText.setEnabled(true);
						forwardToHostText.setEnabled(true);
						forwardToPortText.setEnabled(true);
						list.setEnabled(false);
						button.setText("Start");
					}
				} catch (NumberFormatException | IOException exception) {
					MessageDialog.openError(TraceShell.this,
							"Error", exception.getMessage());
				}
			}
		});
		createContents();
	}
	
	private void forward(String item, Socket inboundSocket, Socket outboundSocket, int index) {
		while (inboundSocket != null && !inboundSocket.isClosed()) {
			try {
				final int available = inboundSocket.getInputStream().available();
				if (available > 0) {
					byte[] buffer = new byte[available];
					int count = 0;
					while (count < buffer.length) {
						count += inboundSocket.getInputStream().read(buffer, count, buffer.length);
					}
					if (data.containsKey(item)) {
						data.get(item)[index].append(new String(buffer));
						if (item.equals(selectedItem.get())) {
							styledTexts[index].setText(data.get(item)[index].toString());
						}
					}
					if (outboundSocket != null && !outboundSocket.isClosed()) {
						outboundSocket.getOutputStream().write(buffer, 0, buffer.length);
						outboundSocket.getOutputStream().flush();
					}
				}
			} catch (IOException exception) {
			}
		}
		if (!outboundSocket.isClosed()) {
			try {
				outboundSocket.close();
			} catch (IOException exception) {
			}
		}
	}

	protected void createContents() {
		setText("TCP Trace");
		setSize(700, 500);

	}

	@Override
	protected void checkSubclass() {
	}
}
