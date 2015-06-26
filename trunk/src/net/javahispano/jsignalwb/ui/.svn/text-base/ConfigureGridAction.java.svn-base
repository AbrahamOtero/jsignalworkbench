/*
 * ConfigureGridAction.java
 *
 * Created on 6 de agosto de 2007, 18:40
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorGrid;

/**
 *
 * @author Roman Segador
 */
public class ConfigureGridAction extends AbstractAction {
    private JSignalMonitorGrid grid;
    private Window owner;
    public ConfigureGridAction(JSignalMonitorGrid grid, Window owner) {
        this.grid = grid;
        this.owner = owner;
        this.putValue(SHORT_DESCRIPTION, "Launch the grid configuration");
        this.putValue(NAME, "Configure grid");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/adjust.png"));
        Icon smallIcon = new ImageIcon(image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    }

    public void actionPerformed(ActionEvent e) {
        grid.launchConfigureGridGUI(owner);
    }

}
