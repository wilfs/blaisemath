/**
 * BlaiseSketchFrameView.java
 * Created Oct 1, 2014
 */
package com.googlecode.blaisemath.sketch;

/*
 * #%L
 * BlaiseGraphics
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


import com.googlecode.blaisemath.gesture.swing.JGraphicGestureLayerUI;
import com.google.common.base.Converter;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.blaisemath.editor.EditorRegistration;
import com.googlecode.blaisemath.firestarter.PropertySheet;
import com.googlecode.blaisemath.gesture.swing.CreateImageGesture;
import com.googlecode.blaisemath.gesture.swing.CreateMarkerGesture;
import com.googlecode.blaisemath.gesture.swing.CreatePathGesture;
import com.googlecode.blaisemath.gesture.swing.CreateTextGesture;
import com.googlecode.blaisemath.gesture.SketchGesture;
import com.googlecode.blaisemath.gesture.swing.CreateCircleGesture;
import com.googlecode.blaisemath.gesture.swing.CreateEllipseGesture;
import com.googlecode.blaisemath.gesture.swing.CreateLineGesture;
import com.googlecode.blaisemath.gesture.swing.CreateRectangleGesture;
import com.googlecode.blaisemath.gesture.swing.GestureOrchestrator;
import com.googlecode.blaisemath.graphics.core.Graphic;
import com.googlecode.blaisemath.graphics.core.GraphicComposite;
import com.googlecode.blaisemath.graphics.core.GraphicUtils;
import com.googlecode.blaisemath.graphics.svg.SVGElementGraphicConverter;
import com.googlecode.blaisemath.graphics.swing.JGraphicComponent;
import com.googlecode.blaisemath.graphics.swing.JGraphicRoot;
import com.googlecode.blaisemath.graphics.swing.PanAndZoomHandler;
import com.googlecode.blaisemath.svg.SVGElement;
import com.googlecode.blaisemath.svg.SVGRoot;
import com.googlecode.blaisemath.util.MPanel;
import com.googlecode.blaisemath.util.RollupPanel;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;

/**
 * Root frame for Blaise drawing.
 * 
 * @author Elisha
 */
public class BlaiseSketchFrameView extends FrameView {
    
    private static final String[][] MENUBAR = new String[][] {
        new String[] { "File", 
            "load", "save", null, 
            "clearCanvas", null, 
            "quit" },
        new String[] { "Edit", 
            "selectAll", "clearSelection", null,
            "copySelectedStyle", "pasteSelectedStyle", null,
            "editSelected", "editSelectedStyle", null, "deleteSelected", null, 
            "groupSelected", "ungroupSelected", null
        },
        new String[] { "Arrange", 
            "moveForward", "moveBackward", null, "moveToFront", "moveToBack"
        },
        new String[] { "Tool", "toolSelect", null, 
            "createMarker", "createLine", "createPath", null,
            "createRect", "createCircle", "createEllipse", null,
            "createText", "createImage"
        },
        new String[] { "Zoom",
            "zoomSelected", "zoomAll", "zoom100"
        }
    };
    
    private static final String[] TOOLBAR = {
        "load", "save", null,
        "toolSelect", null, 
        "createMarker", "createLine", "createPath", 
        "createRect", "createCircle", "createEllipse", 
        "createText", "createImage", null,
        "groupSelected", "ungroupSelected"
    };
    
    private final JFileChooser chooser = new JFileChooser();
    
    private final JGraphicGestureLayerUI gestureUI;
    private final JGraphicComponent activeCanvas;
    
    private final RollupPanel detailsPanel;
    private final MPanel selectionPanel;
    private final JLabel noSelectionLabel = new JLabel("nothing selected");
    private final MPanel overviewPanel;
    
    private final JLabel statusLabel;

    public BlaiseSketchFrameView(BlaiseSketchApp app) {
        super(app);
        
        activeCanvas = new JGraphicComponent();
        activeCanvas.setSelectionEnabled(true);
        new PanAndZoomHandler(activeCanvas);
        activeCanvas.getSelectionModel().addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                boolean selEmpty = isSelectionEmpty();
                boolean one = isOneSelected();
                boolean moreThanOne = isMoreThanOneSelected();
                boolean gpSelected = isGroupSelected();
                firePropertyChange("selectionEmpty", !selEmpty, selEmpty);
                firePropertyChange("groupSelected", !gpSelected, gpSelected);
                firePropertyChange("oneSelected", !one, one);
                firePropertyChange("moreThanOneSelected", !moreThanOne, moreThanOne);
                
                Set<Graphic> sel = (Set<Graphic>) evt.getNewValue();
                setSelectedGraphic(selEmpty ? null 
                        : sel.size() == 1 ? Iterables.get(sel, 0)
                        : sel);
            }
        });
        
        gestureUI = new JGraphicGestureLayerUI();
        gestureUI.addPropertyChangeListener("activeGesture", 
            new PropertyChangeListener(){
                public void propertyChange(PropertyChangeEvent evt) {
                    activeGestureUpdated((SketchGesture) evt.getNewValue());
                }
            });
        JLayer<JGraphicComponent> canvasWithLayer = new JLayer<JGraphicComponent>(activeCanvas, gestureUI);
        
        detailsPanel = new RollupPanel();
        selectionPanel = new MPanel("Selection", noSelectionLabel);
        overviewPanel = new MPanel("Overview", new JLabel("overview of what's on the canvas..."));
        detailsPanel.add(selectionPanel);
        detailsPanel.add(overviewPanel);
        
        JSplitPane mainPanel = new JSplitPane();
        mainPanel.setResizeWeight(.9);
        mainPanel.add(canvasWithLayer, JSplitPane.LEFT);
        mainPanel.add(new JScrollPane(detailsPanel), JSplitPane.RIGHT);
        
        setComponent(mainPanel);

        ActionMap am = app.getContext().getActionMap(this);
        setMenuBar(createMenuBar(am));
        setToolBar(createToolBar(am));
        
        statusLabel = new JLabel("no status to report");
        JPanel statusBar = new JPanel();
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        statusBar.add(new JLabel("STATUS"));
        statusBar.add(Box.createHorizontalStrut(5));
        statusBar.add(new JSeparator(SwingConstants.VERTICAL));
        statusBar.add(Box.createHorizontalStrut(5));
        statusBar.add(statusLabel);
        setStatusBar(statusBar);
        
        EditorRegistration.registerEditors();
    }
    
    //<editor-fold defaultstate="collapsed" desc="INITIALIZATION">
    
    private JMenuBar createMenuBar(ActionMap am) {
        JMenuBar res = new JMenuBar();
        for (String[] arr : MENUBAR) {
            JMenu m = new JMenu(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] == null) {
                    m.addSeparator();
                } else {
                    m.add(am.get(arr[i]));
                }
            }
            res.add(m);
        }
        return res;
    }
    
    private JToolBar createToolBar(ActionMap am) {
        JToolBar res = new JToolBar();
        for (String s : TOOLBAR) {
            if (s == null) {
                res.addSeparator();
            } else {
                res.add(am.get(s));
            }
        }
        return res;
    }
    
    //</editor-fold>
    
    public static ActionMap getActionMap() {
        BlaiseSketchApp app = (BlaiseSketchApp) BlaiseSketchApp.getInstance();
        BlaiseSketchFrameView view = (BlaiseSketchFrameView) app.getMainView();
        return app.getContext().getActionMap(view);
    }
    
    private void setSelectedGraphic(Object gfc) {
        if (gfc == null) {
            selectionPanel.setPrimaryComponent(noSelectionLabel);
        } else {
            if (gfc instanceof Graphic) {
                PropertySheet ps = PropertySheet.forBean(new ProxyGraphicEditor((Graphic) gfc));
                selectionPanel.setPrimaryComponent(ps);
            } else if (gfc instanceof Set) {
                selectionPanel.setPrimaryComponent(new JLabel(((Set)gfc).size()+" graphics selected."));
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="PROPERTIES">
    
    public JGraphicComponent getActiveCanvas() {
        return activeCanvas;
    }
    
    public boolean isSelectionEmpty() {
        return activeCanvas.getSelectionModel().isEmpty();
    }
    
    public boolean isOneSelected() {
        Set<Graphic<Graphics2D>> gfx = activeCanvas.getSelectionModel().getSelection();
        return gfx.size() == 1;
    }
    
    public boolean isMoreThanOneSelected() {
        Set<Graphic<Graphics2D>> gfx = activeCanvas.getSelectionModel().getSelection();
        return gfx.size() > 1;
    }
    
    public boolean isGroupSelected() {
        Set<Graphic<Graphics2D>> gfx = activeCanvas.getSelectionModel().getSelection();
        return gfx.size() == 1 && Iterables.get(gfx, 0) instanceof GraphicComposite;
    }
    
    public void setStatusMessage(String msg, int timeout) {
        statusLabel.setText(msg);
        statusLabel.repaint();
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="I/O ACTIONS">
    
    private SVGRoot toSVG() {
        Converter<Graphic<Graphics2D>, SVGElement> conv = SVGElementGraphicConverter.getInstance().reverse();
        JGraphicRoot gr = activeCanvas.getGraphicRoot();
        SVGRoot r = new SVGRoot();
        for (Graphic<Graphics2D> g : gr.getGraphics()) {
            r.addElement(conv.convert(g));
        }
        return r;
    }
    
    private Iterable<Graphic<Graphics2D>> fromSVG(SVGRoot r) {
        Converter<SVGElement, Graphic<Graphics2D>> conv = SVGElementGraphicConverter.getInstance();
        List<Graphic<Graphics2D>> res = Lists.newArrayList();
        for (SVGElement el : r.getElements()) {
            res.add(conv.convert(el));
        }
        return res;
    }
    
    @Action
    public void load() {        
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(getFrame())) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(chooser.getSelectedFile());
                SVGRoot r = SVGRoot.load(fis);
                activeCanvas.clearGraphics();
                Iterable<Graphic<Graphics2D>> gfcs = fromSVG(r);
                activeCanvas.addGraphics(gfcs);
                SketchGraphics.configureGraphicTree(activeCanvas.getGraphicRoot(), false);
            } catch (IOException x) {
                Logger.getLogger(BlaiseSketchFrameView.class.getName()).log(Level.SEVERE, null, x);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(BlaiseSketchFrameView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    @Action
    public void save() {        
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(getFrame())) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(chooser.getSelectedFile());
                SVGRoot r = toSVG();
                SVGRoot.save(r, out);
            } catch (IOException ex) {
                Logger.getLogger(BlaiseSketchFrameView.class.getName()).log(Level.SEVERE, "Save failed", ex);
                JOptionPane.showMessageDialog(getFrame(), ex, "Save failed", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(BlaiseSketchFrameView.class.getName()).log(Level.SEVERE, "Close failed", ex);
                    }
                }
            }
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Graphic EDIT ACTIONS">
    
    @Action
    public void editGraphic(ActionEvent e) {
        BlaiseSketchActions.editGraphic((Graphic<Graphics2D>) e.getSource(), activeCanvas);
    }
    
    @Action
    public void editGraphicStyle(ActionEvent e) {
        BlaiseSketchActions.editGraphicStyle((Graphic<Graphics2D>) e.getSource(), activeCanvas);
    }
    
    @Action
    public void deleteGraphic(ActionEvent e) {
        BlaiseSketchActions.deleteGraphic((Graphic<Graphics2D>) e.getSource(), activeCanvas);
    }
    
    @Action
    public void copyStyle(ActionEvent e) {
        BlaiseSketchActions.copyStyle((Graphic<Graphics2D>) e.getSource());
    }
    
    @Action
    public void pasteStyle(ActionEvent e) {
        BlaiseSketchActions.pasteStyle(Collections.singleton((Graphic<Graphics2D>) e.getSource()));
    }
    
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EDIT ACTIONS">
    
    @Action
    public void clearCanvas() {
        if (JOptionPane.showConfirmDialog(getFrame(), "Are you sure?") == JOptionPane.OK_OPTION) {
            activeCanvas.clearGraphics();
        }
    }
    
    @Action(enabledProperty="oneSelected")
    public void copySelectedStyle() {
        Graphic<Graphics2D> sel = Iterables.getFirst(activeCanvas.getSelectionModel().getSelection(), null);
        BlaiseSketchActions.copyStyle(sel);
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void pasteSelectedStyle() {
        Set<Graphic<Graphics2D>> sel = activeCanvas.getSelectionModel().getSelection();
        BlaiseSketchActions.pasteStyle(sel);
    }
    
    @Action
    public void selectAll() {
        activeCanvas.getSelectionModel().setSelection(Sets.newHashSet(activeCanvas.getGraphicRoot().getGraphics()));
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void clearSelection() {
        activeCanvas.getSelectionModel().setSelection(Collections.EMPTY_SET);
    }
    
    @Action(enabledProperty="oneSelected")
    public void editSelected() {
        Graphic<Graphics2D> sel = Iterables.getFirst(activeCanvas.getSelectionModel().getSelection(), null);
        BlaiseSketchActions.editGraphic(sel, activeCanvas);
    }
    
    @Action(enabledProperty="oneSelected")
    public void editSelectedStyle() {
        Graphic<Graphics2D> sel = Iterables.getFirst(activeCanvas.getSelectionModel().getSelection(), null);
        BlaiseSketchActions.editGraphicStyle(sel, activeCanvas);
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void deleteSelected() {
        BlaiseSketchActions.deleteSelected(activeCanvas);
    }
    
    @Action(enabledProperty="moreThanOneSelected")
    public void groupSelected() {
        BlaiseSketchActions.groupSelected(activeCanvas);
    }
    
    @Action(enabledProperty="groupSelected")
    public void ungroupSelected() {
        BlaiseSketchActions.ungroupSelected(activeCanvas);
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void moveForward() {
        BlaiseSketchActions.moveForward(activeCanvas.getSelectionModel().getSelection(), activeCanvas);
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void moveBackward() {
        BlaiseSketchActions.moveBackward(activeCanvas.getSelectionModel().getSelection(), activeCanvas);
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void moveToFront() {
        BlaiseSketchActions.moveToFront(activeCanvas.getSelectionModel().getSelection(), activeCanvas);
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void moveToBack() {
        BlaiseSketchActions.moveToBack(activeCanvas.getSelectionModel().getSelection(), activeCanvas);
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TOOL ACTIONS">
    
    @Action
    public void toolSelect() {
        gestureUI.finishActiveGesture();
        setStatusMessage("Selection tool enabled (default)", 2000);
    }
    
    @Action
    public void createMarker() {
        enableGesture(CreateMarkerGesture.class);
    }
    
    @Action
    public void createLine() {
        enableGesture(CreateLineGesture.class);
    }
    
    @Action
    public void createPath() {
        enableGesture(CreatePathGesture.class);
    }
    
    @Action
    public void createRect() {
        enableGesture(CreateRectangleGesture.class);
    }
    
    @Action
    public void createCircle() {
        enableGesture(CreateCircleGesture.class);
    }
    
    @Action
    public void createEllipse() {
        enableGesture(CreateEllipseGesture.class);
    }
    
    @Action
    public void createText() {
        enableGesture(CreateTextGesture.class);
    }
    
    @Action
    public void createImage() {
        enableGesture(CreateImageGesture.class);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ZOOM ACTIONS">
    
    @Action
    public void zoomAll() {
        Rectangle2D bounds = activeCanvas.getGraphicRoot().boundingBox();
        PanAndZoomHandler.zoomCoordBoxAnimated(activeCanvas, new Point2D.Double(
                bounds.getMinX(), bounds.getMinY()),
                new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()));
    }
    
    @Action(disabledProperty="selectionEmpty")
    public void zoomSelected() {
        Rectangle2D bounds = GraphicUtils.boundingBox(activeCanvas.getSelectionModel().getSelection());
        PanAndZoomHandler.zoomCoordBoxAnimated(activeCanvas, new Point2D.Double(
                bounds.getMinX(), bounds.getMinY()),
                new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()));
    }
    
    @Action
    public void zoom100() {
        activeCanvas.setTransform(null);
    }
    
    //</editor-fold>
    
    /** Activates the gesture layer, and allows the user to complete the gesture. */
    private <G extends SketchGesture> void enableGesture(Class<G> gType) {
        checkNotNull(gType);
        try {
            Constructor<G> con = gType.getConstructor(GestureOrchestrator.class);
            G gesture = con.newInstance(gestureUI.getGestureOrchestrator());
            gestureUI.setActiveGesture(gesture);
        } catch (InstantiationException ex) {
            throw new IllegalArgumentException(ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException(ex);
        } catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        } catch (SecurityException ex) {
            throw new IllegalArgumentException(ex);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex);
        } catch (InvocationTargetException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    /** Activates the gesture layer, and allows the user to complete the gesture. */
    private void activeGestureUpdated(SketchGesture gesture) {
        setStatusMessage(gesture.getName()+" | "+gesture.getDesription(), 5000);
    }

    
}