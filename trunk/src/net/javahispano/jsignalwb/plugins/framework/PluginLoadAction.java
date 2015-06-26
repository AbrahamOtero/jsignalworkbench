/*
 * PluginLoadAction.java
 *
 * Created on 14 de septiembre de 2007, 1:58
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class PluginLoadAction extends AbstractAction {
    private String pluginKey;
    private PluginManagerPanel pmPanel;
    public PluginLoadAction(String pluginKey, PluginManagerPanel pmPanel) {
        this.pluginKey = pluginKey;
        this.pmPanel = pmPanel;
        Image image = Toolkit.getDefaultToolkit().createImage(
                PluginLoadAction.class.getResource("images/load.jpg"));
        Icon smallIcon = new ImageIcon(
                image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(NAME, "");
        this.putValue(SHORT_DESCRIPTION, "Load plugin");
    }

    public void actionPerformed(ActionEvent e) {
        JSWBManager.getJSWBManagerInstance().getPluginManager().getPlugin(pluginKey);
        if (!JSWBManager.getJSWBManagerInstance().getPluginManager().isPluginLoaded(pluginKey)) {
            JOptionPane.showMessageDialog(JSWBManager.getJSWBManagerInstance().getParentWindow(),
                                          "Ploblems loading the plugin");
        } else {
            pmPanel.refreshJTable();
        }
    }

}
