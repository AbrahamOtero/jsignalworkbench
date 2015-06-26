/*
 * showJSMPropertiesAction.java
 *
 * Created on 14 de junio de 2007, 18:18
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
public class ShowJSMPropertiesAction extends AbstractAction {

    JSWBManager jswbManager;
    Window owner;

    /** Creates a new instance of ShowPropertiesAction */
    public ShowJSMPropertiesAction(JSWBManager jswbManager, Window owner) {
        this.jswbManager = jswbManager;
        this.owner = owner;
        this.putValue(NAME, String.valueOf("Configure"));
        this.putValue(SHORT_DESCRIPTION, "Show the application properties");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    }

    public void actionPerformed(ActionEvent e) {
        new ConfigureJSM(jswbManager).showJWindow(owner);

    }

}
