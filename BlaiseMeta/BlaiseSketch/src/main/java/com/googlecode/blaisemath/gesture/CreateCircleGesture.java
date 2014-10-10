/**
 * CreateCircleGesture.java Created Oct 10, 2014
 */
package com.googlecode.blaisemath.gesture;

import com.googlecode.blaisemath.graphics.core.PrimitiveGraphic;
import com.googlecode.blaisemath.graphics.swing.JGraphicComponent;
import com.googlecode.blaisemath.graphics.swing.JGraphics;
import com.googlecode.blaisemath.graphics.swing.ShapeRenderer;
import com.googlecode.blaisemath.graphics.swing.TransformedCanvasPainter;
import com.googlecode.blaisemath.style.AttributeSet;
import com.googlecode.blaisemath.style.Renderer;
import com.googlecode.blaisemath.style.Styles;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/*
 * #%L
 * BlaiseSketch
 * --
 * Copyright (C) 2009 - 2014 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Gesture for adding a circle to the canvas.
 * 
 * @author Elisha
 */
public class CreateCircleGesture extends SketchGesture<JGraphicComponent> {
    
    protected static final AttributeSet DRAW_STYLE = Styles.DEFAULT_SHAPE_STYLE;
    private static final Renderer<Shape, Graphics2D> REND = ShapeRenderer.getInstance();
    
    public CreateCircleGesture() {
            super("Draw circle", "Drag from center of circle to its boundary.");
    }

    @Override
    public void finish(JGraphicComponent view) {
        if (pressPoint != null && locPoint != null) {
            double rad = pressPoint.distance(locPoint);
            Ellipse2D.Double circ = new Ellipse2D.Double(pressPoint.getX()-rad, pressPoint.getY()-rad, 2*rad, 2*rad);
            PrimitiveGraphic<Shape, Graphics2D> gfc 
                    = JGraphics.shape(circ, DRAW_STYLE.copy());
            gfc.setSelectionEnabled(true);
            gfc.setMouseEnabled(true);
            view.addGraphic(gfc);
        }
    }

    @Override
    public void paint(Graphics g, JGraphicComponent view) {
        new Painter().paint(view, (Graphics2D) g);
    }
    
    private final class Painter extends TransformedCanvasPainter {
        @Override
        public void paintTransformed(JGraphicComponent jgc, Graphics2D gd) {
            if (pressPoint != null && locPoint != null) {
                double rad = pressPoint.distance(locPoint);
                Ellipse2D.Double circ = new Ellipse2D.Double(pressPoint.getX()-rad, pressPoint.getY()-rad, 2*rad, 2*rad);
                REND.render(circ, DRAW_STYLE, gd);
            }
        }
    }
    
}