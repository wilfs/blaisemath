/*
 * VisualButton.java
 * Created on Mar 15, 2008
 */

package sequor.control;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import javax.swing.event.EventListenerList;
import sequor.VisualControl;

/**
 * VisualButton is a generic class for a button which can be displayed on a GUI.
 * Buttons have active/inactive states, fire ActionCommand's for when the buttons
 * are clicked, and fire ChangeEvent's when their status (e.g. pressed vs. released)
 * changes, more for visual effect.
 * @author Elisha Peterson
 */
public class VisualButton extends VisualControl {
    
    /** Whether the button may be selected or not. */
    boolean active=true;
    /** The action generated by pressing the button. */
    String actionCommand;

    
    // CONSTRUCTORS
    
    /** Generates with given command, listener, and background shape. */
    public VisualButton(String actionCommand,ActionListener al,BoundedShape shape){
        this(shape);
        this.actionCommand=actionCommand;
        addActionListener(al);
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }
    public VisualButton(BoundedShape shape){
        this();
        buttonShape=shape;
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }
    public VisualButton(){this(0,0,15);}
    public VisualButton(int x,int y,int sz){this(x,y,sz,sz,null);}
    public VisualButton(int x,int y,int sz,String label){this(x,y,sz,sz,label);}
    public VisualButton(int x,int y,int wid,int ht,String label){      
        super(x,y,wid,ht);
        setName(label);
        pressed=false;
        actionCommand="button";
        setBackgroundShape(BoundedShape.ELLIPSE);
        buttonShape=BoundedShape.ELLIPSE;
    }
    
    // BEAN PATTERNS
    
    public void setSize(int size){setSize(size,size);}
    public void setPressed(boolean pressed) {this.pressed=pressed;}
    public boolean isPressed(){return pressed;}
    public void setActive(boolean active) {this.active=active;}
    public boolean isActive(){return active;}
    
    void setForegroundShape(BoundedShape buttonShape) {this.buttonShape=buttonShape;}
    @Override
    public void setBackgroundShape(BoundedShape shape) {        
        super.setBackgroundShape(shape);
        if(shape==BoundedShape.ELLIPSE){
            if(getWidth()>20 && getHeight()>20){
                padding=DEFAULT_PADDING+((getWidth()>getHeight())?getHeight()/7:getWidth()/7);
            }else{
                padding=DEFAULT_PADDING+1;
            }
        }
    }
    
    
    // PAINT METHODS
    
    public static final int DEFAULT_PADDING=3;

    boolean pressed;
    int padding=3;
    BoundedShape buttonShape;

    public static final int ROUNDED_CORNERS=8;
    
    @Override
    public void paintComponent(Graphics2D g){
        super.paintComponent(g,0.7f);
        if(pressed){
            g.setColor(getForeground());
            g.fill(backgroundShape.getBoundedShape(getBounds(),0));
            g.setColor(getBackground());
            g.fill(buttonShape.getBoundedShape(getBounds(),padding));
        }else{
            g.setColor(getForeground());
            g.fill(buttonShape.getBoundedShape(getBounds(),padding));
        }
    }
    
    
    // MOUSE EVENTS

    @Override
    public boolean clicked(MouseEvent e) {
        return (active && super.clicked(e));
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        if(clicked(e)){
            pressed=true;
            fireStateChanged();
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        pressed=false;
        fireStateChanged();
        fireActionPerformed(actionCommand);
    }
    
    
    // ACTION EVENT HANDLING
     
    protected ActionEvent actionEvent=null;
    protected EventListenerList actionListenerList=new EventListenerList();    
    public void addActionListener(ActionListener l){actionListenerList.add(ActionListener.class,l);}
    public void removeActionListener(ActionListener l){actionListenerList.remove(ActionListener.class,l);}
    protected void fireActionPerformed(String s){
        Object[] listeners=actionListenerList.getListenerList();
        for(int i=listeners.length-2; i>=0; i-=2){
            if(listeners[i]==ActionListener.class){
                if(actionEvent==null){actionEvent=new ActionEvent(this,0,s);}
                ((ActionListener)listeners[i+1]).actionPerformed(actionEvent);
            }
        }
    }
}
