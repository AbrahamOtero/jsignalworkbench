/*
 * InvadeNearChannelsAction.java
 *
 * Created on 30 de agosto de 2007, 7:54
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.ChannelProperties;

/**
 *
 * @author Roman Segador
 */
public class InvadeNearChannelsAction extends AbstractAction {
    private JSWBManager jswbManager;
    private ChannelProperties channelProperties;

    public InvadeNearChannelsAction(JSWBManager jswbManager, ChannelProperties channelProperties) {
        this.channelProperties = channelProperties;
        this.jswbManager = jswbManager;
        this.putValue(this.NAME, "InvadeNearChannels");
        this.putValue(this.MNEMONIC_KEY, KeyEvent.VK_I);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().toLowerCase().equals("true")) {
            channelProperties.setInvadeNearChannels(true);
            jswbManager.refreshJSM(false);
        } else if (e.getActionCommand().toLowerCase().equals("false")) {
            channelProperties.setInvadeNearChannels(false);
            jswbManager.refreshJSM(false);
        }
    }

}
