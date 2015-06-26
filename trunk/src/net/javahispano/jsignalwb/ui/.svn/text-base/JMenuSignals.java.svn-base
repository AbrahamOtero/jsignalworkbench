/*
 * JMenuSignals.java
 *
 * Created on 17 de mayo de 2007, 13:10
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


/**
 *
 * @author Roman
 */
public class JMenuSignals extends JMenu {
    JSWBManager jswbManager;
    /** Creates a new instance of JMenuSignals */
    public JMenuSignals(JSWBManager jswbManager) {
        super("Signals");
        setMnemonic(KeyEvent.VK_S);
        this.jswbManager = jswbManager;
        MenuListener ml = new MenuListenerAdapter() {
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuSelected(evt);
            }
        };
        addMenuListener(ml);
    }

    public void jMenuSelected(MenuEvent e) {
        List<String>
                signalNames = new ArrayList<String>(jswbManager.getSignalManager().getSignalsNames());
        removeAll();
        if (signalNames.size() == 0) {
            JMenuItem jmi = new JMenuItem("No signals Loaded");
            jmi.setEnabled(false);
            add(jmi);
        } else {
            Collections.sort(signalNames);
            for (String name : signalNames) {
                add(new JMenuSignal(jswbManager, name));
            }
        }
    }

}
