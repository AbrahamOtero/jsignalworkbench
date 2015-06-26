/*
 * SaveAsAction.java
 *
 * Created on 23 de mayo de 2007, 20:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;


/**
 *
 * @author Roman
 */
public class NewAction extends AbstractAction {
    /**
     * Creates a new instance of SaveAsAction
     */
    public NewAction() {
        Image image = Toolkit.getDefaultToolkit().createImage(JSWBManager.class.getResource("images/new.gif"));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, icon);
        this.putValue(NAME, "New");
        this.putValue(SHORT_DESCRIPTION, "New...");
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
    }

    public void actionPerformed(ActionEvent e) {
        if (JSWBManager.getJSWBManagerInstance().prepareClose(true)) {
            JSWBManager.getSignalManager().removeAllSignals();
            JSWBManager.getIOManager().invalidateFile();
        }
    }

}
