package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class EraseAllAction extends AbstractAction {
    public EraseAllAction() {
        this.putValue(SHORT_DESCRIPTION, "Remove all the signals emphasis levels, marks and annotations. Be careful...");
        this.putValue(NAME, "Remove all");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/clear.png"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_M);
    }

    public void actionPerformed(ActionEvent e) {
        SignalManager sm = JSWBManager.getSignalManager();
        sm.eraseAllEmphasisLevels();
        sm.removeAllMarks();
        sm.removeAllAnnotations();
        JSWBManager.getJSWBManagerInstance().refreshJSM(true);
    }
}
