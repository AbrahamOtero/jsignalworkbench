package net.javahispano.jsignalwb.plugins;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.SwingWorker;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import javax.swing.JOptionPane;

/**
 * <p>Peque�a clase de utilidad que permite informar al usuario de las
 * actividades que est� realizando un plugin tipo {@link Algorithm}.</p>
 *
 * @author Rom�n Segador y Abraham Otero
 *    Copyright 2006-2007. This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/).
 */
public class AlgorithmRunner extends SwingWorker<Boolean, Void> {
    private Algorithm algorithm;
    private JSWBManager jswbManager;
    private ArrayList<SignalIntervalProperties> signals;
    private String task;
    private String signalName;
    /**
     * Creates a new instance of AlgorithmRunner.
     * No forma parte de la API.
     *
     * @param alg Algorithm
     * @param jswbManager JSWBManager
     * @param signals ArrayList
     */
    public AlgorithmRunner(Algorithm alg, JSWBManager jswbManager,
                           ArrayList<SignalIntervalProperties> signals) {
        this.algorithm = alg;
        this.jswbManager = jswbManager;
        this.signals = signals;
        task = alg.getShortDescription();
        signalName = "unknown";
    }

    protected Boolean doInBackground() throws Exception {
        algorithm.runAlgorithm(jswbManager.getSignalManager(), signals, this);
        return Boolean.valueOf(true);

    }

    
    protected void done() {
        //super.done();
        Boolean end = Boolean.valueOf(false);
        try {
            end = get();
        } catch (Exception e) {
            if (!isCancelled()) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                  "Ha sucedido un error al ejecutar el algoritmo "+
                  algorithm.getName()+" versi�n "+
                  algorithm.getPluginVersion(),
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

        }
        /*if (algorithm.hasResultsGUI()) {
            algorithm.launchResultsGUI(jswbManager);
                 }*/
        if (end.booleanValue()) {
            jswbManager.refreshJSM(false);
        }
    }

    /**
     * Mostrar� al usuario el porcentaje completado de una de las tareas que el
     * algoritmo realiza o del procesador global.
     *
     * @param progress porcentaje completado.
     */
    public void setARProgress(int progress) {
        setProgress(progress);
    }

    /**
     * Informa al usuario sobre que tarea est� realizando en cada momento el
     * algoritmo.
     *
     * @param task cadena de texto describiendo la tarea.
     */
    public void setTaskRunning(String task) {
        firePropertyChange("task", this.task, task);
        this.task = task;
    }


    /**
     * Informa al usuario sobre qu� se�al se est� procesando en cada momento.
     *
     * @param signal nombre de la se�al.
     */
    public void setSignalRunning(String signal) {
        firePropertyChange("signal", this.signalName, signal);
        this.signalName = signal;
    }

}
