/**
 * VPoint.java
 * Created on Jul 30, 2009
 */

package org.bm.blaise.specto.plottable;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.bm.blaise.specto.primitive.PointStyle;
import org.bm.blaise.specto.primitive.StringStyle;
import org.bm.blaise.specto.visometry.VisometryGraphics;

/**
 * <p>
 *   <code>VPoint</code> is a point in the visometry.
 * </p>
 *
 * @author Elisha Peterson
 */
public class VPoint<C> extends VInvisiblePoint<C> {

    /** Style for the point. */
    PointStyle style = new PointStyle();
    /** A custom label for the point */
    String label = null;
    /** Whether the label si shown. */
    boolean labelVisible = true;
    /** Whether coordinates are shown */
    boolean coordVisible = true;
    /** Style for the coordinate label. */
    StringStyle labelStyle = new StringStyle();

    //
    // CONSTRUCTORS
    //

    /** Constructs the point w/ specified value.
     * @param value coordinates of the point
     */
    public VPoint(C value) {
        super(value);
    }

    public PointStyle getStyle() {
        return style;
    }

    public void setStyle(PointStyle style) {
        this.style = style;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String customLabel) {
        this.label = customLabel;
    }

    public StringStyle getLabelStyle() {
        return labelStyle;
    }

    public void setLabelStyle(StringStyle labelStyle) {
        this.labelStyle = labelStyle;
    }

    public boolean isLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(boolean labelVisible) {
        this.labelVisible = labelVisible;
    }

    public boolean isCoordVisible() {
        return coordVisible;
    }

    public void setCoordVisible(boolean coordVisible) {
        this.coordVisible = coordVisible;
    }

    @Override
    public String toString() {
        return "VPoint ["+value.toString()+"]";
    }
   
    //
    // PAINTING
    //

    final NumberFormat formatter = new DecimalFormat("#0.000");

    /** Hook for subclasses to provide custom formatting of displayed coordinates of point. */
    public String getValueString() {
        if (value instanceof Point2D) {
            Point2D p2d = ((Point2D)value);
            return "(" + formatter.format(p2d.getX()) + ", " + formatter.format(p2d.getY()) + ")";            
        }
        return value.toString();
    }

    @Override
    public void paintComponent(VisometryGraphics<C> vg) {
        vg.setPointStyle(style);
        vg.drawPoint(value);
        if (labelVisible || coordVisible) {
            if (labelStyle != null)
                vg.setStringStyle(labelStyle);
            vg.drawString(
                    labelVisible && coordVisible ? label + " " + getValueString()
                    : labelVisible ? label
                    : coordVisible ? getValueString()
                    : null
                    , value, 5, -5);
        }
    }
}
