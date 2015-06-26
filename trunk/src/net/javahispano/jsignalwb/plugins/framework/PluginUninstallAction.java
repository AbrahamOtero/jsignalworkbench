/*
 * PluginUninstallAction.java
 *
 * Created on 14 de septiembre de 2007, 3:28
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class PluginUninstallAction extends AbstractAction {
    private File file;
    private String pluginKey;
    private PluginManagerPanel pmPanel;
    public PluginUninstallAction(File file, String pluginKey, PluginManagerPanel pmPanel) {
        this.file = file;
        this.pluginKey = pluginKey;
        this.pmPanel = pmPanel;
        Image image = Toolkit.getDefaultToolkit().createImage(
                PluginUninstallAction.class.getResource("images/trash.png"));
        Icon smallIcon = new ImageIcon(
                image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(NAME, "");
        this.putValue(SHORT_DESCRIPTION, "Uninstall plugin");
    }

    public void actionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(
                JSWBManager.getJSWBManagerInstance().getParentWindow(),
                "Are you sure?",
                "Uninstall plugin",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (JSWBManager.getJSWBManagerInstance().getPluginManager().unregisterPlugin(
                    pluginKey)) {
                JSWBManager.getJSWBManagerInstance().deletePluginFile(file);
                pmPanel.refreshJTable();

            } else {
                JOptionPane.showMessageDialog(
                        JSWBManager.getJSWBManagerInstance().getParentWindow(),
                        "Unable to delete plugin");
            }
        }
    }
}
