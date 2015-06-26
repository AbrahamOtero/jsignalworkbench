package net.javahispano.jsignalwb.ui;

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


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.printsupport.PrintDialog;

/**
 *
 * @author Roman Segador
 */
public class PrintAction extends AbstractAction {
    public PrintAction() {
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/print.gif"));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, icon);
        this.putValue(NAME, "Print");
        this.putValue(SHORT_DESCRIPTION, "Print...");
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
    }

    public void actionPerformed(ActionEvent e) {

        (new PrintDialog(JSWBManager.getParentWindow(), "PrintDialog", JSWBManager.getJSignalMonitor())).setVisible(true);
    }

}
