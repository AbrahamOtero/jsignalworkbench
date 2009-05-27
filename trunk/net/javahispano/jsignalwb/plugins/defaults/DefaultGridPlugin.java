/*
 * DefaultGridPlugin.java
 *
 * Created on 7 de agosto de 2007, 1:10
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorGrid;
import net.javahispano.jsignalwb.plugins.*;

/**
 *
 * @author Rom�n Segador
 */
public class DefaultGridPlugin extends GridPluginAdapter{
    private JSignalMonitorGrid jsmGrid;
    public DefaultGridPlugin() {
        jsmGrid=new DefaultGrid();
    }

    public String getName() {
        return "Default grid plugin";
    }

    public void launchConfigureGridGUI(Window owner) {
        jsmGrid.launchConfigureGridGUI(owner);
    }


    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                             GridConfiguration gridConfig) {
        jsmGrid.paintGrid(g2d,p,height,width,gridConfig);
    }

    public int getLeyendHeight() {
        return jsmGrid.getLeyendHeight();
    }

    public int getLeyendWidth() {
        return jsmGrid.getLeyendWidth();
    }

    public boolean hasDataToSave() {
        return true;
    }

    public String getDataToSave() {
        return jsmGrid.getDataToSave();
    }

    public void setSavedData(String data) {
        jsmGrid.setSavedData(data);
    }
}
