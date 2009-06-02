/*
 * JMenuAlgorithms.java
 *
 * Created on 13 de junio de 2007, 13:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.Plugin.GUIPositions;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

/**
 *
 * @author Roman
 */
public class JMenuPlugins extends JMenu {
    private JSWBManager jswbManager;
    /** Creates a new instance of JMenuAlgorithms */
    public JMenuPlugins(JSWBManager jswbManager) {
        super("Plugins");
        setMnemonic(KeyEvent.VK_P);
        this.jswbManager = jswbManager;
        MenuListener ml = new MenuListenerAdapter() {
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuSelected(evt);
            }
        };

        addMenuListener(ml);
    }

    public void jMenuSelected(MenuEvent e) {
        this.removeAll();
        PluginManager pluginManager = JSWBManager.getPluginManager();
        HashMap<String, ArrayList<String>> plugins = pluginManager.getRegisteredPlugins();
        Set<String> kindPlugins = plugins.keySet();
        List<String> kind = new LinkedList<String>(kindPlugins);
        Collections.sort(kind);
        Iterator<String> it = kind.iterator();
        boolean flag = false;
        while (it.hasNext()) {
            String pluginType = it.next();
            flag = false;
            if (pluginType.equals("algorithm") || pluginType.equals("generic")) {
                JMenu jMenu = new JMenu(pluginType);
                ArrayList<String> plug = plugins.get(pluginType);
                for (String s : plug) {
                    Plugin plugin = pluginManager.getPlugin(pluginType + ":" + s);
                    if (plugin.showInGUIOnthe(GUIPositions.MENU)) {
                        if (pluginType.equals("algorithm")) {
                            jMenu.add(new JMenuAlgorithm(s, jswbManager));
                            flag = true;
                        } else if (pluginType.equals("generic")) {
                            jMenu.add(new JMenuGenericPlugin(s,
                                    jswbManager));
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    add(jMenu);
                }
            }
        }
        addSeparator();
        add(new JMenuItem(new ShowPluginManagerAction(jswbManager, jswbManager.getParentWindow())));
    }

}
