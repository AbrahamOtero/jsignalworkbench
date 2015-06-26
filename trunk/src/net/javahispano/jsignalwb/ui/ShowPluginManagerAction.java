/*
 * ShowPluginManagerAction.java
 *
 * Created on 23 de julio de 2007, 13:55
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.framework.PluginManagerPanel;

/**
 *
 * @author Roman Segador
 */
public class ShowPluginManagerAction extends AbstractAction {
    private JSWBManager jswbManager;
    private Window owner;
    public ShowPluginManagerAction(JSWBManager jswbManager, Window owner) {
        this.jswbManager = jswbManager;
        this.owner = owner;
        this.putValue(NAME, String.valueOf("Plugin Manager"));
    }

    public void actionPerformed(ActionEvent e) {
        new PluginManagerPanel(jswbManager).showJWindow(owner);
    }

}
