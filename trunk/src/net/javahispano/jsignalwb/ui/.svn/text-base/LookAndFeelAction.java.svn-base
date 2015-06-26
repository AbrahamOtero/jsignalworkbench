/*
 * LookAndFeelAction.java
 *
 * Created on 13 de junio de 2007, 18:36
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
public class LookAndFeelAction extends AbstractAction {
    private JFrame jFrame;
    private boolean enabled;
    /** Creates a new instance of LookAndFeelAction */
    public LookAndFeelAction(JFrame jFrame) {

        this.jFrame = jFrame;
        this.putValue(SHORT_DESCRIPTION, "Change look&feel");
        this.putValue(NAME, "Change look&feel");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/lookAndFeel.jpg"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        enabled = true;

    }

    public void actionPerformed(ActionEvent e) {

        LooKAndFeelDialog lfd = new LooKAndFeelDialog(jFrame);
        lfd.setVisible(true);

    }
}
