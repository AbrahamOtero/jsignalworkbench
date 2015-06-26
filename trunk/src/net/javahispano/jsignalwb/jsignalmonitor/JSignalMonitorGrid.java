package net.javahispano.jsignalwb.jsignalmonitor;


import java.awt.*;

/*Interfaz que debe ser implementada por los grid que emplea {@link JSignalMonitor}.
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface JSignalMonitorGrid {

    /**
     * Se encarga de pintar el Grid
     *
     * @param g2d Graphics2D elemento sobre el cual se va a pintar
     * @param p Point representa la esquina superior izquierda del grid height
     * @param height int izquierda del grid height
     * @param width int altura del grid en pixeles widht
     * @param gridconfig {@link GridConfiguration} cualquier informacion de configuracion del grid
     */
    public void paintGrid(Graphics2D g2d, Point p,
                          int height, int width, GridConfiguration gridconfig);

    /**
     * Este metodo es invocado cuando el usuario desee cambiar algun parametro de configuracion del grid.
     * Este es el sitio para lanzar la interfaz de usuario de configuracion del grid.
     * @param owner Window
     */
    public void launchConfigureGridGUI(Window owner);

    /** Este metodo devolvera el numero de pixeles que desea que tenga como
     *  tamanho la leyenda horizontal. Idealmente sera la separacion entre las
     *  marcas mas distantes del grid.
     */
    public int getLeyendWidth();

    /** Este metodo devolvera el numero de pixeles que desea que tenga como
     *  tamanho la leyenda vertical. Idealmente sera la separacion entre las
     *  marcas mas distantes del grid.
     */
    public int getLeyendHeight();

    /**
     * Informacion que el grid desea que sea persistida. Es responsabilidad del codigo cliente persistir esta
     * informacion. Habitualmente, se trata de informacion de configuracion del grid.
     *
     * @return String
     */
    public String getDataToSave();

    /**
     * Informacion que se persistio en la ultima sesion en la cual se empleo este grid.
     *
     * @param data String
     */
    public void setSavedData(String data);
}
