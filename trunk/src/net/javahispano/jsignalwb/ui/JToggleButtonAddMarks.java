/*
 * JRadioButtonAddMarks.java
 *
 * Created on 5 de julio de 2007, 18:11
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JToggleButton;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman Segador
 */
public class JToggleButtonAddMarks extends JToggleButton implements JSignalMonitorModeListener {

    public JToggleButtonAddMarks(JSignalMonitor jsm) {
        super(new SelectMarksAction(jsm));
        setFocusable(false);
        this.setText("");
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
