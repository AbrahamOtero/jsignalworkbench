package net.javahispano.jsignalwb.jsignalmonitor;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;

/*Interfaz que debe ser implementada por los grid que emplea {@link JSignalMonitor}.

 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Rom�n Segador y
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
     * @param gridconfig {@link GridConfiguration} cualquier informaci�n de configuraci�n del grid
     */
    public void paintGrid(Graphics2D g2d, Point p,
                          int height, int width, GridConfiguration gridconfig);

    /**
     * Este m�todo es invocado cuando el usuario desee cambiar alg�n par�metro de configuraci�n del grid.
     * Este es el sitio para lanzar la interfaz de usuario de configuracion del grid.
     * @param owner Window
     */
    public void launchConfigureGridGUI(Window owner);

    /** Este m�todo devolver� el n�mero de pixeles que desea que tenga como
     *  tama�o la leyenda horizontal. Idealmente sera la separaci�n entre las
     *  marcas mas distantes del grid.
     */
    public int getLeyendWidth();
    /** Este m�todo devolver� el n�mero de pixeles que desea que tenga como
     *  tama�o la leyenda vertical. Idealmente sera la separaci�n entre las
     *  marcas mas distantes del grid.
     */
    public int getLeyendHeight();

    /**
     * Informaci�n que el grid desea que sea persistida. Es responsabilidad del c�digo cliente persistir esta
     * informaci�n. Habitualmente, se trata de informaci�n de configuraci�n del grid.
     *
     * @return String
     */
    public String getDataToSave();

    /**
     * Informaci�n que se persisti� en la �ltima sesi�n en la cual se emple� este grid.
     *
     * @param data String
     */
    public void setSavedData(String data);
}

