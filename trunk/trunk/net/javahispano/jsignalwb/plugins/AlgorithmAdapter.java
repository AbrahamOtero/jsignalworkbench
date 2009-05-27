/*
 * AlgorithmAdapter.java
 *
 * Created on 11 de abril de 2007, 13:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.SignalManager;
import java.util.*;
import net.javahispano.jsignalwb.plugins.defaults.*;
import net.javahispano.jsignalwb.plugins.framework.*;

/**
 *
 * @author Roman
 */
public abstract class AlgorithmAdapter extends PluginAdapter implements
        Algorithm {

    private boolean executionCanceled = false;
    /** Por defecto no proporciona interfaz de usuario */
    public boolean hasOwnExecutionGUI() {
        return false;
    }

    /** Por defecto lanzara una excepcion */
    public void launchExecutionGUI(JSWBManager jswbManager) {
        JDialog conf = new JDialog(jswbManager.getParentWindow(), "Execution GUI");

        conf.setModal(true);
        JPanel jPane = new DefaultAlgorithmConfiguration(this,
                jswbManager, conf);
        conf.getContentPane().add(jPane);
        conf.setSize(jPane.getPreferredSize());
        conf.setResizable(false);
        conf.setLocationRelativeTo(conf.getParent());
        conf.setVisible(true);
    }

    public boolean hasResultsGUI() {
        return false;
    }

    public void launchResultsGUI(JSWBManager jswbManager) {
        throw new UnsupportedOperationException("No results GUI defined");
    }
    /**
     * El usuario que implemente esta clase puede elegir sobre escribir sólo este método para
     * procesar su señal.
     *
     * @param sm SignalManager
     * @param signals List
     */
    public void runAlgorithm(SignalManager sm,
                             float[] signal) {
    }

    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        if (signals.size()==1) {
     this.runAlgorithm(sm, signals.get(0).getSignal().getValues());
 }

    }

    public int numberOfSignalsNeeded() {
        return 0;
    }

    public void cancelExecution() {
        executionCanceled = true;
    }

    public boolean isExecutionCanceled() {
        return executionCanceled;
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }

}
