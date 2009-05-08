/*
 * MarkPluginAdapter.java
 *
 * Created on 4 de julio de 2007, 15:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;
import javax.swing.JOptionPane;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import net.javahispano.jsignalwb.Signal;

/**
 *
 * @author Roman
 */
public abstract class MarkPluginAdapter extends PluginAdapter implements MarkPlugin {
    Signal signal=null;
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

    public void setJSWBManager(JSWBManager jswbManager) {
    }

    public void setSignal(Signal signal){
        System.out.println("ooooooooooooooooooo"+signal);
        this.signal=signal;
    }

    public Signal getSignal(){
        System.out.println("aaa "+signal);
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
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }

}
