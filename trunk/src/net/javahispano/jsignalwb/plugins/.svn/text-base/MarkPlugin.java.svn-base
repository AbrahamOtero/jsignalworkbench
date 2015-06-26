/*
 * MarkPlugin.java
 *
 * Created on 4 de julio de 2007, 15:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;

/**
 * Esta interfaz se corresponde con el plugin de marcas. Tiene los metodos globales
 * de cualquier plugin, asi como aquellos de la interfaz {@link JSignalMonitorMark}
 * para que estas marcas puedan ser representadas en JSignalMonitor. Ademas se anhaden
 * dos metodos para que la marca indique si es un intervalo y en tal caso en que instante
 * finaliza.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface MarkPlugin extends Plugin, JSignalMonitorMark {


    /**
     * Metodo necesario para indicarle a la marca en que instante esta establecida.
     * En caso de ser intervalo el instante de inicio del mismo.
     * @param markTime Tiempo en que se establece la marca.
     */
    public void setMarkTime(long markTime);

    /**
     * Metodo necesario para indicarle a la marca en que instante finaliza el intervalo.
     * No debe ser accedido en caso de que la marca no se corresponda con un
     * intervalo. Ver {@link isInterval()}
     * @param endTime Instante de finalizacion del intervalo.
     */
    public void setEndTime(long endTime);

    /**
     * Metodo necesario para indicarle a la marca la senal en la cual esta contenida.
     * @param {@link Signal} senal en la cual esta contenida
     */
    public void setSignal(Signal signal);

}
