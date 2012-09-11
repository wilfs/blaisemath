/**
 * DelegatingPointGraphic.java
 * Created Aug 21, 2012
 */
package org.blaise.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.blaise.style.ObjectStyler;
import org.blaise.style.PointStyle;
import org.blaise.style.StringStyle;
import org.blaise.util.Delegator;

/**
 * Uses an {@link ObjectStyler} and a source object to draw a labeled point on a canvas.
 *
 * @author Elisha
 */
public class DelegatingPointGraphic<Src> extends AbstractPointGraphic {

    /** Source object */
    protected Src src;
    /** Manages delegators */
    protected ObjectStyler<Src, PointStyle> styler = new ObjectStyler<Src, PointStyle>();

    public DelegatingPointGraphic() {
        this(null, new Point2D.Double());
    }

    public DelegatingPointGraphic(Src src, Point2D pt) {
        super(pt);
        setSourceObject(src);
    }

    public Src getSourceObject() {
        return src;
    }

    public void setSourceObject(Src src) {
        this.src = src;
        setDefaultTooltip(styler.getTipDelegate().of(src));
        fireGraphicChanged();
    }

    public ObjectStyler<Src, PointStyle> getStyler() {
        return styler;
    }

    public void setStyler(ObjectStyler<Src, PointStyle> styler) {
        this.styler = styler;
        fireGraphicChanged();
    }

    @Override
    public String getTooltip(Point p) {
        if (tipEnabled) {
            String txt = styler.getTipDelegate().of(src);
            return txt == null ? tipText : txt;
        } else {
            return null;
        }
    }

    @Override
    public PointStyle drawStyle() {
        PointStyle style = null;
        if (styler != null && styler.getStyleDelegate() != null) {
            style = styler.getStyleDelegate().of(src);
        }
        if (style == null) {
            style = parent.getStyleProvider().getPointStyle(this);
        }
        return style;
    }

    public void draw(Graphics2D canvas) {
        PointStyle ps = drawStyle();
        ps.draw(point, canvas, visibility);

        if (styler.getLabelDelegate() != null) {
            String label = styler.getLabelDelegate().of(src);
            if (label != null && label.length() > 0) {
                Delegator<Src, StringStyle> lStyler = styler.getLabelStyleDelegate();
                if (lStyler != null) {
                    lStyler.of(src).draw(point, label, canvas, visibility);
                }
            }
        }
    }


}