package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author roman.segador.torre
 */
public class JRadioButtonMenuItemDebugModeOnRestart extends JRadioButtonMenuItem {

    public JRadioButtonMenuItemDebugModeOnRestart() {
        super(new DebugModeOnRestartAction());
        if (JSWBManager.getJSWBManagerInstance().isDebugModeOnRestart()) {
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
