/*
 * ZoomAction.java
 *
 * Created on 11 de mayo de 2007, 14:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *
 * @author Roman
 */
public class ZoomAction extends AbstractAction {
    private JSignalMonitor jsm;
    private String signalName;
    private int value;
    /** Creates a new instance of VisibleAction */
    public ZoomAction(JSignalMonitor jsm, String signalName, int value) {
        this.jsm = jsm;
        this.signalName = signalName;
        this.value = value;
        this.putValue(NAME, String.valueOf(value));
        this.putValue(SHORT_DESCRIPTION, "Set the zoom at " + value + " %");

    }

    public void actionPerformed(ActionEvent e) {
        jsm.setVerticalZoom(signalName, value);
        jsm.repaintAll();
    }

}
