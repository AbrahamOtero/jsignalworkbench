/*
 * SaveAction.java
 *
 * Created on 1 de agosto de 2007, 17:41
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class SaveAction extends AbstractAction {
    private JSWBManager jswbManager;
    public SaveAction(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/save.jpg"));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, icon);
        this.putValue(NAME, "Save file");
        this.putValue(SHORT_DESCRIPTION, "save...");
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }

    public void actionPerformed(ActionEvent e) {
        jswbManager.saveChannels();
    }

}
