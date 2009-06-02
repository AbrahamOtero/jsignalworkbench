package net.javahispano.jsignalwb.plugins;

import java.io.File;
import java.util.ArrayList;

/**
 * Interfaz que implementan todos los plugings que permitan cargar datos en
 * JSignalWorkbench. Se recomienda sobre escribir la clase {@link LoaderAdapter}
 * o {@link DefaultLoader} en vez de implementar esta interfaz.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface Loader extends Plugin {


    /**
     * Proporciona las extensiones que el loader en concreto acepta a la hora de
     * cargar las senales
     *
     * @return ArrayList
     */
    public ArrayList<String> getAvalaibleExtensions();

    /**
     * Metodo que realiza la carga de los datos.
     *
     * @param file archiva cargar.
     * @param jswbManager {@link JSWBManager}
     * @return true si la carga se realiza correctamente, false en caso
     *   contrario.
     * @throws Exception
     */
    public boolean load(File file) throws
            Exception;

    /** Comunica al loader que la ejecucion debe ser cancelada. Es respondabilidad
     *  del algoritmo gestionar esta parada de la ejecucion.
     */
    public void cancelExecution();
}
