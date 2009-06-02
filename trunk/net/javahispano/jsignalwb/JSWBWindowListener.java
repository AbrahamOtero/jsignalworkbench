/*
 * JSWBWindowListener.java
 *
 * Created on 30 de julio de 2007, 13:48
 */

package net.javahispano.jsignalwb;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Roman Segador
 */
public class JSWBWindowListener extends WindowAdapter {
    private JSWBManager jswbManager;
    public JSWBWindowListener(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
    }

    public void windowClosing(WindowEvent e) {
        if (jswbManager.prepareClose(false)) {
            System.exit(0);
        }
    }
}
