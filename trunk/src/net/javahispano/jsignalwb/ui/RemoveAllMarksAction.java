/*
 * RemoveAllMarksAction.java
 *
 * Created on 31 de julio de 2007, 20:01
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class RemoveAllMarksAction extends AbstractAction {
    JSWBManager jswbManager;
    public RemoveAllMarksAction(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        this.putValue(SHORT_DESCRIPTION, "Remove all the marks on all the signals. Be careful...");
        this.putValue(NAME, "Remove all marks");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/clear.png"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_M);
    }

    public void actionPerformed(ActionEvent e) {
        jswbManager.removeAllMarks();
        jswbManager.refreshJSM(true);
    }

}
