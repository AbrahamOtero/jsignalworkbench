/*
 * AbscissaOffsetAction.java
 *
 * Created on 16 de mayo de 2007, 13:40
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
public class AbscissaOffsetAction extends AbstractAction {
    private JSignalMonitor jsm;
    private String signalName;
    private int value;
    /** Creates a new instance of AbscissaOffsetAction */
    public AbscissaOffsetAction(JSignalMonitor jsm, String signalName, int value) {
        this.jsm = jsm;
        this.signalName = signalName;
        this.value = value;
        this.putValue(NAME, String.valueOf(value) + "%");
    }

    public void actionPerformed(ActionEvent e) {
        //jsm.getChannelProperties(signalName).setAbscissaOffset(value/(float)100);
        jsm.repaintAll();
    }
}
