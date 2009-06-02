package net.javahispano.jsignalwb.jsignalmonitor;

/**
 * Evento que se genera a cambiar el valor del scroll de JSignalMonitor.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
class JSignalMonitorScrollEvent {
    private long scrollValue;
    private long oldScrollValue;
    /** Creates a new instance of ScrollValueChangeEvent */
    JSignalMonitorScrollEvent(long scrollValue, long oldScrollValue) {
        this.scrollValue = scrollValue;
        this.oldScrollValue = oldScrollValue;
    }

    /**
     * Devuelve el instante del tiempo, medido en milisegundos desde 00:00:00
     * 01/01/1970, al que esta apuntando ahora el de scroll.
     *
     * @return long
     */
    public long getScrollValue() {
        return scrollValue;
    }

    /**
     * Devuelve el instante de tiempo,medido en milisegundos transcurridos desde
     * 00:00:00 01/01/1970, al cual estaba apuntando en le scroll antes de
     * generarse el evento.
     *
     * @return long
     */
    public long getOldScrollValue() {
        return oldScrollValue;
    }

}
