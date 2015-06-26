package net.javahispano.jsignalwb.plugins;

import java.io.File;

import net.javahispano.jsignalwb.*;
import java.util.Date;

/**
 * clase que implementa {@link Loader} proporcionando una implementacion por
 * defecto para parte de sus metodos.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public abstract class LoaderAdapter extends PluginAdapter implements Loader {
    private boolean executionCanceled = false;

    /**
     * <p>Invoca al metodo  <code>load(File f, SignalManager sm)</code> con
     * los dos primeros argumentos que se le pasan y
     * devuelve el resultado de la invocacion.</p>
     *
     * <p> el El usuario puede sobrescribir solo dicho metodo y
     *  el plugin funcionara adecuadamente.</p>
     *
     * @param f File
     * @param sm SignalManager
     * @param pm PluginManager
     * @return boolean
     * @throws Exception
     */
    public boolean load(File f) throws
            Exception {
        return load(f, JSWBManager.getSignalManager());
    }

    /**
     * <p> Construye un conjunto de objetos {@link Signal}, con nombres
     * "SignalX", donde X es un entero que se va incrementando para cada nueva
     * senhal, y con los datos que devuelve el metodo load (File). Dichos objetos
     * son cargados en el {@link SignalManager}. </p>
     *
     * <p>Todas las senhales tienen frecuencia de muestreo unidad y el
     * principio del registro sera el 1 de enero de 1970.</p>
     *
     * <p> el usuario puede sobrescribir tanto este metodo como el metodo load
     * (File) para realizar la carga de datos. </p>
     *
     * @param f File
     * @param sm SignalManager
     * @return boolean
     * @throws Exception
     */
    protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
        float[][] values = loadSignals(f);
        Object[] signals = sm.getSignals().toArray();
        Signal sr = null;
        if (signals.length > 0) {
            sr = (Signal) signals[0];
        }
        if (values != null) {
            int i = 0;
            for (float[] val : values) {
                Signal s = new Signal("Signal" + i, val);

                i++;
                if (sr != null) {
                    s.setStart(sr.getStart());
                    s.setFrecuency(sr.getSRate());
                } else {
                    Date d = new Date();
                    d.setHours(0);
                    d.setMinutes(0);
                    d.setSeconds(0);
                    s.setStart(d.getTime());

                }
                if (!sm.addSignal(s)) {
                    flag = false;
                } else {
                    s.adjustVisibleRange();
                }
            }
        }
        return flag;
    }

    /**
     * La implementacion por defecto de vuelve null. Si el usuario solo escribe
     * este metodo y de vuelve un array que contenga varios arrays con senhales
     * estas seran cargadas en el entorno con frecuencia de muestreo unidad y
     * con un nombre generico.
     *
     * @param f File
     * @return null
     * @throws Exception
     */
    protected float[][] loadSignals(File f) throws Exception {
        return null;
    }

    public void cancelExecution() {
        executionCanceled = true;
    }

    /**
     * Devuelve true si la ejecucion ha sido cancelada.
     *
     * @return boolean
     */
    public boolean isExecutionCanceled() {
        return executionCanceled;
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

}
