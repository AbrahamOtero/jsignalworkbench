/*
 * SynchronizedElements.java
 *
 * Created on 27 de septiembre de 2007, 14:00
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.Rectangle;
import java.util.*;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;

/**
 *
 * @author Roman Segador
 */
class SynchronizedElements {
    private Map<String, Channel> channels; //Almacena los canales identificandolos mediante su nombre
    private Map<JSignalMonitorMark, Rectangle> marks;
    private List<String> positions; //Almacena el nombre de los canales en el orden que se mostraran
    public SynchronizedElements() {
        channels = new HashMap<String, Channel>();
        positions = new ArrayList<String>();
        marks = new HashMap<JSignalMonitorMark, Rectangle>();
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public Map<JSignalMonitorMark, Rectangle> getMarks() {
        return marks;
    }

    public List<String> getPositions() {
        return positions;
    }

}
