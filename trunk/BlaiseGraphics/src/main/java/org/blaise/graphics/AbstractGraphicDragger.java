/**
 * AbstractGraphicDragger.java
 * Created Jul 31, 2012
 */
package org.blaise.graphics;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * <p>
 *  Provides hooks for drag mouse gestures. Instead of working with all six mouse methods, subclasses can work with
 *  two or three (dragInitiated, dragInProgress, and optionally dragCompleted).
 * </p>
 * @author elisha
 */
public abstract class AbstractGraphicDragger extends MouseAdapter implements MouseMotionListener {
    
    /** Stores the starting point of the drag */
    protected Point start;

    /**
     * Called when the mouse is pressed, starting the drag
     * @param e the source event
     * @param start the initial drag point
     */
    public abstract void mouseDragInitiated(GraphicMouseEvent e, Point start);

    /**
     * Called as mouse drag is in progress
     * @param e the source event (stores the point and graphic for the event)
     * @param start the initial drag point
     */
    public abstract void mouseDragInProgress(GraphicMouseEvent e, Point start);

    /**
     * Called when the mouse is released, finishing the drag. Does nothing by default.
     * @param e the source event (stores the point and graphic for the event)
     * @param start the initial drag point
     */
    public void mouseDragCompleted(GraphicMouseEvent e, Point start) {
    }

    @Override
    public final void mousePressed(MouseEvent e) {
        start = e.getPoint();
        mouseDragInitiated((GraphicMouseEvent) e, start);
        e.consume();
    }

    public final void mouseDragged(MouseEvent e) {
        if (start == null) {
            mousePressed(e);
        }
        mouseDragInProgress((GraphicMouseEvent) e, start);
        e.consume();
    }

    @Override
    public final void mouseReleased(MouseEvent e) {
        if (start != null) {
            mouseDragCompleted((GraphicMouseEvent) e, start);
            start = null;
        }
    }

    @Override
    public final void mouseExited(MouseEvent e) {
        if (start != null) {
            mouseReleased(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
    }
    
}