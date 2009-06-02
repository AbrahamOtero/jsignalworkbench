/*
 * ShowXYPointsAction.java
 *
 * Created on 21 de junio de 2007, 14:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *
 * @author Roman
 */
public class ShowXYPointsAction extends AbstractAction {
    JSignalMonitor jsm;
    /** Creates a new instance of ShowXYPointsAction */
    public ShowXYPointsAction(JSignalMonitor jsm) {
        this.putValue(NAME, "XY");
        this.putValue(SHORT_DESCRIPTION, "Show the XY Values of the signals");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/xy.gif"));
        this.putValue(SMALL_ICON, new ImageIcon(image.getScaledInstance(22, 20, Image.SCALE_SMOOTH)));
        this.jsm = jsm;
    }

    public void actionPerformed(ActionEvent e) {
        if ("true".equals(e.getActionCommand())) {
            jsm.setRepresentingXYValues(true);
            jsm.repaintChannels();
        } else if ("false".equals(e.getActionCommand())) {
            jsm.setRepresentingXYValues(false);
            jsm.repaintChannels();
        }
    }

    private Icon generateIcon() {
        BufferedImage bufferedImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setBackground(Color.LIGHT_GRAY);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.clearRect(0, 0, 20, 20);
        g2d.setColor(Color.RED);
        g2d.drawString("X,Y", 2, 14);
        return new ImageIcon(bufferedImage);

    }


}
