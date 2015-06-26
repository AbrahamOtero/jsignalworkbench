/*
 * JMenuGrids.java
 *
 * Created on 7 de agosto de 2007, 2:04
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenu;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JMenuGrids extends JMenu {

    public JMenuGrids(String signalName, JSWBManager jswbManager) {
        super("Change Grid");
        setMnemonic(KeyEvent.VK_G);
        List<String> grids = jswbManager.getAvailableKindsOfGrids();
        if (grids != null) {
            for (String grid : grids) {
                add(new SetGridAction(signalName, grid, jswbManager));
            }
        } else {
            setEnabled(false);
        }
    }

}
