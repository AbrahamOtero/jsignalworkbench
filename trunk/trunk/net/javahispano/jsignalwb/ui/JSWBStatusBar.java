/*
 * JSWBStatusBar.java
 *
 * Created on 31 de julio de 2007, 3:32
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;

import javax.swing.*;

import net.javahispano.jsignalwb.SessionInfo;

/**
 *
 * @author Roman Segador
 */
public class JSWBStatusBar extends JPanel {
    private JLabel lastFile;
    private JLabel saved;
    private JLabel loader;
    private JLabel text1;
    private SessionInfo sessionInfo;
    public JSWBStatusBar(SessionInfo sessionInfo) {
        super();
        this.sessionInfo = sessionInfo;
        Font font = new Font(Font.DIALOG, Font.BOLD, 12);
        //Border border=BorderFactory.createLoweredBevelBorder();
        lastFile = new JLabel("  " + sessionInfo.getLastFileOpenedPath() + "  ||");
        if (sessionInfo.isSessionSaved()) {
            saved = new JLabel("   Saved   ||");
        } else {
            saved = new JLabel("   Not Saved   ||");
        }
        loader = new JLabel("   " + sessionInfo.getLastLoaderUsed() + "   ||");
        text1 = new JLabel(" Project:");
        lastFile.setFont(font);
        saved.setFont(font);
        loader.setFont(font);
        text1.setFont(font);
        setBorder(BorderFactory.createLoweredBevelBorder());
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 5, 2);
        setMinimumSize(new Dimension(1, 20));
        setMaximumSize(new Dimension(10000, 20));
        setLayout(fl);
        add(text1);
        add(lastFile);
        add(saved);
        add(loader);
    }

    public void refresh() {
        lastFile.setText("  " + sessionInfo.getLastFileOpenedPath() + "  ||");
        if (sessionInfo.isSessionSaved()) {
            saved.setText("   Saved   ||");
        } else {
            saved.setText("   Not Saved   ||");
        }
        loader.setText("   " + sessionInfo.getLastLoaderUsed() + "   ||");
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                validate();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

    }

}
