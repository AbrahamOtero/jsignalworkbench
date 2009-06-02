/*
 * ChangePositionAction.java
 *
 * Created on 17 de mayo de 2007, 12:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *
 * @author Roman
 */
public class ChangePositionAction extends AbstractAction {
    public static final String UP = "up";
    public static final String DOWN = "down";
    private JSignalMonitor jsm;
    private String signalName;
    private String value;

    /** Creates a new instance of ChangePositionAction */
    public ChangePositionAction(JSignalMonitor jsm, String signalName, String value) {
        this.jsm = jsm;
        this.signalName = signalName;
        this.value = value;
        this.putValue(NAME, String.valueOf(value));
        if (value.equals(UP)) {
            this.putValue(MNEMONIC_KEY, KeyEvent.VK_U);
        } else if (value.equals(DOWN)) {
            this.putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String signalName2;
        if ("up".equals(value.toLowerCase())) {
            signalName2 = jsm.getChannelName(jsm.getChannelPosition(signalName) - 1);
        } else {
            signalName2 = jsm.getChannelName(jsm.getChannelPosition(signalName) + 1);
        }
        jsm.swapChannelsPositions(signalName, signalName2);
        jsm.repaintAll();
    }
}
