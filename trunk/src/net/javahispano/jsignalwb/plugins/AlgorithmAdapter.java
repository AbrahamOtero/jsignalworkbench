package net.javahispano.jsignalwb.plugins;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultAlgorithmConfiguration;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/*
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public abstract class AlgorithmAdapter extends PluginAdapter implements
        Algorithm {

    private boolean executionCanceled = false;

    /**
     * Por defecto no proporciona interfaz propia de ejecucion.
     *
     * @return boolean
     */
    public boolean hasOwnExecutionGUI() {
        return false;
    }


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

    /**
     * Por defecto no proporciona interfaz de usuario.
     *
     * @return boolean
     */
    public boolean hasResultsGUI() {
        return false;
    }

    public void launchResultsGUI(JSWBManager jswbManager) {
        throw new UnsupportedOperationException("No results GUI defined");
    }

    /**
     * El usuario que implemente esta clase puede elegir sobre escribir solo este metodo para
     * procesar su senhal. A este metodo se le pasa el array de datos
     * de la primera senhal que haya seleccionado el usuario.
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
        if (signals.size() == 1) {
            this.runAlgorithm(sm, signals.get(0).getSignal().getValues());
        }

    }

    /**
     * Devuelve 0; este valor indica que el algoritmo no tiene un numero maximo
     * de senhales/intervalos a procesar.
     *
     * @return int
     */
    public int numberOfSignalsNeeded() {
        return 0;
    }

    public void cancelExecution() {
        executionCanceled = true;
    }

    /**
     * Devuelve true si la ejecucion del algoritmo ha sido cancelada.
     *
     * @return boolean
     */
    public boolean isExecutionCanceled() {
        return executionCanceled;
    }

    /**
     * Por defecto los algoritmo se muestran en el menu de plugins y en la barra
     * de tareas. Este comportamiento puede cambiarse sobreescribiendo este
     * metodo.
     *
     * @param gUIPositions GUIPositions
     * @return boolean
     */
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }

}
