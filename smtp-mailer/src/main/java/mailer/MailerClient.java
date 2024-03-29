package mailer;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import mailer.Mailer.MailListener;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MailerClient extends Shell implements MailListener {
	private Table table;
	private Text txtFrom;
	private Text txtSubject;
	private Text txtBody;
	private Mailer mailer;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			MailerClient shell = new MailerClient(display);
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
	 * @throws FileNotFoundException 
	 */
	public MailerClient(Display display) throws FileNotFoundException, IOException {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblFrom = new Label(composite, SWT.NONE);
		lblFrom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFrom.setText("From");
		
		txtFrom = new Text(composite, SWT.BORDER);
		txtFrom.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Label lblSubject = new Label(composite, SWT.NONE);
		lblSubject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSubject.setText("Subject");
		
		txtSubject = new Text(composite, SWT.BORDER);
		txtSubject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBody = new Label(composite, SWT.NONE);
		lblBody.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		lblBody.setText("Body");
		
		txtBody = new Text(composite, SWT.BORDER | SWT.MULTI);
		txtBody.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Button btnSend = new Button(composite, SWT.NONE);
		btnSend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txtFrom.getText().trim().length() > 0
						&& txtSubject.getText().trim().length() > 0
						&& txtBody.getText().trim().length() > 0) {
					MailerClient.this.getDisplay().asyncExec(() -> {
						try {
							mailer.send(txtFrom.getText().trim(),
									txtSubject.getText().trim(),
									txtBody.getText().trim());
						} catch (Exception exception) {
							MessageDialog.openError(MailerClient.this, "Error", exception.getMessage());
						}
					});
				}
			}
		});
		btnSend.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		btnSend.setText("Send to List");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		table = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		sashForm.setWeights(new int[] {1, 1});
		createContents();
		
		this.mailer = new Mailer(Settings.ID,
				Settings.HOST,
				Settings.PORT,
				"data/mailer.csv",
				this);
		if (!mailer.getData().isEmpty()) {
			for (String field : mailer.getData().getFields()) {
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setWidth(100);
				tableColumn.setText(field);
			}
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			tableColumn.setWidth(100);
			tableColumn.setText("Feedback");
			for (int i = 0; i < mailer.getData().getLength(); i++) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				for (int j = 0; j < mailer.getData().getFields().length; j++) {
					tableItem.setText(j, mailer.getData().getValue(i, j));
				}
			}
			initializeWithDummyValues();
		}
		
	}

	private void initializeWithDummyValues() {
		txtSubject.setText("Test");
		StringBuilder builder = new StringBuilder();
		for (String field : mailer.getData().getFields()) {
			builder.append("{{").append(field).append("}} ");
		}
		txtBody.setText(builder.toString());
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Mailer Client");
		setSize(640, 480);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void onSent(int index, String feedback) {
		getDisplay().asyncExec(() -> {
			TableItem tableItem = table.getItem(index);
			tableItem.setText(mailer.getData().getFields().length, feedback);
		});
		
	}

}
