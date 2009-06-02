/*
 * JRadioButtonMenuItemXY.java
 *
 * Created on 1 de agosto de 2007, 13:58
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemXY extends JRadioButtonMenuItem implements JSignalMonitorModeListener {

    public JRadioButtonMenuItemXY(JSignalMonitor jsm) {
        super(new ShowXYPointsAction(jsm));
        setMnemonic(KeyEvent.VK_X);
        setSelected(jsm.isRepresentingXYValues());
        jsm.addModeListener(this);
    }

    public String getActionCommand() {
        if (isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public void jSignalMonitorModeActionPerformed(JSignalMonitorModeEvent e) {
        if (e.getMode() == JSignalMonitorModeEvent.REPRESENT_XY_VALUES) {
            setSelected(e.getValue());
        }
    }
}
