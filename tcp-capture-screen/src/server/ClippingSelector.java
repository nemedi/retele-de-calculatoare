package server;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
 
public class ClippingSelector {
 
    public Rectangle select() {
 
        final Display display = Display.getDefault();
 
        //convert desktop to image
        final Image backgroundImage = new Image(display, display.getBounds().width, display.getBounds().height);
        GC gc = new GC(display);
        gc.copyArea(backgroundImage, display.getBounds().x, display.getBounds().y);
        gc.dispose();
 
        //invisible shell and parent for tracker
        final Shell shell = new Shell(display.getActiveShell(), SWT.NO_BACKGROUND | SWT.ON_TOP);
        shell.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_CROSS));
        shell.setBounds(display.getBounds());
 
        final Rectangle result = new Rectangle(0, 0, 0, 0);
 
        shell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
 
                Tracker tracker = new Tracker(shell, SWT.RESIZE);
                tracker.setStippled(true);
                tracker.setRectangles(new Rectangle[] { new Rectangle(e.x, e.y, 0, 0) });
                tracker.open();
 
                Rectangle selection = tracker.getRectangles()[0];
 
                result.width = selection.width;
                result.height = selection.height;
 
                result.x = shell.toDisplay(selection.x, selection.y).x;
                result.y = shell.toDisplay(selection.x, selection.y).y;
 
                shell.dispose();
 
            }
        });
 
        shell.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                shell.dispose(); //any key pressed close shell
            }
        });
 
        shell.addShellListener(new ShellAdapter() {
            @Override
            public void shellDeactivated(ShellEvent e) {
                shell.dispose(); //close shell if another shell is activated
            }
 
        });
 
        shell.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                e.gc.drawImage(backgroundImage, -1, -1); //paint background image on invisible shell
            }
        });
 
        shell.open();
 
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
 
        backgroundImage.dispose();
        shell.dispose();
 
        return result;
 
    }
 
    public static void main(String[] args) {
 
        final Display display = new Display();
        final Shell shell = new Shell(display);
 
        final Rectangle rect = new ClippingSelector().select();
 
        if (rect.height == 0 || rect.width == 0) return;
 
        //we show the selected area in a new shell. Just for demonstration!
        final Image image = new Image(display, rect);
        final GC gc = new GC(display);
        gc.copyArea(image, rect.x, rect.y);
        gc.dispose();
 
        shell.setBounds(rect);
        shell.addPaintListener(new PaintListener() {
 
            @Override
            public void paintControl(PaintEvent e) {
                e.gc.drawImage(image, 0, 0);
            }
        });
 
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
 
    }
}