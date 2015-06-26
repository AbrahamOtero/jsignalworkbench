/*
 * JMenuFile.java
 *
 * Created on 13 de junio de 2007, 16:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */
public class JMenuFile extends JMenu {

    /** Creates a new instance of JMenuFile */
    public JMenuFile() {
        super("File");
        JSWBManager jswbManager = JSWBManager.getJSWBManagerInstance();
        setMnemonic(KeyEvent.VK_F);
        add(new JMenuItem(new NewAction()));
        addSeparator();
        add(new JMenuItem(new OpenFileAction(jswbManager)));
        add(new JMenuItem(new OpenFileAndAddSignalsAction(jswbManager)));
        addSeparator();
        add(new JMenuItem(new SaveAction(jswbManager)));
        add(new JMenuItem(new SaveAsAction(jswbManager.getParentWindow(), jswbManager)));
        addSeparator();
        add(new JMenuItem(new PrintAction()));
        add(new JMenuItem(new ExitAction()));
    }

}
