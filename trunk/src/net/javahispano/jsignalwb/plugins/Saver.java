package net.javahispano.jsignalwb.plugins;

import java.io.File;
import java.util.ArrayList;

/**
 * <p>Interfaz que deben implementar todos los plugin que pretendan extender
 * JSignalWorkbench para permitir almacenar datos en un nuevo formato.</p>
 *
 * <p> La forma preferida de crear un nuevo Saver es extendiendo a
 * {@link SaverAdapter}o, preferiblemente, a {@link DefaultSaver}.</p>
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero.
 */
public interface Saver extends Plugin {

    /**
     * Devuelve las extensiones correspondientes con el formato de los archivos
     * que permite generar este plugin.
     *
     * @return ArrayList
     */
    public ArrayList<String> getAvalaibleExtension();

    public boolean save(File f) throws
            Exception;

    /** Comunica al loader que la ejecucion debe ser cancelada. Es respondabilidad
     *  del algoritmo gestionar esta parada de la ejecucion.
     */
    public void cancelExecution();
}
