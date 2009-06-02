package net.javahispano.jsignalwb.ui;

import java.awt.event.*;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class JMenuAbout extends JMenu {
    public JMenuAbout() {
        super("About");

        setMnemonic(KeyEvent.VK_U);
        JMenuItem i = new JMenuItem("About");
        i.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                              "<html></head><body><p><font color=\"#FF0000\" size=\"5\">About</font>" +
                                              "</p><p><font " +
                                              "color=\"#0000FF\" size=\"4\">" +
                                              "</font></p><p><font color=\"#0000FF\" size=\"4\"> JSignalWorkbench<br>" +
                                              "Developed by Roman Segador and Abraham Otero.</font></p></body></html>",
                                              "About", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        );

        this.add(i);

    }

}
