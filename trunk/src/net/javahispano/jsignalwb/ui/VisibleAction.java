/*
 * VisibleAction.java
 *
 * Created on 11 de mayo de 2007, 13:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */
public class VisibleAction extends AbstractAction {
    private JSWBManager jswbManager;
    private String signalName;
    /** Creates a new instance of VisibleAction */
    public VisibleAction(JSWBManager jswbManager, String signalName) {
        this.signalName = signalName;
        this.jswbManager = jswbManager;
        this.putValue(this.NAME, "Visible");
        this.putValue(SHORT_DESCRIPTION, "Make visible(or not) the signal");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_V);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().toLowerCase().equals("true")) {
            jswbManager.setChannelVisible(signalName, true);
            jswbManager.refreshJSM(false);
        } else if (e.getActionCommand().toLowerCase().equals("false")) {
            jswbManager.setChannelVisible(signalName, false);
            jswbManager.refreshJSM(false);
        }
    }


}
