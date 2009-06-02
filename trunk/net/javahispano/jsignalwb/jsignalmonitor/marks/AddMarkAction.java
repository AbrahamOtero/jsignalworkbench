/*
 * AddMarkAction.java
 *
 * Created on 6 de julio de 2007, 2:52
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSource;

/**
 *
 * @author Roman Segador
 */
public class AddMarkAction extends AbstractAction {
    private String markName;
    private String signalName;
    private JSignalMonitorDataSource dataSource;
    private long startTime;
    private long endTime;
    boolean interval;

    public AddMarkAction(String markName, String signalName, JSignalMonitorDataSource dataSource, long time) {
        this.markName = markName;
        this.signalName = signalName;
        this.dataSource = dataSource;
        this.startTime = time;
        interval = false;
        putValue(NAME, markName);
    }

    public AddMarkAction(String markName, String signalName, JSignalMonitorDataSource dataSource, long startTime,
                         long endTime) {
        this.markName = markName;
        this.signalName = signalName;
        this.dataSource = dataSource;
        this.startTime = startTime;
        this.endTime = endTime;
        interval = true;
        putValue(NAME, markName);
    }

    public void actionPerformed(ActionEvent e) {
        if (interval) {
            dataSource.notifyMarkAdded(markName, signalName, startTime, endTime);
        } else {
            dataSource.notifyMarkAdded(markName, signalName, startTime);
        }
    }

}
