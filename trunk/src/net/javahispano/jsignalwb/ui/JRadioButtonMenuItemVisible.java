/*
 * JMenuVisible.java
 *
 * Created on 11 de mayo de 2007, 13:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */

public class JRadioButtonMenuItemVisible extends JRadioButtonMenuItem {

    /** Creates a new instance of JMenuVisible */
    public JRadioButtonMenuItemVisible(JSWBManager jswbManager, String signalName) {
        super(new VisibleAction(jswbManager, signalName));

        if (jswbManager.getSignalManager().getSignal(signalName).getProperties().
            isVisible()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    public String getActionCommand() {
        if (isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

}
