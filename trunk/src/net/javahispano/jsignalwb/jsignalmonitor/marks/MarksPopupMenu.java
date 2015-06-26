/*
 * MarksPopupMenu.java
 *
 * Created on 6 de julio de 2007, 3:04
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import javax.swing.JPopupMenu;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSource;

/**
 *
 * @author Roman Segador
 */
public class MarksPopupMenu extends JPopupMenu {

    public MarksPopupMenu(JSignalMonitorDataSource dataSource, String signalName, long time) {
        super();
        for (String markName : dataSource.getAvailableKindsOfInstantMarks()) {
            add(new AddMarkAction(markName, signalName, dataSource, time));
        }
    }

    public MarksPopupMenu(JSignalMonitorDataSource dataSource, String signalName, long startTime, long endTime) {
        super();

        for (String markName : dataSource.getAvailableKindsOfIntervalMarks()) {
            add(new AddMarkAction(markName, signalName, dataSource, startTime, endTime));
        }
    }
}
