/*
 * JSMProperties.java
 *
 * Created on 28 de mayo de 2007, 12:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.util.ArrayList;

/**
 *
 *No forma parte del API.
 */
public class JSMProperties {
    private JSignalMonitorDataSource dataSource;
    private long scrollBaseTime;
    private long maxTime;
    private long scrollValue;
    private float frec;
    private ArrayList<JSignalMonitorScrollListener> listeners;
    private ArrayList<MouseTimeChangeListener> mouseListeners;
    private ArrayList<JSignalMonitorModeListener> modeListeners;
    private boolean representingValues;
    private boolean intervalSelection;
    private boolean ignoreRepaint;
    private boolean markCreation;
    private boolean clicked;
    private boolean showTimeLeyend;
    private boolean showActionLeyend;
    private long firstTimeClicked;
    private long mouseTime;
    private LeftPanelConfiguration leftPanelConfiguration;

    private LookAndFeelConfiguration lookAndFeelConfiguration;
    /** Creates a new instance of JSMProperties */

    public JSMProperties(JSignalMonitorDataSource dataSource) {
        this(dataSource, -1, 0, 0, 5);
    }

    public JSMProperties(JSignalMonitorDataSource dataSource, long scrollBaseTime, long maxTime, long scrollValue,
                         float frec) {
        this.dataSource = dataSource;
        this.scrollBaseTime = scrollBaseTime;
        this.maxTime = maxTime;
        this.scrollValue = scrollValue;
        this.frec = frec;
        mouseTime = scrollBaseTime;
        representingValues = false;
        intervalSelection = false;
        ignoreRepaint = false;
        markCreation = false;
        clicked = false;
        showTimeLeyend = true;
        showActionLeyend = true;
        firstTimeClicked = -1;
        leftPanelConfiguration = new LeftPanelConfiguration();
        listeners = new ArrayList<JSignalMonitorScrollListener>();
        mouseListeners = new ArrayList<MouseTimeChangeListener>();
        modeListeners = new ArrayList<JSignalMonitorModeListener>();
    }

    public void addScrollValueChangeListener(JSignalMonitorScrollListener l) {
        listeners.add(l);
    }

    public void addMouseTimeChangeListener(MouseTimeChangeListener l) {
        mouseListeners.add(l);
    }

    public void addModeListener(JSignalMonitorModeListener l) {
        modeListeners.add(l);
    }

    public void removeScrollValueChangeListener(JSignalMonitorScrollListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public void removeMouseTimeChangeListener(MouseTimeChangeListener l) {
        if (mouseListeners.contains(l)) {
            mouseListeners.remove(l);
        }
    }

    public void removeModeListener(JSignalMonitorModeListener l) {
        if (modeListeners.contains(l)) {
            modeListeners.remove(l);
        }
    }

    private void performAction(JSignalMonitorScrollEvent evt) {
        for (JSignalMonitorScrollListener l : listeners) {
            l.scrollValueChanged(evt);
        }
    }

    private void fireJSignalMonitorModeAction(JSignalMonitorModeEvent evt) {
        for (JSignalMonitorModeListener l : modeListeners) {
            l.jSignalMonitorModeActionPerformed(evt);
        }
    }

    public JSignalMonitorDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JSignalMonitorDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long getScrollBaseTime() {
        return scrollBaseTime;
    }

    public void setScrollBaseTime(long scrollBaseTime) {
        this.scrollBaseTime = scrollBaseTime;
    }

    public float getFrec() {
        return frec;
    }

    public void setFrec(float frec) {
        this.frec = frec;
    }

    public long getScrollValue() {
        return scrollValue;
    }

    public void setScrollValue(long scrollValue) {
        if (scrollValue < scrollBaseTime) {
            scrollValue = scrollBaseTime;
        } else if (scrollValue > maxTime) {
            scrollValue = maxTime;
        }
        long temp = this.getScrollValue();
        this.scrollValue = scrollValue;
        performAction(new JSignalMonitorScrollEvent(scrollValue, temp));

    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    /**
     *  Este metodo calcula el tiempo correspondiente al pixel que se encuentra a la
     *  distancia(en pixeles) que se le pasa como parametro del pixel que representa
     *  el valor del scroll,
     */
    public long getTimeAtLocation(int width) {
        return getScrollValue() + (long) (1000 * ((width / getFrec())));
    }

    public int getLocationAtTime(long time) {
        return (int) ((getFrec() * (time - getScrollValue())) / 1000f);
    }

    public boolean isRepresentingValues() {
        return representingValues;
    }

    public void setRepresentingValues(boolean representingValues) {
        this.representingValues = representingValues;
        fireJSignalMonitorModeAction(new JSignalMonitorModeEvent(JSignalMonitorModeEvent.REPRESENT_XY_VALUES,
                representingValues));
    }

    public long getMouseTime() {
        return mouseTime;
    }

    public void setMouseTime(long mouseTime) {
        this.mouseTime = mouseTime;
        for (MouseTimeChangeListener l : mouseListeners) {
            l.MouseTimeChangeActionPerformed(mouseTime);
        }

    }

    public boolean isIntervalSelection() {
        return intervalSelection;
    }

    public void setIntervalSelection(boolean intervalSelection) {
        this.intervalSelection = intervalSelection;
        fireJSignalMonitorModeAction(new JSignalMonitorModeEvent(JSignalMonitorModeEvent.INTERVAL_SELECTION,
                intervalSelection));
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public long getFirstTimeClicked() {
        return firstTimeClicked;
    }

    public void setFirstTimeClicked(long firstTimeClicked) {
        this.firstTimeClicked = firstTimeClicked;
    }

    public boolean isMarkCreation() {
        return markCreation;
    }

    public void setMarkCreation(boolean markCreation) {
        this.markCreation = markCreation;
        this.setIntervalSelection(false);
        fireJSignalMonitorModeAction(new JSignalMonitorModeEvent(JSignalMonitorModeEvent.MARK_CREATION, markCreation));
    }

    public LeftPanelConfiguration getLeftPanelConfiguration() {
        return leftPanelConfiguration;
    }

    public boolean isIgnoreRepaint() {
        return ignoreRepaint;
    }

    public void setIgnoreRepaint(boolean ignoreRepaint) {
        this.ignoreRepaint = ignoreRepaint;
    }

    public boolean isShowTimeLeyend() {
        return showTimeLeyend;
    }

    public void setShowTimeLeyend(boolean showTimeLeyend) {
        this.showTimeLeyend = showTimeLeyend;
    }

    public boolean isShowActionLeyend() {
        return showActionLeyend;
    }

    public LookAndFeelConfiguration getLookAndFeelConfiguration() {
        return lookAndFeelConfiguration;
    }

    public void setShowActionLeyend(boolean showActionLeyend) {
        this.showActionLeyend = showActionLeyend;
    }

    public void setLookAndFeelConfiguration(LookAndFeelConfiguration lookAndFeelConfiguration) {
        this.lookAndFeelConfiguration = lookAndFeelConfiguration;
    }

    public float getFrecForFullView(int width) {
        return ((float) width) / ((maxTime - scrollBaseTime) / 1000);
    }

    public float getFrecForTimeInterval(long startTime, long endTime, int width) {
        return ((float) width) / ((endTime - startTime) / 1000);
    }
}
