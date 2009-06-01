package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorGrid;
import net.javahispano.jsignalwb.*;

/*
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public interface GridPlugin extends Plugin,JSignalMonitorGrid{

    /**
     * Permite indicarle al plugin sobre qué señales se está mostrando.
     *
     * @param s Signal
     */
    public void setSignal(Signal s);

}
