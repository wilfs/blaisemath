/*
 * PointFormatters.java
 * Created Jan 30, 2011
 */

package org.blaise.util;

import java.awt.geom.Point2D;

/**
 * Utilities for formatting Euclidean points as strings.
 * 
 * @author Elisha Peterson
 */
public final class PointFormatters { 
    
    // utility class - no instantiation
    private PointFormatters() {
    }

    /**
     * Formats a point with n decimal places
     * @param p the point to format
     * @param n number of decimal places
     * @return formatted point, e.g. (2.1,-3.0)
     */
    public static String formatPoint(Point2D p, int n) {
        return String.format("(%."+n+"f, %."+n+"f)", p.getX(), p.getY());
    }

}
