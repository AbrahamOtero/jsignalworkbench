/*
 * AnnotationPlugin.java
 *
 * Created on 17 de julio de 2007, 18:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;

/**
 *
 * @author Compaq_Propietario
 */
public interface AnnotationPlugin extends Plugin, JSignalMonitorAnnotation {
    /**
     * Metodo necesario para indicarle a la anotacion en que instante esta establecida.
     * En caso de ser intervalo el instante de inicio del mismo.
     * @param annotationTime Tiempo en que se establece la anotacion.
     */
    public void setAnnotationTime(long annotationTime);

    /**
     * Metodo necesario para indicarle a la anotacion en que instante finaliza el intervalo.
     * No debe ser accedido en caso de que la anotacion no se corresponda con un
     * intervalo. Ver {@link isInterval()}
     * @param endTime Instante de finalizacion del intervalo.
     */
    public void setEndTime(long endTime);

    public void setJSWBManager(JSWBManager jswbManager);

}
