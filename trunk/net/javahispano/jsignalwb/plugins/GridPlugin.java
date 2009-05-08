/*
 * GridPlugin.java
 *
 * Created on 4 de julio de 2007, 13:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorGrid;
import net.javahispano.jsignalwb.*;

/**
 *
 * @author Roman
 */
public interface GridPlugin extends Plugin,JSignalMonitorGrid{

    /**
     * Permite indicarle al plugin sobre qué señales se está mostrando.
     *
     * @param s Signal
     */
    public void setSignal(Signal s);

}
