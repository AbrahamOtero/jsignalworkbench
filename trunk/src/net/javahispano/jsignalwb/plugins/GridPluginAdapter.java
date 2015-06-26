package net.javahispano.jsignalwb.plugins;

import java.awt.Window;

import net.javahispano.jsignalwb.Signal;

public abstract class GridPluginAdapter extends PluginAdapter implements GridPlugin {
    /**
     * Contiene la senhal sobre la cual se esta mostrando este grid.
     */
    protected Signal signal;
    public GridPluginAdapter() {
    }

    public void setSignal(Signal s) {
        signal = s;
    }

    public void launchConfigureGridGUI(Window owner) {
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

    /**
     * getLeyendHeight
     *
     * @return 0
     */
    public int getLeyendHeight() {
        return 0;
    }

    /**
     * getLeyendWidth
     *
     * @return 0
     */
    public int getLeyendWidth() {
        return 0;
    }

}
