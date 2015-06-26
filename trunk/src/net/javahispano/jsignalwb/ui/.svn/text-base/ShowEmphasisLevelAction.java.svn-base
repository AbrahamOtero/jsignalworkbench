/*
 * ShowEmphasisLevelAction.java
 *
 * Created on 11 de septiembre de 2007, 19:47
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class ShowEmphasisLevelAction extends AbstractAction {
    private String signalName;
    public ShowEmphasisLevelAction(String signalName) {
        this.signalName = signalName;
        this.putValue(this.NAME, "Show Emphasis");
        this.putValue(SHORT_DESCRIPTION, "Make visible(or not) the emphasis level" +
                      " of the signal");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_H);
    }

    public void actionPerformed(ActionEvent e) {
        JSWBManager jSWBManager = JSWBManager.getJSWBManagerInstance();
        if (e.getActionCommand().toLowerCase().equals("true")) {
            if (jSWBManager.setSignalHasEmphasis(signalName, true)) {
                jSWBManager.refreshJSM(true);
            } else {
                JOptionPane.showMessageDialog(JSWBManager.getJSWBManagerInstance().getParentWindow(),
                                              "The signal " + signalName + " hasn't emphasis info");
            }
        } else if (e.getActionCommand().toLowerCase().equals("false")) {
            jSWBManager.setSignalHasEmphasis(signalName, false);
            jSWBManager.refreshJSM(true);
        }
    }

}
