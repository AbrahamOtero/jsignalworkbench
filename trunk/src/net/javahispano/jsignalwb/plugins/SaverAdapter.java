package net.javahispano.jsignalwb.plugins;

import java.io.File;

import net.javahispano.jsignalwb.*;

/**
 * <p>Clase Adapter para la interfaz {@link Saver}.</p>
 *
 * <p> Para realizar la carga de datos el usuario puede extender cualquiera de
 * los tres metodos save definidos en esta clase. </p>
 *
 *
 * <p> La forma preferida de crear un nuevo Saver es extendiendo  a {@link DefaultSaver}.</p>
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero.
 */
public abstract class SaverAdapter extends PluginAdapter implements Saver {
    private boolean executionCanceled = false;
    /**
     * Invoca a <code>save(File f, SignalManager sm)</code> pasandole como
     * argumento los dos primeros argumentos que recibe este metodo.
     *
     * @param f File
     * @param sm SignalManager
     * @param pm PluginManager
     * @return boolean
     * @throws Exception
     */
    public boolean save(File f) throws
            Exception {
        return save(f, JSWBManager.getSignalManager());
    }

    /**
     * Invoca al metodo <code>save(File f, float[][] data) </code> pasandole el
     * primer argumento que recibe este metodo y un array con todos los arrays
     * de datos de las senhales cargadas en el entorno.
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
     * Este metodo debe almacenar en el fichero que se le pasa como primer
     * argumento las senhales que se le pasan, en forma de array, como segundo
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
        return false;
    }


}
