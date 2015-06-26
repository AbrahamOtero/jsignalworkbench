/*
 * AnnotationAction.java
 *
 * Created on 18 de julio de 2007, 10:30
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import net.javahispano.jsignalwb.jsignalmonitor.JSMProperties;

/**
 *
 * @author Roman Segador
 */
public class AnnotationAction extends AbstractAction {
    private AnnotationsPanel annotationsPanel;
    private JSMProperties jsmp;
    private Point p;
    private boolean interval;
    public AnnotationAction(AnnotationsPanel annotationsPanel, JSMProperties jsmp, Point p, boolean interval) {
        this.annotationsPanel = annotationsPanel;
        this.jsmp = jsmp;
        this.interval = interval;
        this.p = p;
        if (interval) {
            putValue(NAME, "Interval");
        } else {
            putValue(NAME, "Instant");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!interval) {
            JPopupMenu popup = new AnnotationsPopupMenu(jsmp.getDataSource(),
                    jsmp.getTimeAtLocation((int) p.getX() - annotationsPanel.getHLeftOffsetScale()));
            //Point p=MouseInfo.getPointerInfo().getLocation();
            popup.show(annotationsPanel, (int) p.getX(), (int) p.getY());
            /*jsmp.getDataSource().notifyMarkAdded(channels.getChannelAtIndex((int)(p.getY()-channels.getVScaleOffset()) / channels.getChannelHeight()),
                    jsmp.getTimeAtLocation((int)p.getX()-channels.getHLeftOffsetScale()));*/
        } else {
            jsmp.setIntervalSelection(true);
            jsmp.setFirstTimeClicked(jsmp.getTimeAtLocation((int) p.getX() - annotationsPanel.getHLeftOffsetScale()));
            jsmp.setClicked(true);
        }

    }

}
