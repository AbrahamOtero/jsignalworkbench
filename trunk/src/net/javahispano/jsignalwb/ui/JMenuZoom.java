/*
 * JMenuZoom.java
 *
 * Created on 11 de mayo de 2007, 14:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *
 * @author Roman
 */
public class JMenuZoom extends JMenu {

    /** Creates a new instance of JMenuZoom */
    public JMenuZoom(JSignalMonitor jsm, String signalName) {
        super("Zoom");
        setMnemonic(KeyEvent.VK_Z);
        if (jsm.hasChannel(signalName)) {
            setEnabled(true);
            int actualZoom = (int) (jsm.getChannelProperties(signalName).getZoom() * 100);
            ButtonGroup itemGroup = new ButtonGroup();
            for (int index = 25; index < 251; index += 25) {
                JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(new ZoomAction(jsm, signalName, index));
                itemGroup.add(jrbmi);
                add(jrbmi);
                if (index == actualZoom) {
                    jrbmi.setSelected(true);
                }
            }
        } else {
            setEnabled(false);
        }
    }

}
