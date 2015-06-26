/*
 * AnnotationsPopupMenu.java
 *
 * Created on 18 de julio de 2007, 10:37
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import javax.swing.JPopupMenu;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSource;

/**
 *
 * @author Roman Segador
 */
public class AnnotationsPopupMenu extends JPopupMenu {


    public AnnotationsPopupMenu(JSignalMonitorDataSource dataSource, long time) {
        super();
        for (String annotationName : dataSource.getAvailableKindsOfInstantAnnotations()) {
            add(new AddAnnotationAction(annotationName, dataSource, time));
        }
    }

    public AnnotationsPopupMenu(JSignalMonitorDataSource dataSource, long startTime, long endTime) {
        super();
        for (String annotationName : dataSource.getAvailableKindsOfIntervalAnnotations()) {
            add(new AddAnnotationAction(annotationName, dataSource, startTime, endTime));
        }
    }
}
