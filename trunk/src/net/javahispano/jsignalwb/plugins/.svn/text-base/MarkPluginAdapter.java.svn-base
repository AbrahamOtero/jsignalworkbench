/*
 * MarkPluginAdapter.java
 *
 * Created on 4 de julio de 2007, 15:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.*;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;

/**
 *
 * @author Roman
 */
public abstract class MarkPluginAdapter extends PluginAdapter implements MarkPlugin {
    protected Signal signal = null;
    public boolean isInterval() {
        return false;
    }

    public long getEndTime() {
        throw new UnsupportedOperationException("Try to access a mark end time in a instant mark");
    }

    public void setEndTime(long endTime) {
        throw new UnsupportedOperationException("Try to set a mark end time in a instant mark");
    }

    public void showMarkInfo(Window owner) {
        JOptionPane.showMessageDialog(owner, TimeRepresentation.timeToString(getMarkTime()));
    }

    public String getToolTipText() {
        return getName();
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Signal getSignal() {
        return signal;
    }

    public boolean isOwnPainted() {
        return false;
    }

    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        throw new UnsupportedOperationException("Try to paint a mark that isn't ownPainted. " +
                                                "Set isOwnPainted false or override the paint method");
    }

    public Image getImage() {
        throw new UnsupportedOperationException("Try to get the image of a mark that is ownPaintd. " +
                                                "Set isOwnPainted true or override the getImage method.");
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

}
