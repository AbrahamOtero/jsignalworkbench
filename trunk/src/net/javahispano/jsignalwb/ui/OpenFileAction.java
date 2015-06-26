/*
 * OpenFileAction.java
 *
 * Created on 17 de mayo de 2007, 13:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;

import net.javahispano.jsignalwb.EnvironmentConfiguration;
import net.javahispano.jsignalwb.JSWBManager;

/**
 * @author Roman
 */
public class OpenFileAction extends AbstractAction {
    JSWBManager jswbManager;
    JSWFileChooser chooser;

    /**
     * Creates a new instance of OpenFileAction
     * @param jswbManager JSWBManager
     */
    public OpenFileAction(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        chooser = new JSWFileChooser(JSWBManager.getPluginManager());
        this.putValue(NAME, "Open file");
        //this.putValue(MNEMONIC_KEY,KeyEvent.VK_O);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/load.jpg"));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, icon);
        this.putValue(SHORT_DESCRIPTION, "Load...");
        chooser.setCurrentDirectory(new File(EnvironmentConfiguration.getInstancia().getPathJFFicheros()));
    }

    public void actionPerformed(ActionEvent e) {

        int result = chooser.showOpenDialog(JSWBManager.getParentWindow());
        File file = chooser.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION) {
            if (file != null) {
                loadData(file);
                EnvironmentConfiguration.getInstancia().setPathJFFicheros(
                        chooser.getCurrentDirectory().getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                              "The selected file is not a valid JSW project");
            }

            EnvironmentConfiguration.getInstancia().setDefaultLoader(chooser.getLoaderSelected().getName());

        }
    }

    protected void loadData(File file) {
        this.jswbManager.setDeleteSignalsInNextLoad(true);
        jswbManager.loadChannels(chooser.getLoaderSelected().getName(), file);
    }
}
