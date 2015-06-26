/*
 * AdjustSignalVisibleRangeAction.java
 *
 * Created on 28 de junio de 2007, 19:32
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
public class AdjustSignalVisibleRangeAction extends AbstractAction {
    private JSWBManager jswbManager;
    private String channelName;
    private float range;
    /** Creates a new instance of AdjustSignalVisibleRangeAction */
    public AdjustSignalVisibleRangeAction(JSWBManager jswbManager) {
        this(1, jswbManager);
    }

    public AdjustSignalVisibleRangeAction(float range, JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        this.range = range;
        this.putValue(SHORT_DESCRIPTION, "Adjust the zoom to make all the signals full visible");
        this.putValue(NAME, String.format("x%2.1f", (1 / range)));
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/adjust.png"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
    }

    public AdjustSignalVisibleRangeAction(String channelName, JSWBManager jswbManager) {
        this(channelName, 1, jswbManager);
    }

    public AdjustSignalVisibleRangeAction(String channelName, float range, JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        this.channelName = channelName;
        this.range = range;
        this.putValue(SHORT_DESCRIPTION, "Adjust the zoom to make the signal full visible");
        this.putValue(NAME, String.format("x%2.1f", (1 / range)));
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/adjust.png"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
    }

    public void actionPerformed(ActionEvent e) {
        if (channelName == null) {
            jswbManager.adjustVisibleRange(range);
        } else {
            jswbManager.adjustVisibleRange(channelName, range);
        }

        jswbManager.refreshJSM(false);
    }


}
