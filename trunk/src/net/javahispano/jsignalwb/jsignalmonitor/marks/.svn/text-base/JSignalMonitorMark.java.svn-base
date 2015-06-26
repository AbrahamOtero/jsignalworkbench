/*
 * JSignalMonitorMark.java
 *
 * Created on 4 de julio de 2007, 14:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.*;

/**
 * Esta interfaz debera ser implementada por las clases que deseen representar
 * marcas en JSiganlMonitor.
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface JSignalMonitorMark {
    /**
     * Proporciona la imagen a mostrar en la marca.
     * Se recomienda mirar {@link BufferedImage}.
     * @return Image Imagen a mostrar
     */
    public Image getImage();


    /**
     * Indica el tiempo en el que se situa la marca. En caso de ser un intervalo
     * devuelve el instante de inicio del mismo.
     * @return long Intante de tiempo de la marca o inicio de intervalo.
     */
    public long getMarkTime();

    /**
     * Indica si la marca se corresponde con un instante o un intervalo.
     * @return True si la marca es de un intervalo.
     * @return False si la marca es de un instante.
     */
    public boolean isInterval();

    /**
     * Indica el tiempo final del intervalo. No debe ser accedido en caso de que
     * la marca no se corresponda con un intervalo. Ver {@link isInterval()}.
     * @return long Tiempo del instante final del intervalo
     */
    public long getEndTime();

    /**
     * Muestra una pantalla con la informacion de la marca.
     * @param owner Ventana propietaria, en funcion de la cual se deberia colocar
     * la ventana de informacion.
     */
    public void showMarkInfo(Window owner);

    /**
     * Proporciona el texto que se mostrara a modo de ToolTip al pasar el raton sobre
     * la marca
     * @return Texto a mostrar a modo de ToolTip
     */
    public String getToolTipText();

    public boolean isOwnPainted();

    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo);

}
