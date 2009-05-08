/*
 * Grid.java
 *
 * Created on 20 de marzo de 2007, 12:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;

/**
 *
 * @author Roman
 */
public interface JSignalMonitorGrid {

    /**
     * Se encarga de pintar el Grid
     *
     * @param g2d Graphics2D elemento sobre el cual se va a pintar
     * @param p Point representa la esquina superior izquierda del grid height
     * @param height int izquierda del grid height
     * @param width int altura del grid en pixeles widht
     * @param gridconfig {@link GridConfiguration} cualquier información de configuración del grid
     */
    public void paintGrid(Graphics2D g2d, Point p,
                          int height, int width, GridConfiguration gridconfig);

    /**
     * Este método es invocado cuando el usuario desee cambiar algún parámetro de configuración del grid.
     *
     * @param owner Window
     */
    public void launchConfigureGridGUI(Window owner);

    /** Este método devolverá el número de pixeles que desea que tenga como
     *  tamaño la leyenda horizontal. Idealmente sera la separación entre las
     *  marcas mas distantes del grid.
     */
    public int getLeyendWidth();
    /** Este método devolverá el número de pixeles que desea que tenga como
     *  tamaño la leyenda vertical. Idealmente sera la separación entre las
     *  marcas mas distantes del grid.
     */
    public int getLeyendHeight();

    /**
     * Información que el grid desea que sea persistida. Es responsabilidad del código cliente persistir esta
     * información. Habitualmente, se trata de información de configuración del grid.
     *
     * @return String
     */
    public String getDataToSave();

    /**
     * Información que se persistió en la última sesión en la cual se empleó este grid.
     *
     * @param data String
     */
    public void setSavedData(String data);
}

