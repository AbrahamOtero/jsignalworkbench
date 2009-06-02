/*
 * JRadioButtonMenuItemInvadeNearChannels.java
 *
 * Created on 30 de agosto de 2007, 7:47
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.ChannelProperties;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemInvadeNearChannels extends JRadioButtonMenuItem {

    public JRadioButtonMenuItemInvadeNearChannels(JSWBManager jswbManager, ChannelProperties channelProperties) {
        super(new InvadeNearChannelsAction(jswbManager, channelProperties));

        if (channelProperties.isInvadeNearChannels()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
        if (!jswbManager.getSignalManager().isSignalVisible(channelProperties.getName())) {
            setEnabled(false);
        }
    }

    public String getActionCommand() {
        if (isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

}
