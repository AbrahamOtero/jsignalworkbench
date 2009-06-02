/*
 * JSignalMonitorDataSourceAdapter.java
 *
 * Created on 2 de agosto de 2007, 12:53
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.util.ArrayList;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;

/**
 *
 * @author Roman Segador
 */
public abstract class JSignalMonitorDataSourceAdapter implements JSignalMonitorDataSource {
    private ArrayList mokList = new ArrayList<String>();

    public List<JSignalMonitorAnnotation> getAnnotations(long fistValue, long endValue) {
        return mokList;
    }

    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        return mokList;
    }

    public List<String> getAvailableKindsOfInstantAnnotations() {
        return mokList;
    }

    public List<String> getAvailableKindsOfInstantMarks() {
        return mokList;
    }

    public List<String> getAvailableKindsOfIntervalAnnotations() {
        return mokList;
    }

    public List<String> getAvailableKindsOfIntervalMarks() {
        return mokList;
    }

    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue, long lastValue) {
        return mokList;
    }

    public short[] getSignalEmphasisLevels(String signalName, long firstValue, long lastValue) {
        throw new UnsupportedOperationException(
                "Override getSignalEmphasisLevels on " +
                "JSignalMonitorDataSourceAdapter if you want to use emphasis levels");
    }

    public void notifyAnnotationAdded(String kindOfAnnotation, long startTime, long endTime) {
    }

    public void notifyAnnotationAdded(String kindOfAnnotation, long time) {
    }

    public void notifyIntervalSelection(String signalName, long startTime, long endTime) {
    }

    public void notifyMarkAdded(String kindOfMark, String signalName, long startTime, long endTime) {
    }

    public void notifyMarkAdded(String kindOfMark, String signalName, long time) {
    }

}
