package client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DebuggerComposite extends Composite {
	
	private final Color COLOR_GREEN = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	private final Color COLOR_RED = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	private Table table;
	private List<Integer> breakpoints;
	private Button continueButton;
	private int debuggingLine = -1;
	private StyledText styledText;
	private Button debugButton;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws RemoteException 
	 */
	public DebuggerComposite(Composite parent, String program, ClientService clientService) throws RemoteException {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1, false));
		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.styledText = new StyledText(sashForm, SWT.READ_ONLY | SWT.BORDER);
		styledText.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					Point point = new Point(e.x, e.y);
					int offset = styledText.getOffsetAtLocation(point);
					int line = styledText.getLineAtOffset(offset);
					if (line != debuggingLine) {
						if (breakpoints.contains(line)) {
							breakpoints.remove(line);
							styledText.setLineBackground(line, 1, styledText.getBackground());
						} else {
							breakpoints.add(line);
							styledText.setLineBackground(line, 1, COLOR_GREEN);
						}
					}
				} catch (Throwable t) {
					
				}
			}
		});
		
		table = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setWidth(100);
		nameColumn.setText("Variable");
		
		TableColumn valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setWidth(100);
		valueColumn.setText("Value");
		valueColumn.setAlignment(SWT.RIGHT);
		sashForm.setWeights(new int[] {3, 1});
		
		Composite buttonsComposite = new Composite(this, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(3, false));
		
		debugButton = new Button(buttonsComposite, SWT.NONE);
		debugButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if ("start".equalsIgnoreCase(debugButton.getText())) {
						clientService.startDebugging(program, breakpoints.stream().mapToInt(Integer::intValue).toArray());
						debugButton.setText("Stop");
						debuggingLine = -1;
					} else {
						clientService.stopDebugging(program);
						onEnd();
					}
				} catch (RemoteException exception) {
					MessageDialog.openError(parent.getShell(), "Error", exception.getMessage());
				}
			}
		});
		debugButton.setBounds(0, 0, 94, 28);
		debugButton.setText("Start");
		
		this.continueButton = new Button(buttonsComposite, SWT.NONE);
		continueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					styledText.setLineBackground(debuggingLine, 1, COLOR_GREEN);
					clientService.continueDebugging(program);
				} catch (RemoteException exception) {
					MessageDialog.openError(parent.getShell(), "Error", exception.getMessage());
				}
			}
		});
		continueButton.setEnabled(false);
		continueButton.setText("Continue");
		new Label(buttonsComposite, SWT.NONE);
		
		styledText.setText(clientService.getProgram(program));
		this.breakpoints = new ArrayList<Integer>();
	}

	@Override
	protected void checkSubclass() {
	}

	public void onBreakpoint(int line, Map<String, Double> variables) {
		table.removeAll();
		variables.entrySet().stream()
			.sorted((firstEntry, secondEntry) -> firstEntry.getKey().compareTo(secondEntry.getKey()))
			.filter(entry -> !entry.getKey().isEmpty())
			.forEach((entry) -> {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, entry.getKey());
			item.setText(1, Double.toString(entry.getValue()));
		});
		continueButton.setEnabled(true);
		debuggingLine = line;
		styledText.setLineBackground(line, 1, COLOR_RED);
	}
	
	public void onEnd() {
		if (debuggingLine > -1) {
			styledText.setLineBackground(debuggingLine, 1, COLOR_GREEN);
			debuggingLine = -1;
		}
		continueButton.setEnabled(false);
		debugButton.setText("Start");
	}
}
