/*
 * SetGridAction.java
 *
 * Created on 7 de agosto de 2007, 1:51
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class SetGridAction extends AbstractAction {
    private JSWBManager jswbManager;
    private String signalName;
    private String gridName;
    public SetGridAction(String signalName, String gridName, JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        this.signalName = signalName;
        this.gridName = gridName;
        this.putValue(NAME, gridName);
        Icon smallIcon = jswbManager.getPluginManager().getIconDefaultSize("grid", gridName);
        Icon icon = jswbManager.getPluginManager().getIconDefaultSize("grid", gridName);
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        this.putValue(SHORT_DESCRIPTION, "Set the " + gridName + " grid to signal: " + signalName);
    }

    public void actionPerformed(ActionEvent e) {
        jswbManager.setSignalGrid(signalName, gridName);
    }

}
