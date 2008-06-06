/*
 * SpaceFillingApplet.java
 *
 * Created on April 2, 2008, 9:03 AM
 */

package curro;

import scio.coordinate.R2;
import sequor.control.NumberSlider;
import sequor.control.SliderBox;
import specto.euclidean2.FractalShape2D;

/**
 *
 * @author  ae3263
 */
public class SpaceFillingApplet extends javax.swing.JApplet {
    
    /** Initializes the applet SpaceFillingApplet */
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    plot2D1.getVisometry().setDesiredBounds(-.5,-.2,1.5,1.5);
                    FractalShape2D.Edges fe1=new FractalShape2D.Edges(new R2(-3,3),new R2(-2,3));
                    fe1.add(-2-1/3.,3);
                    fe1.add(-2-1/3.,3+1/3.);
                    fe1.add(-2-2/3.,3+1/3.);
                    fe1.add(-2-2/3.,3);
                    plot2D1.add(fe1); 
                    FractalShape2D.SpaceFilling sfc1=new FractalShape2D.SpaceFilling();
                    plot2D1.add(sfc1);
                    SliderBox nab5=new SliderBox();
                    nab5.add(new NumberSlider(210,10,fe1.getIterModel()));
                    nab5.add(new NumberSlider(210,10,sfc1.getIterModel()));
                    plot2D1.add(nab5,3,1);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plot2D1 = new specto.euclidean2.Plot2D();

        javax.swing.GroupLayout plot2D1Layout = new javax.swing.GroupLayout(plot2D1);
        plot2D1.setLayout(plot2D1Layout);
        plot2D1Layout.setHorizontalGroup(
            plot2D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        plot2D1Layout.setVerticalGroup(
            plot2D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plot2D1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plot2D1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private specto.euclidean2.Plot2D plot2D1;
    // End of variables declaration//GEN-END:variables
    
}
