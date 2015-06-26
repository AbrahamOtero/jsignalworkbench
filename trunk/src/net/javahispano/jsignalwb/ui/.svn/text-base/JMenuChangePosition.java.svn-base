/*
 * JMenuChangePosition.java
 *
 * Created on 17 de mayo de 2007, 12:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *
 * @author Roman
 */
public class JMenuChangePosition extends JMenu {

    /** Creates a new instance of JMenuChangePosition */
    public JMenuChangePosition(JSignalMonitor jsm, String signalName) {
        super("Position");
        setMnemonic(KeyEvent.VK_P);
        JMenuItem rbMenuItem = new JMenuItem(new ChangePositionAction(jsm, signalName, ChangePositionAction.UP));
        JMenuItem rbMenuItem2 = new JMenuItem(new ChangePositionAction(jsm, signalName, ChangePositionAction.DOWN));

        int actualPosition = jsm.getChannelPosition(signalName);
        if (actualPosition == -1) {
            setEnabled(false);
        } else {
            if (actualPosition > 0) {
                rbMenuItem.setEnabled(true);
            } else {
                rbMenuItem.setEnabled(false);
            }
            if (actualPosition < jsm.channelsSize() - 1) {
                rbMenuItem2.setEnabled(true);
            } else {
                rbMenuItem2.setEnabled(false);
            }
        }
        add(rbMenuItem);
        add(rbMenuItem2);
    }
}
