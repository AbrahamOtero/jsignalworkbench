/*
 * JRadioButtonXY.java
 *
 * Created on 21 de junio de 2007, 15:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JToggleButton;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman
 */
public class JToggleButtonXY extends JToggleButton implements JSignalMonitorModeListener {

    /**
     * Creates a new instance of JRadioButtonXY
     */
    public JToggleButtonXY(JSignalMonitor jsm) {
        super(new ShowXYPointsAction(jsm));
        setFocusable(false);
        setText("");
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
