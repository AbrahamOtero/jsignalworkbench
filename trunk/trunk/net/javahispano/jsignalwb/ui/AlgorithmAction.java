/*
 * AlgorithmAction.java
 *
 * Created on 13 de junio de 2007, 12:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */
public class AlgorithmAction extends AbstractAction {
    public final static int CONFIGURE_ACTION = 1;
    public final static int RUN_ACTION = 2;
    public final static int RESULTS_ACTION = 3;
    private int action;
    private String algorithmName;
    private JSWBManager jswbm;
    /** Creates a new instance of AlgorithmAction */
    public AlgorithmAction(String algorithmName, int algorithmAction,
                           JSWBManager jswbManager) {
        this.action = algorithmAction;
        this.algorithmName = algorithmName;
        this.jswbm = jswbManager;
        this.putValue(SHORT_DESCRIPTION, algorithmName);
        if (action == CONFIGURE_ACTION) {
            this.putValue(NAME, "Configure");
            this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        } else if (action == RUN_ACTION) {
            this.putValue(NAME, "Run");
            Icon smallIcon = jswbManager.getPluginManager().getIconDefaultSize("algorithm", algorithmName);
            Icon icon = jswbManager.getPluginManager().getIconDefaultSize("algorithm", algorithmName);
            this.putValue(SMALL_ICON, smallIcon);
            this.putValue(LARGE_ICON_KEY, icon);
            this.putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        } else if (action == RESULTS_ACTION) {
            this.putValue(NAME, "Show results");
            this.putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (jswbm.getPluginManager().isPluginRegistered("algorithm", algorithmName)) {
            if (action == CONFIGURE_ACTION) {
                jswbm.showPluginConfiguration("algorithm", algorithmName);
            } else if (action == RUN_ACTION) {
                jswbm.showPluginExecution("algorithm", algorithmName);
            } else if (action == RESULTS_ACTION) {
                jswbm.showAlgorithmResults(algorithmName);
            }
        } else {
            JOptionPane.showMessageDialog(jswbm.getParentWindow(), "This algorithm is already uninstalled." +
                                          " Should be restart the application");
        }
    }

    public String getAlgorithmName() {
        return algorithmName;
    }
}
