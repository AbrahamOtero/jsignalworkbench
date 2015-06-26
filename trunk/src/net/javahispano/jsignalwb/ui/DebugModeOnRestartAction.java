package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author roman.segador.torre
 */
public class DebugModeOnRestartAction extends AbstractAction {

    public DebugModeOnRestartAction() {
        this.putValue(NAME, "Debug mode");
        this.putValue(SHORT_DESCRIPTION, "Debug mode when restart the " +
                      "applicattion turn ON/OFF");
    }


    public void actionPerformed(ActionEvent e) {
        JSWBManager jswbManager = JSWBManager.getJSWBManagerInstance();
        if (e.getActionCommand().toLowerCase().equals("true")) {
            jswbManager.setDebugModeOnRestart(true);
            JOptionPane.showMessageDialog(jswbManager.getParentWindow(),
                                          "Debug mode is ON now");
        } else if (e.getActionCommand().toLowerCase().equals("false")) {
            jswbManager.setDebugModeOnRestart(false);
            JOptionPane.showMessageDialog(jswbManager.getParentWindow(),
                                          "When you restart the application the debug mode will be OFF");
        }
    }


}
