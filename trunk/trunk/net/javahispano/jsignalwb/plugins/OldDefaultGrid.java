package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import java.awt.Point;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import java.awt.Graphics2D;
import java.awt.Window;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorGrid;

/**
 * @todo borrar
 */
public class OldDefaultGrid extends GridPluginAdapter{
    private JSignalMonitorGrid jsmGrid;
    public OldDefaultGrid() {
        jsmGrid=new DefaultGrid();
    }

    public String getName() {
        return "Default grid plugin";
    }

    public void launchConfigureGridGUI(Window owner) {
        jsmGrid.launchConfigureGridGUI(owner);
    }


    public void paintGrid(Graphics2D g2d, Point p, int height, int width, GridConfiguration gridConfig) {
        jsmGrid.paintGrid(g2d,p,height,width,gridConfig);
    }

    public int getLeyendWidth() {
        return 0;
    }

    public int getLeyendEight() {
        return 0;
    }

    public int getLeyendHeight() {
        return 0;
    }


}
