/*
 * JMenuActions.java
 *
 * Created on 1 de agosto de 2007, 13:35
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JMenuActions extends JMenu {

    public JMenuActions(JSWBManager jswbManager) {
        super("Actions");
        setMnemonic(KeyEvent.VK_A);
        add(new RemoveAllMarksAction(jswbManager));
        add(new RemoveAllAnnotationsAction(jswbManager));
        add(new EraseAllEmphasisLevelsAction());
        addSeparator();
        add(new EraseAllAction());
        addSeparator();
        add(new JRadioButtonMenuItemXY(jswbManager.getJSignalMonitor()));
        add(new JRadioButtonMenuItemAddMarks(jswbManager.getJSignalMonitor()));
        addSeparator();
        add(new JMenuItem(new AdjustSignalVisibleRangeAction(jswbManager)));
        add(new JMenuItem(new AdjustSignalVisibleRangeAction(0.6666f, jswbManager)));
        add(new JMenuItem(new AdjustSignalVisibleRangeAction(0.5f, jswbManager)));
        addSeparator();
        add(new JRadioButtonMenuItemDebugModeOnRestart());
        add(new JMenuItem(new ShowJSMPropertiesAction(jswbManager, jswbManager.getParentWindow())));
        if (jswbManager.getParentWindow() != null &&
            (jswbManager.getParentWindow() instanceof JFrame)) {
            addSeparator();
            add(new LookAndFeelAction((JFrame) jswbManager.getParentWindow()));
        }
    }

}
