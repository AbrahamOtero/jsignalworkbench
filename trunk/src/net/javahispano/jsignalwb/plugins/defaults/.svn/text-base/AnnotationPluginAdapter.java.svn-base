/*
 * AnnotationPluginAdapter.java
 *
 * Created on 17 de julio de 2007, 19:25
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.plugins.*;

/**
 *
 * @author Roman Segador
 */
public abstract class AnnotationPluginAdapter extends PluginAdapter implements AnnotationPlugin {
    public boolean isInterval() {
        return false;
    }

    public long getEndTime() {
        throw new UnsupportedOperationException("Try to access a mark end time in a instant Annotation");
    }

    public void setEndTime(long endTime) {
        throw new UnsupportedOperationException("Try to set a mark end time in a instant Annotation");
    }

    public void showMarkInfo(Window owner) {
        JOptionPane.showMessageDialog(owner, TimeRepresentation.timeToString(getAnnotationTime()));
    }

    public String getToolTipText() {
        return getName();
    }

    public String getCategory() {
        return "Unknown";
    }

    public void setJSWBManager(JSWBManager jswbManager) {
    }

    public boolean isOwnPainted() {
        return false;
    }

    public void paint(Graphics2D g2d, Point p, int height, int widht) {
        throw new UnsupportedOperationException("Try to paint a mark that isn't ownPainted. " +
                                                "Set isOwnPainted false or override the paint method");
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions.equals(GUIPositions.MENU)) {
            return true;
        }
        return false;
    }

}
