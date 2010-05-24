/*
 * GraphExplorerActions.java
 * Created May 14, 2010
 */

package graphexplorer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.bm.blaise.scio.graph.Graph2;
import org.bm.blaise.scio.graph.SimpleGraph;
import org.bm.blaise.scio.graph.GraphCreation;
import org.bm.blaise.scio.graph.io.SimpleGraphIO;
import org.bm.blaise.scio.graph.layout.EnergyLayout;
import org.bm.blaise.scio.graph.layout.StaticGraphLayout;
import org.bm.blaise.scio.graph.metrics.GraphMetrics;
import stormtimer.BetterTimer;

/**
 * Describes actions supporting the graph explorer app.
 * @author Elisha Peterson
 */
public class GraphExplorerActions {

    /** What this class works with */
    GraphExplorerMain main;
    public GraphExplorerActions() {
        this(null);
    }
    public GraphExplorerActions(GraphExplorerMain main) {
        this.main = main;
    }
    public GraphExplorerMain getMain() {
        return main;
    }
    public void setMain(GraphExplorerMain main) {
        this.main = main;
    }

    //
    // FILE HANDLING & HELP
    //

    JFileChooser fc;
    File openFile;

    void initFileChooser() {
        if (fc == null)
            fc = new JFileChooser();
    }

    public Action LOAD_ACTION = new AbstractAction("Load graph") {
        {
            putValue(SHORT_DESCRIPTION, "Load a graph from a file");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        }
        public void actionPerformed(ActionEvent e) {
            initFileChooser();
            if (openFile != null) {
                fc.setCurrentDirectory(openFile);
                fc.setSelectedFile(openFile);
            }
            if (fc.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
                openFile = fc.getSelectedFile();
                SimpleGraph sg = SimpleGraphIO.importSimpleGraph(openFile);
                if (sg != null)
                    main.loadGraph(sg, openFile.getName());
                else
                    main.output("Attempt to load file " + openFile + " failed!");
            }
        }
    };

    public Action SAVE_ACTION = new AbstractAction("Save graph") {
        {
            putValue(SHORT_DESCRIPTION, "Save current graph to a file");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        }
        public void actionPerformed(ActionEvent e) {
            main.output("Save feature not yet implemented.");
        }
    };

    public Action CLOSE_ACTION = new AbstractAction("Close graph") {
        {
            putValue(SHORT_DESCRIPTION, "Closes current graph window");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_W);
        }
        public void actionPerformed(ActionEvent e) {
            main.closeActiveGraph();
        }
    };

    public Action QUIT_ACTION = new AbstractAction("Exit") {
        {
            putValue(SHORT_DESCRIPTION, "Exit Graph Explorer");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        }
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    public Action ABOUT_ACTION = new AbstractAction("About") {
        {
            putValue(SHORT_DESCRIPTION, "About GraphExplorer");
        }
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(main, "GraphExplorer 0.0001\nCreated by Elisha Peterson");
        }
    };

    public Action HELP_ACTION = new AbstractAction("Help") {
        {
            putValue(SHORT_DESCRIPTION, "Load help file");
        }
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(main, "Sorry, the help feature is not yet available!");
        }
    };

    //
    // GRAPH GENERATION
    //

    public Action GENERATE_EMPTY = new AbstractAction("Empty graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates an empty graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for empty graph (up to 1 million).", 1, 1000000);
            main.loadGraph(GraphCreation.buildEmptyGraph(num), "Empty graph");
        }
    };

    public Action GENERATE_COMPLETE = new AbstractAction("Complete graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates a complete graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for complete graph (up to 1000).", 1, 1000);
            main.loadGraph(GraphCreation.buildCompleteGraph(num, false), "Complete graph");
        }
    };

    public Action GENERATE_CIRCLE = new AbstractAction("Circle graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates a circle graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for circle graph (up to 1 million).", 1, 1000000);
            main.loadGraph(GraphCreation.buildCircleGraph(num), "Circle graph");
        }
    };

    public Action GENERATE_STAR = new AbstractAction("Star graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates graph with central hub connected to all other vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_H);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for star graph (up to 1 million).", 1, 1000000);
            main.loadGraph(GraphCreation.buildStarGraph(num), "Star graph");
        }
    };

    public Action GENERATE_WHEEL = new AbstractAction("Wheel graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates a wheel graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_W);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for wheel graph (up to 1 million).", 1, 1000000);
            main.loadGraph(GraphCreation.buildWheelGraph(num), "Wheel graph");
        }
    };

    public Action GENERATE_RANDOM_EDGE = new AbstractAction("Uniform (by number of edges)") {
        {
            putValue(SHORT_DESCRIPTION, "Generate random graph by selecting edges at random");
            putValue(MNEMONIC_KEY, KeyEvent.VK_U);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for random graph (up to 1 million).", 1, 1000000);
            int maxEdges = Math.max(num*(num-1)/2,10000000);
            int numE = showIntegerInputDialog("Enter number of edges in random graph (up to " + maxEdges + ").", 0, maxEdges);
            main.loadGraph(GraphCreation.generateSparseRandomGraph(num, numE, false), "Random graph");
        }
    };

    public Action GENERATE_RANDOM_PROBABILITY = new AbstractAction("Uniform (by probability)") {
        {
            putValue(SHORT_DESCRIPTION, "Generate random graph by applying a uniform probability to each edge");
            putValue(MNEMONIC_KEY, KeyEvent.VK_V);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for random graph (up to 100000).", 1, 100000);
            double prob = showFloatInputDialog("Enter probability for each edge", 0f, 1f);
            main.loadGraph(GraphCreation.generateRandomGraph(num, prob, false), "Random graph");
        }
    };

    public Action GENERATE_PREFERENTIAL = new AbstractAction("Preferential attachment") {
        {
            putValue(SHORT_DESCRIPTION, "Generate random graph using preferential attachment algorithm");
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices in seed graph (should be small).", 1, 10000);
            double prob = showFloatInputDialog("Enter probability for each edge in seed graph", 0f, 1f);
            int num2 = showIntegerInputDialog("Enter number of vertices in final graph (up to 1000000)", 1, 1000000);
            int num3 = showIntegerInputDialog("Enter number of edges to add with each vertex", 0, 1000);
            main.loadGraph(GraphCreation.generatePreferentialAttachmentGraph(num, prob, num2, num3), "Preferential attachment graph");
        }
    };

    public Action GENERATE_PREFERENTIAL2 = new AbstractAction("Preferential attachment (varied edge connections)") {
        {
            putValue(SHORT_DESCRIPTION, "Generate random graph using preferential attachment algorithm, with varied numbers of addon edge connections");
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices in seed graph (should be small).", 1, 10000);
            double prob = showFloatInputDialog("Enter probability for each edge in seed graph", 0f, 1f);
            int num2 = showIntegerInputDialog("Enter number of vertices in final graph (up to 1000000)", 1, 1000000);
            int num3 = showIntegerInputDialog("Enter maximum number of connections to include per added vertex", 0, 1000);
            double[] probs = new double[num3+1];
            for (int i = 0; i <= num3; i++)
                probs[i] = showFloatInputDialog("Enter probability that a new vertex connects to " + i + " vertices.", 0f, 1f);
            main.loadGraph(GraphCreation.generatePreferentialAttachmentGraph(num, prob, num2, probs), "Preferential attachment graph");
        }
    };





    //
    // LAYOUTS
    //

    BetterTimer timer;
    boolean updateEL;

    public Action LAYOUT_CIRCULAR = new AbstractAction("Circular layout") {
        {
            putValue(SHORT_DESCRIPTION, "Layout vertices of current graph around a circle");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null) {
                main.activeGraph.applyLayout(StaticGraphLayout.CIRCLE, 5.0);
                updateEL = true;
            }
        }
    };

    public Action LAYOUT_RANDOM = new AbstractAction("Random layout") {
        {
            putValue(SHORT_DESCRIPTION, "Layout vertices in random locations");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null) {
                main.activeGraph.applyLayout(StaticGraphLayout.RANDOM, 5.0);
                updateEL = true;
            }
        }
    };

    public Action LAYOUT_ENERGY_START = new AbstractAction("Energy layout - start") {
        {
            putValue(SHORT_DESCRIPTION, "Starts energy layout animation");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null) {
                if (main.energy == null)
                    main.energy = new EnergyLayout(main.activeGraph.getGraph(), main.activeGraph.getPoint());
                else if (updateEL)
                    main.energy.initialize(main.activeGraph.getGraph(), main.activeGraph.getPoint());
                timer = new BetterTimer(100);
                timer.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        if (updateEL)
                            main.energy.initialize(main.activeGraph.getGraph(), main.activeGraph.getPoint());
                        main.energy.iterate();
                        main.activeGraph.setPoint(main.energy.getPoints());
                    }
                });
                timer.start();
            }
        }
    };

    public Action LAYOUT_ENERGY_STOP = new AbstractAction("Energy layout - stop") {
        {
            putValue(SHORT_DESCRIPTION, "Stops energy layout animation");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (timer != null)
                timer.stop();
            timer = null;
        }
    };

    public Action LAYOUT_ENERGY_ITERATE = new AbstractAction("Energy layout - iterate") {
        {
            putValue(SHORT_DESCRIPTION, "Iterates energy layout animation");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_I);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null) {
                if (main.energy == null)
                    main.energy = new EnergyLayout(main.activeGraph.getGraph(), main.activeGraph.getPoint());
                else if (updateEL)
                    main.energy.initialize(main.activeGraph.getGraph(), main.activeGraph.getPoint());
                main.energy.iterate();
                main.activeGraph.setPoint(main.energy.getPoints());
            }
        }
    };

    //
    // STATISTICS
    //

    public Action STAT_DEGREE = new AbstractAction("Compute degree distribution") {
        {
            putValue(SHORT_DESCRIPTION, "Computes degree distribution and sets up metric table with results");
            putValue(MNEMONIC_KEY, KeyEvent.VK_D);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null && main.activeGraph.getGraph() instanceof Graph2)
                main.setMetricTableModel(new DistributionTableModel((Graph2) main.activeGraph.getGraph(), GraphMetrics.DEGREE));
        }
    };

    public Action STAT_DEGREE2 = new AbstractAction("Compute degree distribution 2") {
        {
            putValue(SHORT_DESCRIPTION, "Computes degree distribution and sets up metric table with results");
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null && main.activeGraph.getGraph() instanceof Graph2)
                main.setMetricTableModel(new DistributionTableModel((Graph2) main.activeGraph.getGraph(), GraphMetrics.DEGREE2));
        }
    };

    public Action STAT_CLIQUE = new AbstractAction("Compute clique distribution") {
        {
            putValue(SHORT_DESCRIPTION, "Computes clique distribution and sets up metric table with results");
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null && main.activeGraph.getGraph() instanceof Graph2)
                main.setMetricTableModel(new DistributionTableModel((Graph2) main.activeGraph.getGraph(), GraphMetrics.CLIQUE_COUNT));
        }
    };

    public Action POPULATE_METRIC_TABLE = new AbstractAction("Compute standard metrics") {
        {
            putValue(SHORT_DESCRIPTION, "Compute several standard metric for each vertex.");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK));
            putValue(MNEMONIC_KEY, KeyEvent.VK_M);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (main.activeGraph != null && main.activeGraph.getGraph() instanceof Graph2)
                main.activateMetricTable();
        }
    };


    //
    // UTILITY METHODS
    //

    /** Shows option pane to retrieve an integer value in provided range. */
    public static int showIntegerInputDialog(String message, int min, int max) {
        int num = 0;
        do {
            String response = JOptionPane.showInputDialog(message);
            try { num = Integer.decode(response); } catch (NumberFormatException ex) { System.out.println("Improperly formatted number..."); }
        } while (num < min || num > max);
        return num;
    }

    /** Shows option pane to retrieve a float value in provided range. */
    public static float showFloatInputDialog(String message, float min, float max) {
        float num = 0;
        do {
            String response = JOptionPane.showInputDialog(message);
            try { num = Float.parseFloat(response); } catch (NumberFormatException ex) { System.out.println("Improperly formatted number..."); }
        } while (num < min || num > max);
        return num;
    }

}
