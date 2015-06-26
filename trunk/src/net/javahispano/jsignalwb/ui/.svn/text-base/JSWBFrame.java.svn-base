/*
 * testFrame.java
 *
 * Created on 13 de febrero de 2007, 22:13
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.JSWBWindowListener;
import net.javahispano.jsignalwb.jsignalmonitor.MoveScrollPanel;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.Plugin.GUIPositions;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

/**
 * ventana principal de la aplicacion.No disenhado como parte del API.
 *
 * @author Roman
 */
public class JSWBFrame extends javax.swing.JFrame {
    private javax.swing.JMenuBar jMenuBar;
    private JToolBar jToolBar;
    private JPanel centerPanel;
    private JPanel statusBarPanel;
    //private JMenu jMenuPlugins;
    //private JSignalMonitorPanel jsmPanel;
    //private PluginManager pm;
    /** Creates new form testFrame */
    public JSWBFrame(JSWBManager jswbManager) {
        this.setTitle("JSignalWorkBench");
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/jswIcon.jpg")));
        jswbManager.setParentWindow(this);
        jswbManager.loadPreviouslyFile();
        centerPanel = jswbManager.getJSWBPanel();
        jswbManager.addJMenuBarItem(new JMenuFile());
        jswbManager.addJMenuBarItem(new JMenuSignals(jswbManager));
        jswbManager.addJMenuBarItem(new JMenuPlugins(jswbManager));
        jswbManager.addJMenuBarItem(new JMenuActions(jswbManager));
        jswbManager.addJMenuBarItem(new JMenuAbout());
        jMenuBar = jswbManager.getJMenuBar();
        initJToolBar(jswbManager);
        jToolBar = jswbManager.getJToolBar();
        statusBarPanel = jswbManager.getStatusBarPanel();
        initComponents();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (d.getWidth() * 0.8), (int) (d.getHeight() * 0.8));
        this.setLocation((int) (d.getWidth() * 0.1), (int) (d.getHeight() * 0.1));
        //jswbManager.setJSWBFrame(this);
        jswbManager.refreshJSM(false);

        this.addWindowListener(new JSWBWindowListener(jswbManager));
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                setVisible(true);
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setJMenuBar(jMenuBar);
        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(jToolBar, BorderLayout.NORTH);
        add(statusBarPanel, BorderLayout.SOUTH);

        pack();
    }

    /**
     *
     * que se ejecute adecuadamente aunque no haya parametros de la consola
     * @todo obtener los plugin de desarrollo de un modo adecuado
     *
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIManager.installLookAndFeel("NimROD", "com.nilo.plaf.nimrod.NimRODLookAndFeel");
                try {
                    UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");

                } catch (UnsupportedLookAndFeelException ex1) {
                } catch (IllegalAccessException ex1) {
                } catch (InstantiationException ex1) {
                } catch (ClassNotFoundException ex1) {
                }
                boolean develop = true; //cierto solo si se esta usando el framework para el desarrollo de plugings
                try {
                    develop = Boolean.parseBoolean(args[0]);
                    //path = args[1];
                    //pluginsPath = args[1];
                } catch (Exception ex) {
                    //no se proporciona parmetro y se usa el valor por defecto
                }
                JSWBManager jswbManager = JSWBManager.getJSWBManagerInstance(develop);
                new JSWBFrame(jswbManager);
            }
        });
    }


    /**
     * @todo ahora este metodo fuerza que se cae de los plugin. Buscar una solucion para esto mas adelante.
     *
     * @param jswbManager JSWBManager
     */
    private void initJToolBar(JSWBManager jswbManager) {
        PluginManager pluginManager = jswbManager.getPluginManager();
        jswbManager.addJToolBarButton(new NewAction());
        // jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarButton(new OpenFileAction(jswbManager));
        jswbManager.addJToolBarButton(new SaveAction(jswbManager));
        // jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarButton(new GenericPluginAction(jswbManager,
                "JSWTextProcessorPlugin",
                GenericPluginAction.LAUNCH_ACTION));

        //jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarButton(new PrintAction());

        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));

        jswbManager.addJToolBarButton(new GenericPluginAction(jswbManager,
                "SignalOrganizerPlugin",
                GenericPluginAction.LAUNCH_ACTION));

        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarSeparator();
        HashMap<String, ArrayList<String>> plugins = pluginManager.getRegisteredPlugins();
        ArrayList<String> algorithms = plugins.get("algorithm");
        if (algorithms != null && algorithms.size() > 0) {
            boolean labelAded = false;
            for (String algorithm : algorithms) {
                if (pluginManager.getAlgorithm(algorithm).showInGUIOnthe(GUIPositions.TOOLBAR)) {
                    if (!labelAded) {
                        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(4));
                        JLabel lAlg = new JLabel(" Algorithms: ");
                        lAlg.setFont(JSWBManager.getNormalFont());
                        lAlg.setForeground(JSWBManager.getFontColor());
                        jswbManager.addJToolBarComponent(lAlg);
                        labelAded = true;
                    }
                    jswbManager.addJToolBarButton(new AlgorithmAction(algorithm,
                            AlgorithmAction.RUN_ACTION, jswbManager));
                }
            }
            if (labelAded) {
                jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
                jswbManager.addJToolBarSeparator();
            }
        }

        ArrayList<String> genericPlugins = plugins.get("generic");
        if (genericPlugins != null && genericPlugins.size() > 0) {
            boolean labelAded = false;
            for (String genericPlugin : genericPlugins) {
                if (pluginManager.getGeneric(genericPlugin).showInGUIOnthe(GUIPositions.TOOLBAR)) {
                    if (!labelAded) {
                        JLabel lGen = new JLabel(" Generics: ");
                        lGen.setFont(JSWBManager.getNormalFont());
                        lGen.setForeground(JSWBManager.getFontColor());
                        jswbManager.addJToolBarComponent(lGen);
                        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(4));
                        labelAded = true;
                    }
                    jswbManager.addJToolBarButton(new GenericPluginAction(jswbManager, genericPlugin,
                            GenericPluginAction.LAUNCH_ACTION));
                }
            }
            if (labelAded) {
                jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
                jswbManager.addJToolBarSeparator();
            }
        }
        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarButton(new AdjustSignalVisibleRangeAction(jswbManager));
        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarComponent(new JToggleButtonXY(jswbManager.getJSignalMonitor()));
        jswbManager.addJToolBarComponent(new JToggleButtonAddMarks(jswbManager.getJSignalMonitor()));
        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarSeparator();
        jswbManager.addJToolBarComponent(Box.createHorizontalStrut(8));
        jswbManager.addJToolBarComponent(new MoveScrollPanel(jswbManager.getJSignalMonitor()));
    }
}
