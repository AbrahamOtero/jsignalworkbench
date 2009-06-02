/*
 * JRadioButtonMenuItemShowEmphasisLevel.java
 *
 * Created on 11 de septiembre de 2007, 19:57
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemShowEmphasisLevel extends JRadioButtonMenuItem {

    public JRadioButtonMenuItemShowEmphasisLevel(String signalName) {
        super(new ShowEmphasisLevelAction(signalName));

        if (JSWBManager.getJSWBManagerInstance().getSignalHasEmphasis(signalName)) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    public String getActionCommand() {
        if (isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }
}
