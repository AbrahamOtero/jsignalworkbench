package net.javahispano.jsignalwb.plugins;

import java.io.*;

import net.javahispano.jsignalwb.*;

/**
 * clase que implementa {@link Loader} proporcionando una implementaci�n por
 * defecto para parte de sus m�todos.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Rom�n Segador y
 *   Abraham Otero
 */
public abstract class LoaderAdapter extends PluginAdapter implements Loader {
    private boolean executionCanceled = false;

    /**
     *
     * @return false.
     */
    public boolean isPartialAccesible() {
        return false;
    }


    /**
     * getPartialSignal
     *
     * @throws UnsupportedOperationException si se invoca.
     */
    public float[] getPartialSignal(int start, int distance) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>Invoca al m�todo  <code>load(File f, SignalManager sm)</code> con
     * los dos primeros argumentos que se le pasan y
     * devuelve el resultado de la invocaci�n.</p>
     *
     * <p> el El usuario puede sobrescribir s�lo dicho m�todo y
     *  el plugin funcionar� adecuadamente.</p>
     *
     * @param f File
     * @param sm SignalManager
     * @param pm PluginManager
     * @return boolean
     * @throws Exception
     */
    public boolean load(File f, JSWBManager jswbManager) throws
            Exception {
        return load(f, jswbManager.getSignalManager());
    }

    /**
     * <p> Construye un conjunto de objetos {@link Signal}, con nombres
     * "SignalX", donde X es un entero que se va incrementando para cada nueva
     * se�al, y con los datos que devuelve el m�todo load (File). Dichos objetos
     * son cargados en el {@link SignalManager}. </p>
     *
     * <p>Todas las se�ales tienen frecuencia de muestreo unidad y el
     * principio del registro ser� el 1 de enero de 1970.</p>
     *
     * <p> el usuario puede sobrescribir tanto este m�todo como el m�todo load
     * (File) para realizar la carga de datos. </p>
     *
     * @param f File
     * @param sm SignalManager
     * @return boolean
     * @throws Exception
     */
    protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
        float[][] values = load(f);
        if (values != null) {
        int i = 0;
            for (float[] val : values) {
                Signal s = new Signal("Signal" + i, val);
                s.setFrecuency(1);
                i++;
                if (!sm.addSignal(s)) {
                    flag = false;
                }else{
                    s.adjustVisibleRange();
                }
            }
        }
        return flag;
    }

    /**
     * La implementaci�n por defecto de vuelve null. Si el usuario s�lo escribe
     * este m�todo y de vuelve un array que contenga varios arrays con se�ales
     * estas ser�n cargadas en el entorno con frecuencia de muestreo unidad y
     * con un nombre gen�rico.
     *
     * @param f File
     * @return null
     * @throws Exception
     */
    protected float[][] load(File f) throws Exception {
        return null;
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
