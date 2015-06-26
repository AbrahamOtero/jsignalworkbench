/*
 * SelectMarksAction.java
 *
 * Created on 5 de julio de 2007, 18:07
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *
 * @author Roman Segador
 */
public class SelectMarksAction extends AbstractAction {
    JSignalMonitor jsm;

    public SelectMarksAction(JSignalMonitor jsm) {
        this.putValue(NAME, "Add Marks");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        this.putValue(SHORT_DESCRIPTION, "Set create marks mode...click on signal to add one...");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/marks.png"));
        this.putValue(SMALL_ICON, new ImageIcon(image.getScaledInstance(33, 20, Image.SCALE_SMOOTH)));
        this.jsm = jsm;
    }

    public void actionPerformed(ActionEvent e) {
        if ("true".equals(e.getActionCommand())) {
            jsm.setMarksSelectionMode(true);

        } else if ("false".equals(e.getActionCommand())) {
            jsm.setMarksSelectionMode(false);
        }
    }

}
