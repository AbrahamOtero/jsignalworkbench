/*
 * JRadioButtonMenuItemSetSignalImaginary.java
 *
 * Created on 11 de octubre de 2007, 13:16
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemSetSignalImaginary extends JRadioButtonMenuItem {

    public JRadioButtonMenuItemSetSignalImaginary(String signalName) {
        super(new SetSignalImaginaryAction(signalName));

        if (JSWBManager.getJSWBManagerInstance().getSignalManager().getSignal(signalName).isImaginary()) {
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
