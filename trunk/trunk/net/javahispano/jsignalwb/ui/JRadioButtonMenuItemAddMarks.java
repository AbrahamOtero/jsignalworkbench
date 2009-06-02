/*
 * JRadioButtonMenuItemAddMarks.java
 *
 * Created on 1 de agosto de 2007, 13:59
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemAddMarks extends JRadioButtonMenuItem implements JSignalMonitorModeListener {

    public JRadioButtonMenuItemAddMarks(JSignalMonitor jsm) {
        super(new SelectMarksAction(jsm));
        setSelected(jsm.isMarkSelectionMode());
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
        if (e.getMode() == JSignalMonitorModeEvent.MARK_CREATION) {
            setSelected(e.getValue());
        }
    }

}
