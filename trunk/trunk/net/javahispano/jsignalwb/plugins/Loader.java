package net.javahispano.jsignalwb.plugins;

import java.io.*;
import java.util.*;

import net.javahispano.jsignalwb.*;

/**
 * Interfaz que implementan todos los plugings que permitan cargar datos en
 * JSignalWorkbench. Se recomienda sobre escribir la clase {@link LoaderAdapter}
 * o {@link DefaultLoader} en vez de implementar esta interfaz.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
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
     * True si el loader concreto permite cargar una parte concreta de los datos
     * y no el conjunto global de los mismos, false en caso contrario
     *
     * @return boolean
     */
    public boolean isPartialAccesible();

    /**
     * En caso de permitir el acceso parcial de datos, proporciona el fragmento
     * de datos con comienzo temporal en start.
     *
     * @param start Instante correspondiente con el primer dato que se
     *   solicita. Se mide en milisegundos a partir de 00:00:00 01/01/1970. Ver
     *   {@link TimePositionConverter}.
     * @param distance Instante correspondiente con el último dato parco se
     *   solicitan datos. Se mide en milisegundos desde 00:00:00 01/01/1970.
     *   Ver {@link TimePositionConverter}.
     * @return datos solicitados.
     */
    public float[] getPartialSignal(int start, int distance);

    /**
     * Método que realiza la carga de los datos.
     *
     * @param file archiva cargar.
     * @param jswbManager {@link JSWBManager}
     * @return true si la carga se realiza correctamente, false en caso
     *   contrario.
     * @throws Exception
     */
    public boolean load(File file, JSWBManager jswbManager) throws
            Exception;
    
    public void cancelExecution();
}
