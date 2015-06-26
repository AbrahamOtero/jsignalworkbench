/*
 * ExitAction.java
 *
 * Created on 5 de septiembre de 2007, 5:40
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import net.javahispano.jsignalwb.EnvironmentConfiguration;
import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class ExitAction extends AbstractAction {

    public ExitAction() {
        this.putValue(NAME, "Exit");
        this.putValue(SHORT_DESCRIPTION, "Leave the application...");
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    public void actionPerformed(ActionEvent e) {
        if (JSWBManager.getJSWBManagerInstance().prepareClose(false)) {
            EnvironmentConfiguration.getInstancia().almacenaADisco();
            System.exit(0);
        }
    }
}
