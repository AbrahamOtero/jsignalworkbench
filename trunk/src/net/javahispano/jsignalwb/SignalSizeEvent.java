/*
 * SignalSizeEvent.java
 *
 * Created on 23 de mayo de 2007, 11:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb;

/**
 *
 * @author Roman
 */
public class SignalSizeEvent {
    private Signal s;
    private boolean add;
    /** Creates a new instance of SignalSizeEvent */
    public SignalSizeEvent(Signal s, boolean add) {
        this.s = s;
        this.add = add;
    }

    public boolean isSignalAdded() {
        if (s != null) {
            return add;
        } else {
            return false;
        }
    }

    public boolean isSignalRemoved() {
        if (s != null) {
            return!add;
        } else {
            return false;
        }
    }

    public Signal getSignal() {
        return s;
    }

    public boolean isSignalsReset() {
        if (s == null) {
            return true;
        } else {
            return false;
        }
    }

}
