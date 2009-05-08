package net.javahispano.jsignalwb.plugins;

import java.io.*;

import net.javahispano.jsignalwb.*;

/**
 * <p>Clase Adapter para la interfaz {@link Saver}.</p>
 *
 * <p> Para realizar la carga de datos el usuario puede extender cualquiera de
 * los tres métodos save definidos en esta clase. </p>
 *
 *
 * <p> La forma preferida de crear un nuevo Saver es extendiendo  a {@link DefaultSaver}.</p>
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero.
 */
public abstract class SaverAdapter extends PluginAdapter implements Saver {
    private boolean executionCanceled = false;
    /**
     * Invoca a <code>save(File f, SignalManager sm)</code> pasándole como
     * argumento los dos primeros argumentos que recibe este método.
     *
     * @param f File
     * @param sm SignalManager
     * @param pm PluginManager
     * @return boolean
     * @throws Exception
     */
    public boolean save(File f, JSWBManager jswbManager) throws
            Exception {
        return save(f, jswbManager.getSignalManager());
    }

    /**
     * Invoca al método <code>save(File f, float[][] data) </code> pasándole el
     * primer argumento que recibe este método y un array con todos los arrays
     * de datos de las señales cargadas en el entorno.
     *
     * @param f File
     * @param sm SignalManager
     * @return boolean
     * @throws Exception
     */
    public boolean save(File f, SignalManager sm) throws Exception {
        float data[][] = new float[sm.getSignals().size()][];
        int index = 0;
        for (Signal s : sm.getSignals()) {
            data[index] = s.getValues();
            index++;
        }
        return save(f, data);
    }

    /**
     * Este método debe almacenar en el fichero que se le pasa como primer
     * argumento las señales que se le pasan, en forma de array, como segundo
     * argumento.
     *
     * @param f File
     * @param data float[][]
     * @return false
     * @throws Exception
     */
    public boolean save(File f, float[][] data) throws Exception {
        return false;
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
