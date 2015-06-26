package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorGrid;

/*
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface GridPlugin extends Plugin, JSignalMonitorGrid {

    /**
     * Permite indicarle al plugin sobre que senhales se esta mostrando.
     *
     * @param s Signal
     */
    public void setSignal(Signal s);

}
