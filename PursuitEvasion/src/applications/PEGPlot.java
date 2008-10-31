/*
 * PEGPlot.java
 * Created on September 8, 2007, 8:59 AM
 */

// TODO synchronize timers in the two different plot windows.

package applications;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import scio.coordinate.R2;
import sequor.editor.ComboBoxEditor;
import analysis.Statistics;
import analysis.SimulationLog;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.OutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import metrics.Valuation;
import sequor.SettingsFactory;
import simulation.Simulation;
import simulation.Team;
import utility.XmlHandler;

/**
 *
 * @author  ae3263
 */
public class PEGPlot extends javax.swing.JFrame {
    
    final JFileChooser fc = new JFileChooser();
    
    /** Creates new form PEGPlot */
    @SuppressWarnings("unchecked")
    public PEGPlot() {
        initComponents();
        simulationPlot.getVisometry().setBounds(new R2(-70,-70),new R2(70,70));
        metricsPlot.getVisometry().setBounds(new R2(-10,-100),new R2(200,100));
        metricsPlot.synchronizeTimerWith(simulationPlot);
        mainVisuals1.setSim(simulation1);
        metricVisuals1.setSim(simulation1);
        simulationPlot.add(mainVisuals1);
        metricsPlot.add(metricVisuals1);
        metricsPlot.add(metricVisuals1.getLegend(),5,6);
        simulation1.run();
        simulationComboBox.setModel(new ComboBoxEditor(simulation1.getGameTypeModel()));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuSimModeGroup = new javax.swing.ButtonGroup();
        numBatchRunsModel = new sequor.model.IntegerRangeModel(100,0,100000);
        simulation1 = new simulation.Simulation();
        mainVisuals1 = new analysis.MainVisuals();
        metricVisuals1 = new analysis.MetricVisuals();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        simulationSettingsPanel1 = new applications.SimulationSettingsPanel();
        infoPane = new javax.swing.JTabbedPane();
        metricsPlot = new specto.euclidean2.Plot2D();
        jScrollPane5 = new javax.swing.JScrollPane();
        logWindow = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataWindow = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        codeWindow = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        simulationPlot = new specto.euclidean2.Plot2D();
        statusBar = new javax.swing.JPanel();
        statusText = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        simulationComboBox = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        randomizeButton = new javax.swing.JButton();
        batchButton = new javax.swing.JButton();
        numBatchRunsSpinner = SettingsFactory.getSpinner(numBatchRunsModel);
        cooperationButton = new javax.swing.JButton();
        startingPositionsButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        addDotsButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        quitMenuItem = new javax.swing.JMenuItem();
        simulationMenu = new javax.swing.JMenu();
        randomizeMenuItem = new javax.swing.JMenuItem();
        animateMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        ModeMenu = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        appearanceMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        simulation1.setGameType(2);
        simulation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulation1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pursuit-Evasion Games");

        jSplitPane2.setDividerLocation(450);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        simulationSettingsPanel1.setSim(simulation1);
        jSplitPane1.setTopComponent(simulationSettingsPanel1);

        infoPane.setToolTipText("See information regarding the simulations.");
        infoPane.setMaximumSize(new java.awt.Dimension(450, 600));

        metricsPlot.setAnimatorVisible(false);
        metricsPlot.setMarkerBoxVisible(false);

        javax.swing.GroupLayout metricsPlotLayout = new javax.swing.GroupLayout(metricsPlot);
        metricsPlot.setLayout(metricsPlotLayout);
        metricsPlotLayout.setHorizontalGroup(
            metricsPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 442, Short.MAX_VALUE)
        );
        metricsPlotLayout.setVerticalGroup(
            metricsPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );

        infoPane.addTab("Metrics", metricsPlot);

        logWindow.setColumns(20);
        logWindow.setEditable(false);
        logWindow.setRows(5);
        logWindow.setToolTipText("See information about simulations which have been run.");
        jScrollPane5.setViewportView(logWindow);

        infoPane.addTab("Log", jScrollPane5);

        dataWindow.setColumns(20);
        dataWindow.setRows(5);
        dataWindow.setToolTipText("See a table of data obtained from the last simulation.");
        jScrollPane1.setViewportView(dataWindow);

        infoPane.addTab("Data", jScrollPane1);

        codeWindow.setColumns(20);
        codeWindow.setRows(5);
        jScrollPane2.setViewportView(codeWindow);

        infoPane.addTab("Code", jScrollPane2);

        jScrollPane3.setToolTipText("Communications network of the teams.");
        jScrollPane3.setEnabled(false);
        jScrollPane3.setFocusable(false);
        infoPane.addTab("Network View", jScrollPane3);

        jSplitPane1.setRightComponent(infoPane);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel1);

        simulationPlot.setAxisStyle(1);

        javax.swing.GroupLayout simulationPlotLayout = new javax.swing.GroupLayout(simulationPlot);
        simulationPlot.setLayout(simulationPlotLayout);
        simulationPlotLayout.setHorizontalGroup(
            simulationPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        simulationPlotLayout.setVerticalGroup(
            simulationPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(simulationPlot);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.CENTER);

        statusBar.setBackground(new java.awt.Color(255, 255, 255));
        statusBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        statusText.setText("Awaiting user instructions.");

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusText, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusText)
        );

        getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);

        jToolBar1.setRollover(true);

        jLabel1.setText("Game Preset:  ");
        jToolBar1.add(jLabel1);

        simulationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        simulationComboBox.setMaximumSize(new java.awt.Dimension(150, 20));
        jToolBar1.add(simulationComboBox);
        jToolBar1.add(jSeparator1);

        randomizeButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        randomizeButton.setText("RANDOMIZE");
        randomizeButton.setToolTipText("Click here to reset the simulation and re-randomize starting positions.");
        randomizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(randomizeButton);

        batchButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        batchButton.setText("BATCH");
        batchButton.setFocusable(false);
        batchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        batchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        batchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(batchButton);

        numBatchRunsSpinner.setMaximumSize(new java.awt.Dimension(70, 32767));
        jToolBar1.add(numBatchRunsSpinner);

        cooperationButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        cooperationButton.setText("COOPERATION");
        cooperationButton.setFocusable(false);
        cooperationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cooperationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cooperationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cooperationButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(cooperationButton);

        startingPositionsButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        startingPositionsButton.setText("GET START POSITIONS");
        startingPositionsButton.setFocusable(false);
        startingPositionsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startingPositionsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        startingPositionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startingPositionsButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(startingPositionsButton);
        jToolBar1.add(jSeparator3);

        addDotsButton.setText("Add Dots");
        addDotsButton.setEnabled(false);
        addDotsButton.setFocusable(false);
        addDotsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addDotsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addDotsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDotsButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addDotsButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        fileMenu.setText("File");

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setText("New Simulation");
        newMenuItem.setEnabled(false);
        fileMenu.add(newMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Open Simulation");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save Simulation");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        quitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        quitMenuItem.setText("Quit");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(quitMenuItem);

        jMenuBar1.add(fileMenu);

        simulationMenu.setText("Simulation");

        randomizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        randomizeMenuItem.setText("Randomize Starting Locations");
        randomizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeButtonActionPerformed(evt);
            }
        });
        simulationMenu.add(randomizeMenuItem);

        animateMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        animateMenuItem.setText("Animate Simulation");
        animateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animateMenuItemActionPerformed(evt);
            }
        });
        simulationMenu.add(animateMenuItem);

        jMenuBar1.add(simulationMenu);

        settingsMenu.setText("Settings");
        settingsMenu.setEnabled(false);

        ModeMenu.setText("Simulation Mode");
        ModeMenu.setEnabled(false);

        jRadioButtonMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSimModeGroup.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("One Simulation (Dynamic)");
        ModeMenu.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSimModeGroup.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("Two Simulations (Comparison)");
        ModeMenu.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSimModeGroup.add(jRadioButtonMenuItem3);
        jRadioButtonMenuItem3.setText("Multiple Simulations (Statistical)");
        ModeMenu.add(jRadioButtonMenuItem3);

        settingsMenu.add(ModeMenu);

        jMenuBar1.add(settingsMenu);

        appearanceMenu.setText("Appearance");
        appearanceMenu.setEnabled(false);

        jMenuItem1.setText("Plot Window");
        jMenuItem1.setEnabled(false);
        appearanceMenu.add(jMenuItem1);

        jMenuItem2.setText("Points");
        jMenuItem2.setEnabled(false);
        appearanceMenu.add(jMenuItem2);

        jMenuItem7.setText("Paths");
        jMenuItem7.setEnabled(false);
        appearanceMenu.add(jMenuItem7);

        jMenuBar1.add(appearanceMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void batchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchButtonActionPerformed
        statusText.setText("Batching "+numBatchRunsModel.getValue()+" runs... this may take a while!");
        simulation1.runSeveral(numBatchRunsModel.getValue());
}//GEN-LAST:event_batchButtonActionPerformed

private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitMenuItemActionPerformed
    statusText.setText("Goodbye!");
    System.exit(0);
}//GEN-LAST:event_quitMenuItemActionPerformed

private void randomizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomizeButtonActionPerformed
    statusText.setText("Randomizing starting locations...");
    logWindow.append("\nNEW SIMULATION\n");
    simulation1.initStartingLocations();
    simulation1.run();
    success();
}//GEN-LAST:event_randomizeButtonActionPerformed

private void success(){
    statusText.setText("Success! Awaiting further instructions.");
}

    @SuppressWarnings("unchecked")
private void simulation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulation1ActionPerformed
    //System.out.println("pegplot action performed: "+evt.getActionCommand());
    if(evt.getActionCommand().equals("redraw")){
        simulationPlot.repaint();
        metricsPlot.repaint();
        simulationSettingsPanel1.repaint();
    }
    // simulation has changed in some fundamental way
    else if(evt.getActionCommand().equals("reset")){
        statusText.setText("Resetting simulation...");
        simulationPlot.rebuildOptionsMenu();
        metricsPlot.rebuildOptionsMenu();
        simulationSettingsPanel1.setTree(simulation1.ss);
    }
    else if(evt.getActionCommand().equals("log")){
        if(evt.getSource() instanceof SimulationLog){
            ((SimulationLog)evt.getSource()).output(logWindow);
        }else if(evt.getSource() instanceof Statistics){
            ((Statistics)evt.getSource()).output(logWindow);
            // copy stats to dataWindow and then to clipboard
            statusText.setText("Copying stats to clipboard...");
            ((Statistics)evt.getSource()).outputData(dataWindow); 
            StringSelection data = new StringSelection(dataWindow.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
            // display happy message            
            statusText.setText("Success! The batch data has been copied to the clipboard.");
        }
    }
    else{
        logWindow.append(evt.getActionCommand()+"\n");
        statusText.setText(evt.getActionCommand());
    }
}//GEN-LAST:event_simulation1ActionPerformed

    private void addDotsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDotsButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Sorry, dots are currently unavailable.","Functionality unavailable",JOptionPane.ERROR_MESSAGE);
//PlaneFunction2D pf=new Statistics().getInitialPositionTestPlot(simulation1);
        //pf.style.setValue(PlaneFunction2D.DOTS);
        //plot2D1.add(pf);
}//GEN-LAST:event_addDotsButtonActionPerformed

    private void cooperationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cooperationButtonActionPerformed
        statusText.setText("Printing cooperation metrics...");
        for (Team t : simulation1.getTeams()) {
            if (t.victory != null) {
                logWindow.append(t.victory.getCooperationMetric(simulation1).toString());
            }
            for (Valuation v : t.metrics) {
                logWindow.append(v.getCooperationMetric(simulation1).toString());
            }
        }    
        statusText.setText("Success! Check the log window for cooperation metrics.");
}//GEN-LAST:event_cooperationButtonActionPerformed

private void startingPositionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startingPositionsButtonActionPerformed
    try {codeWindow.getDocument().remove(0, codeWindow.getDocument().getLength()-1);}catch(Exception e){}
    simulation1.log.printStartingLocations(logWindow, codeWindow);
    StringSelection data = new StringSelection(codeWindow.getText());
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
    // display result message
    statusText.setText("Success! The starting positions are in the log."
            + " The Java code to re-initialize teams to these positions is in the clipboard and the code window.");
}//GEN-LAST:event_startingPositionsButtonActionPerformed

private void animateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animateMenuItemActionPerformed
    simulationPlot.getTimer().start();
}//GEN-LAST:event_animateMenuItemActionPerformed

private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
    int returnVal = fc.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                statusText.setText("Opening: " + file.getName() + ".");
                simulation1 = XmlHandler.unmarshal(file);
                mainVisuals1.setSim(simulation1);
                metricVisuals1.setSim(simulation1);
                simulation1.run();
                simulationComboBox.setModel(new ComboBoxEditor(simulation1.getGameTypeModel()));
                simulationPlot.rebuildOptionsMenu();
                metricsPlot.rebuildOptionsMenu();
                simulationSettingsPanel1.setTree(simulation1.ss);
            } catch (JAXBException ex) {
                Logger.getLogger(PEGPlot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PEGPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
    } else {
        statusText.setText("Open command cancelled by user.");
    }
}//GEN-LAST:event_openMenuItemActionPerformed

private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
    int returnVal = fc.showSaveDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                statusText.setText("Saving: " + file.getName() + ".");
                XmlHandler.marshal(simulation1, file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PEGPlot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(PEGPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
    } else {
        statusText.setText("Save command cancelled by user.");
    }
}//GEN-LAST:event_saveMenuItemActionPerformed
    
    /**
     * @param args the command lineSegment arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PEGPlot().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ModeMenu;
    private javax.swing.JButton addDotsButton;
    private javax.swing.JMenuItem animateMenuItem;
    private javax.swing.JMenu appearanceMenu;
    private javax.swing.JButton batchButton;
    private javax.swing.JTextArea codeWindow;
    private javax.swing.JButton cooperationButton;
    private javax.swing.JTextArea dataWindow;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JTabbedPane infoPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea logWindow;
    private analysis.MainVisuals mainVisuals1;
    private javax.swing.ButtonGroup menuSimModeGroup;
    private analysis.MetricVisuals metricVisuals1;
    private specto.euclidean2.Plot2D metricsPlot;
    private javax.swing.JMenuItem newMenuItem;
    private sequor.model.IntegerRangeModel numBatchRunsModel;
    private javax.swing.JSpinner numBatchRunsSpinner;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JButton randomizeButton;
    private javax.swing.JMenuItem randomizeMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenu settingsMenu;
    private simulation.Simulation simulation1;
    private javax.swing.JComboBox simulationComboBox;
    private javax.swing.JMenu simulationMenu;
    private specto.euclidean2.Plot2D simulationPlot;
    private applications.SimulationSettingsPanel simulationSettingsPanel1;
    private javax.swing.JButton startingPositionsButton;
    private javax.swing.JPanel statusBar;
    private javax.swing.JLabel statusText;
    // End of variables declaration//GEN-END:variables
}
