/*
 * SignalDataColorAction.java
 *
 * Created on 2 de agosto de 2007, 18:49
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class SignalDataColorAction extends AbstractAction {
    private JSWBManager jswbManager;
    private String signalName;
    public SignalDataColorAction(JSWBManager jswbManager, String signalName) {
        this.jswbManager = jswbManager;
        this.signalName = signalName;
        this.putValue(SHORT_DESCRIPTION, "Select the color of the signal...");
        this.putValue(NAME, "Signal color");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/color.png"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
    }

    public void actionPerformed(ActionEvent e) {
        JColorChooser jColorChooser = new JColorChooser();
        Color color = jColorChooser.showDialog(jswbManager.getParentWindow(),
                                               "Choose color for " + signalName,
                                               jswbManager.getSignalDataColor(signalName));
        if (color != null) {
            jswbManager.setSignalDataColor(signalName, color);
        }
    }


}
