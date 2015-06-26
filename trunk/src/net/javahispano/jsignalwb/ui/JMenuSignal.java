/*
 * SignalAction.java
 *
 * Created on 17 de mayo de 2007, 13:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;

/**
 *
 * @author Roman
 */
public class JMenuSignal extends JMenu {
    Signal signal;
    /** Creates a new instance of SignalAction */
    public JMenuSignal(JSWBManager jswbManager, String signalName) {
        this(jswbManager, signalName, true, true, true, true, true, true, true, true, true, true, true);
    }

    public JMenuSignal(JSWBManager jswbManager, String signalName,
                       boolean properties, boolean visible, boolean imaginary, boolean showEmphasis, boolean zoom,
                       boolean position,
                       boolean color, boolean remove, boolean invade, boolean grid, boolean adjust) {
        super(signalName);
        this.signal = jswbManager.getSignalManager().getSignal(signalName);
        if (visible) {
            add(new JRadioButtonMenuItemVisible(jswbManager, signalName));
        }
        if (imaginary) {
            add(new JRadioButtonMenuItemSetSignalImaginary(signalName));
        }
        if (showEmphasis) {
            add(new JRadioButtonMenuItemShowEmphasisLevel(signalName));
        }
        if (visible || showEmphasis || imaginary) {
            addSeparator();
        }
        if (color) {
            add(new SignalDataColorAction(jswbManager, signalName));
            addSeparator();
        }
        if (zoom) {
            add(new JMenuZoom(jswbManager.getJSignalMonitor(), signalName));
        }
        if (position) {
            add(new JMenuChangePosition(jswbManager.getJSignalMonitor(), signalName));
        }
        if (invade) {
            add(new JRadioButtonMenuItemInvadeNearChannels(jswbManager, signal.getProperties()));
        }
        if (grid) {
            addSeparator();
            if (jswbManager.getSignalManager().isSignalVisible(signalName)) {
                add(new ConfigureGridAction(jswbManager.getJSignalMonitor().getChannelGrid(signalName),
                                            jswbManager.getParentWindow()));
                add(new JMenuGrids(signalName, jswbManager));
            }
        }

        if (adjust) {
            addSeparator();
            add(new JMenuItem(new AdjustSignalVisibleRangeAction(signalName, jswbManager)));
            add(new JMenuItem(new AdjustSignalVisibleRangeAction(signalName, 0.666f, jswbManager)));
            add(new JMenuItem(new AdjustSignalVisibleRangeAction(signalName, 0.5f, jswbManager)));
        }
        if (remove) {
            addSeparator();
            add(new JMenuItem(new RemoveAction(jswbManager, signalName)));
        }

        if (properties) {
            addSeparator();
            add(new JMenuItem(new ShowPropertiesAction(signalName, jswbManager, jswbManager.getParentWindow())));
        }
    }
}
