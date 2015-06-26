/*
 * JSignalMonitor.java
 *
 * Created on 13 de abril de 2007, 11:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



package net.javahispano.jsignalwb.jsignalmonitor;

import java.util.ArrayList;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;

/**
 *
 * @author Roman
 */
public interface JSignalMonitorDataSource {

    /**
     * Devuelve los valores de la senhal que se le pasa como parametro en el
     * intervalo del tiempo solicitado.
     *
     * @param signalName nombre de la senhal.
     * @param firstValue Primer instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 del cual se requiere los valores de la senhal.
     *   Ver {@link TimePositionConverter}.
     * @param lastValue Ultimo instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 del cual se requiere los valores de la senhal.
     *   Ver {@link TimePositionConverter}.
     * @return Valores de la senhal en el intervalo indicado. Si la senhal no
     *   estuviese definida en alguna parte de ese intervalo de tiempo se
     *   considerara que su valor es el de la abscissa. ver {@link JSignalMonitor.getScrollValue()}
     * @throws {@link SignalNotFoundException} si no existe una senhal con el
     *   nombre indicado cargada en el entorno.
     * @todo (Abraham) Si la senhal no esta definida se devuelve el valor de la abscissa
     *  en vez de cero.
     */
    public float[] getChannelData(String signalName, long firstValue,
                                  long lastValue);

    /**
     * Devuelve los valores de enfasis de la senhal que se le pasa como parametro
     * en el intervalo del tiempo solicitado. En la implementacion actual, el
     * enfasis se muestra coloreando aquellas partes de la senhal cuyo nivel de
     * enfasis es superior a 0. El mayor nivel de enfasis se representa mediante
     * el color rojo.
     *
     * @param signalName nombre de la senhal.
     * @param firstValue Primer instante de tiempo medido en milisegundos desde
     *   el 00:00:00 01/01/1970 del cual se requiere los valores de enfasis.
     *   Ver {@link TimePositionConverter}.
     * @param lastValue Ultimo instante de tiempo medido en milisegundos desde
     *   el 00:00:00 01/01/1970 del cual se requiere los valores de enfasis Ver
     *   {@link TimePositionConverter}.
     * @return Valores de enfasis de la senhal en el intervalo indicado. Los
     *   valores de enfasis deberan estar comprendidos en el intervalo [0,
     *   100], correspondiendose el 0 con el nivel mas bajo de enfasis y el 100
     *   con el nivel mas alto. Si la senhal no estuviese definida en alguna
     *   parte de ese intervalo de tiempo se considerara que su valor es 0.
     */
    public short[] getSignalEmphasisLevels(String signalName, long firstValue,
                                           long lastValue);

    /**
     * Devuelve un listado de las marcas de la senhal que se le pasa como parametro en el
     * intervalo del tiempo solicitado. Ver {@link JSignalMonitorMark}.
     *
     * @param signalName nombre de la senhal.
     * @param firstValue Primer instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 del cual se requieren las marcas de la senhal.
     *   Ver {@link TimePositionConverter}.
     * @param lastValue Ultimo instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 del cual se requieren las marcas de la senhal.
     *   Ver {@link TimePositionConverter}.
     * @return Marcas de la senhal en el intervalo indicado. Si la senhal no
     *   tuviera definida en ese intervalo de tiempo la lista sera vacia.
     * @throws {@link SignalNotFoundException} si no existe una senhal con el
     *   nombre indicado cargada en el entorno.
     */
    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue,
            long lastValue);

    /**
     * Devuelve un listado de las anotaciones en el intervalo del tiempo
     * solicitado. Ver {@link JSignalMonitorAnnotation}.
     *
     * @param firstValue Primer instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 del cual se requieren las anotaciones.
     *   Ver {@link TimePositionConverter}.
     * @param lastValue Ultimo instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 del cual se requieren las anotaciones.
     *   Ver {@link TimePositionConverter}.
     * @return Anotaciones en el intervalo indicado. Si no
     *   tuviera definida en ese intervalo de tiempo la lista sera vacia.
     */
    public List<JSignalMonitorAnnotation> getAnnotations(long fistValue, long endValue);

    /**
     * Devuelve el valor de la senhal que se le pasa como parametro en el
     * instante del tiempo solicitado.
     *
     * @param signalName nombre de la senhal.
     * @param time Instante de tiempo medido en milisegundos desde 00:00:00
     *   01/01/1970. Ver {@link TimePositionConverter}.
     * @return Valor de la senhal en el instante indicado. Si la senhal no
     *   estuviese definida en ese instante de tiempo se considerara que su
     *   valor es 0.
     * @throws {@link SignalNotFoundException} si no existe una senhal con el
     *   nombre indicado cargada en el entorno.
     */
    public float getChannelValueAtTime(String signalName, long time);

    public List<String> getAvailableKindsOfInstantMarks();

    public List<String> getAvailableKindsOfIntervalMarks();

    public List<String> getAvailableKindsOfInstantAnnotations();

    public List<String> getAvailableKindsOfIntervalAnnotations();

    public ArrayList<String> getAvailableCategoriesOfAnnotations();

    /**
     * Notifica al {@link JSignalMonitorDataSource} de que el usuario ha
     * terminado la seleccion de un intervalo de senhal.
     *
     * @param signalName nombre de la senhal.
     * @param startTime Primer instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado. Ver {@link TimePositionConverter}.
     * @param endTime Ultimo instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado Ver {@link TimePositionConverter}.
     * @todo (Abraham) "verificar que el javadoc es correcto. en concreto ?el
     *   intervalo que se solicita es abierto o cerrado?"--> el intervalo es cerrado,
     *   startTime y endTime estan incluidos en el mismo.
     */
    public void notifyIntervalSelection(String signalName, long startTime,
                                        long endTime);

    /**
     * Notifica al {@link JSignalMonitorDataSource} de que el usuario ha
     * terminado creacion de una marca de instante en la senhal.
     *
     * @param kindOfMark nombre del tipo de marca que se desea crear.
     * @param signalName nombre de la senhal.
     * @param time Instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado. Ver {@link TimePositionConverter}.
     */
    public void notifyMarkAdded(String kindOfMark, String signalName, long time);

    /**
     * Notifica al {@link JSignalMonitorDataSource} de que el usuario ha
     * terminado la seleccion de una marca de intervalo en la senhal.
     *
     * @param kindOfMark nombre del tipo de marca que se desea crear.
     * @param signalName nombre de la senhal.
     * @param startTime Primer instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado. Ver {@link TimePositionConverter}.
     * @param endTime Ultimo instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado Ver {@link TimePositionConverter}.
     */
    public void notifyMarkAdded(String kindOfMark, String signalName, long startTime, long endTime);

    /**
     * Notifica al {@link JSignalMonitorDataSource} de que el usuario ha
     * terminado creacion de una anotacion de instante.
     *
     * @param kindOfAnnotation nombre del tipo de Annotation que se desea crear.
     * @param time Instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado. Ver {@link TimePositionConverter}.
     */
    public void notifyAnnotationAdded(String kindOfAnnotation, long time);

    /**
     * Notifica al {@link JSignalMonitorDataSource} de que el usuario ha
     * terminado la seleccion de una anotacion de intervalo.
     *
     * @param kindOfAnnotation nombre del tipo de Annotation que se desea crear.
     * @param signalName nombre de la senhal.
     * @param startTime Primer instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado. Ver {@link TimePositionConverter}.
     * @param endTime Ultimo instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 seleccionado Ver {@link TimePositionConverter}.
     */
    public void notifyAnnotationAdded(String kindOfAnnotation, long startTime, long endTime);

}
