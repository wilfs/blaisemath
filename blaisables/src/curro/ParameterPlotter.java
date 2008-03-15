/*
 * ParameterPlotter.java
 *
 * Created on November 13, 2007, 9:31 AM
 */

package curro;

import java.awt.Color;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sequor.Settings;
import sequor.model.FunctionTreeModel;
import sequor.model.ParameterListModel;
import sequor.SettingsProperty;
import specto.plottable.Function2D;

/**
 *
 * @author  ae3263
 */
public class ParameterPlotter extends javax.swing.JFrame {
    FunctionTreeModel ftm,ftm2;
    ParameterListModel plm;
        
    /** Creates new form ParameterPlotter */
    public ParameterPlotter() {
        plm=new ParameterListModel();
        plm.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if((e.getSource() instanceof ParameterListModel)&&(((ParameterListModel)plm).isAdded())){
                    ((ParameterListModel)plm).updatePanel(settingsPanel2);
                }
            }
        });    
        initComponents();  
        ftm=new FunctionTreeModel("a*sin(b*(x-c))+d","x");
        ftm2=new FunctionTreeModel(ftm.getRoot().derivativeTree("x").fullSimplified());
        ftm.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){ 
                try{
                    ftm2.setValue(ftm.getRoot().derivativeTree("x").fullSimplified().toString());
                }catch(NullPointerException exc){}
                settingsBar1.validate();
            }
        });
        plot2D1.add(new Function2D(ftm2,Color.lightGray));
        plot2D1.add(new Function2D(ftm));
        plm.addChangeListener(ftm);  
        plm.addChangeListener(ftm2);
        plm.setParameterValue("a",1.0);
        plm.setParameterValue("b",2.0);
        plm.setParameterValue("c",Math.PI/2);
        plm.setParameterValue("d",-1.0);
        settings1.addProperty("f(x)=",ftm,Settings.EDIT_FUNCTION);
        settings1.addProperty("f'(x)=",ftm2,Settings.EDIT_FUNCTION);
        settingsBar1.updateBar();
        animationControlPanel1.setTimer(plot2D1.getTimer());
        plot2D1.repaint();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        settings1 = new sequor.Settings();
        settings2 = new sequor.Settings();
        plot2D1 = new specto.plotpanel.Plot2D();
        animationControlPanel1 = new sequor.component.AnimationControlPanel();
        settingsPanel2 = new sequor.component.SettingsPanel(settings2);
        settingsBar1 = new sequor.component.SettingsBar(settings1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Function Plotter with Parameters");
        setMinimumSize(new java.awt.Dimension(600, 300));

        plot2D1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        plot2D1.setPreferredSize(new java.awt.Dimension(400, 250));
        getContentPane().add(plot2D1, java.awt.BorderLayout.CENTER);
        getContentPane().add(animationControlPanel1, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(settingsPanel2, java.awt.BorderLayout.LINE_END);

        settingsBar1.setFloatable(false);
        settingsBar1.setRollover(true);
        getContentPane().add(settingsBar1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParameterPlotter().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private sequor.component.AnimationControlPanel animationControlPanel1;
    private specto.plotpanel.Plot2D plot2D1;
    private sequor.Settings settings1;
    private sequor.Settings settings2;
    private sequor.component.SettingsBar settingsBar1;
    private sequor.component.SettingsPanel settingsPanel2;
    // End of variables declaration//GEN-END:variables
    
}
