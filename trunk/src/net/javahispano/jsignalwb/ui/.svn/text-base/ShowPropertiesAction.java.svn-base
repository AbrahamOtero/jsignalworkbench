/*
 * ShowPropertiesAction.java
 *
 * Created on 29 de mayo de 2007, 19:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */
public class ShowPropertiesAction extends AbstractAction {
    String signalName;
    JSWBManager jswbManager;
    Window owner;
    /** Creates a new instance of ShowPropertiesAction */
    public ShowPropertiesAction(String signalName, JSWBManager jswbManager, Window owner) {
        this.signalName = signalName;
        this.jswbManager = jswbManager;
        this.owner = owner;
        this.putValue(NAME, String.valueOf("Properties"));
        this.putValue(SHORT_DESCRIPTION, "Show the signal properties configuration window");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_T);
    }

    public void actionPerformed(ActionEvent e) {
        new ConfigureSignalPanel(signalName, jswbManager).showJWindow(owner);

    }

}
