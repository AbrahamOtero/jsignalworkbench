/*
 * AddAnnotationAction.java
 *
 * Created on 18 de julio de 2007, 10:34
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSource;

/**
 *
 * @author Roman Segador
 */
public class AddAnnotationAction extends AbstractAction {

    private String annotationName;
    private JSignalMonitorDataSource dataSource;
    private long startTime;
    private long endTime;
    boolean interval;

    public AddAnnotationAction(String annotationName, JSignalMonitorDataSource dataSource, long time) {
        this.annotationName = annotationName;
        this.dataSource = dataSource;
        this.startTime = time;
        interval = false;
        putValue(NAME, annotationName);
    }

    public AddAnnotationAction(String annotationName, JSignalMonitorDataSource dataSource, long startTime, long endTime) {
        this.annotationName = annotationName;
        this.dataSource = dataSource;
        this.startTime = startTime;
        this.endTime = endTime;
        interval = true;
        putValue(NAME, annotationName);
    }

    public void actionPerformed(ActionEvent e) {
        if (interval) {
            dataSource.notifyAnnotationAdded(annotationName, startTime, endTime);
        } else {
            dataSource.notifyAnnotationAdded(annotationName, startTime);
        }
    }
}
