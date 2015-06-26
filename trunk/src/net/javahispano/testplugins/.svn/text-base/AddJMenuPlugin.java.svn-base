/*
 * AddJMenuItem.java
 *
 * Created on 2 de octubre de 2007, 20:14
 */

package net.javahispano.testplugins;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class AddJMenuPlugin extends GenericPluginAdapter {

    public AddJMenuPlugin() {
    }

    public String getName() {
        return "AddJMenuPlugin";
    }

    public void launch(JSWBManager jswbManager) {
        JMenu menu = new JMenu("Menu de prueba");
        menu.add(new JMenuItem("menu de prueba"));
        jswbManager.addJMenuBarItem(menu);
    }

}
