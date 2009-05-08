package net.javahispano.jsignalwb.plugins;

import javax.swing.Icon;
import net.javahispano.jsignalwb.JSWBManager;
import java.awt.Window;
import java.awt.Graphics2D;
import java.awt.Point;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import java.io.File;
import net.javahispano.jsignalwb.Signal;

public abstract class GridPluginAdapter extends PluginAdapter implements GridPlugin {
    protected Signal signal;
    public GridPluginAdapter() {
    }

    public void setSignal(Signal s) {
        signal = s;
    }
    public void launchConfigureGridGUI(Window owner) {
    }
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }
    public int getLeyendHeight() {
         return 0;
     }

     public int getLeyendWidth() {
         return 0;
    }

}
