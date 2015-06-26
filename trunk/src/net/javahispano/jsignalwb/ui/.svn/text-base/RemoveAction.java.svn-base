/*
 * RemoveAction.java
 *
 * Created on 16 de mayo de 2007, 13:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */
public class RemoveAction extends AbstractAction {
    private JSWBManager jswbManager;
    private String signalName;
    /** Creates a new instance of RemoveAction */
    public RemoveAction(JSWBManager jswbManager, String signalName) {
        this.jswbManager = jswbManager;
        this.signalName = signalName;
        this.putValue(NAME, String.valueOf("Remove"));
    }

    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(jswbManager.getParentWindow(), "Are you sure?",
                "Remove signal: " + signalName, JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            //boolean flag=jswbManager.getSignalManager().isSignalVisible(signalName);
            jswbManager.removeSignal(signalName);
        }

    }
}
