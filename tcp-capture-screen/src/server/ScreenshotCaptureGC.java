package server;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ScreenshotCaptureGC {

  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell(display);

    Button button = new Button(shell, SWT.PUSH);
    button.setText("Capture");
    button.pack();
    button.setLocation(10, 140);
    button.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        GC gc = new GC(display);
        final Image image = new Image(display, 400,400);
        gc.copyArea(image, 0, 0);
        gc.dispose();

        Shell popup = new Shell(shell);
        popup.setText("Image");
        popup.addListener(SWT.Close, new Listener() {
          public void handleEvent(Event e) {
            image.dispose();
          }
        });

        Canvas canvas = new Canvas(popup, SWT.NONE);
        canvas.setBounds(10, 10, 400 + 10, 400 + 10);
        canvas.addPaintListener(new PaintListener() {
          public void paintControl(PaintEvent e) {
            e.gc.drawImage(image, 0, 0);
          }
        });
        popup.pack();
        popup.open();
      }
    });
    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}