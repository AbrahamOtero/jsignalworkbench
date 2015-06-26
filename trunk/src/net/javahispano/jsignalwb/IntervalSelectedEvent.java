/*
 * IntervalSelectedEvent.java
 *
 * Created on 25 de junio de 2007, 12:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb;

/**
 *
 * @author Roman
 */
public class IntervalSelectedEvent {
    private String channelName;
    private long startTime;
    private long endTime;

    /** Creates a new instance of IntervalSelectedEvent */
    public IntervalSelectedEvent(String channelName) {
        this.channelName = channelName;
        this.startTime = -1;
        this.endTime = -1;
    }

    /** genera un evento nuevo. Si alguno de los tiempos no es valido(menos que 0)
     *  se estimara que la seleccion de la senhal es completa*/

    public IntervalSelectedEvent(String channelName, long startTime, long endTime) {
        this.channelName = channelName;
        this.startTime = Math.min(startTime, endTime);
        this.endTime = Math.max(startTime, endTime);
    }

    public String getChannelName() {
        return channelName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isFullSignal() {
        if (startTime < 0 || endTime < 0) {
            return true;
        } else {
            return false;
        }
    }

}
