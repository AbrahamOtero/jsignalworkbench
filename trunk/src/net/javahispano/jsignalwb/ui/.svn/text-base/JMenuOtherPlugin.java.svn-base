package net.javahispano.jsignalwb.ui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

/**
 *
 * @author roman.segador.torre
 */
public class JMenuOtherPlugin extends JMenu {

    public JMenuOtherPlugin(String pluginType, String pluginName) {
        super(pluginName);
        JMenuItem jmi;
        PluginManager pm = JSWBManager.getPluginManager();
        if (pm.isPluginLoaded(pluginType, pluginName)) {
            Plugin plug = pm.getPlugin(pluginType + ":" + pluginName);
            if (!plug.hasOwnConfigureGUI()) {
                jmi = new JMenuItem("Configure");
                jmi.setEnabled(false);
            } else {
                jmi = new JMenuItem(new OtherPluginsAction(pluginName, pluginType));
            }
        } else {
            jmi = new JMenuItem(new OtherPluginsAction(pluginName, pluginType));
        }
        add(jmi);

    }
}
