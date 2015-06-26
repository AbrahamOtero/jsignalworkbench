/*
 * SaveAsAction.java
 *
 * Created on 23 de mayo de 2007, 20:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Saver;
import net.javahispano.jsignalwb.plugins.framework.PluginLoadException;


/**
 *
 * @author Roman
 */
public class SaveAsAction extends AbstractAction {
    private Component component;
    private JSWBManager jswbManager;
    private final JSWFileChooser chooser;
    /**
     * Creates a new instance of SaveAsAction
     */
    public SaveAsAction(Component component, JSWBManager jswbManager) {
        this.component = component;
        this.jswbManager = jswbManager;
        this.chooser = new JSWFileChooser(jswbManager.getPluginManager());

        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/save.jpg"));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, icon);
        this.putValue(NAME, "Save As");
        this.putValue(SHORT_DESCRIPTION, "Save As...");
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
    }

    public void actionPerformed(ActionEvent e) {
        try {
            //chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooser.showSaveDialog(component);
            File file = chooser.getSelectedFile();
            if (file != null && result == JFileChooser.APPROVE_OPTION) {
                if (chooser.isSaverSelected()) {
                    Saver saver = chooser.getSaverSelected();
                    jswbManager.saveChannelsAs(saver.getName(), file, true);
                }
            }
        } catch (PluginLoadException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
