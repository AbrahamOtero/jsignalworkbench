/*
 * MarkAction.java
 *
 * Created on 6 de julio de 2007, 1:41
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import net.javahispano.jsignalwb.jsignalmonitor.Channels;
import net.javahispano.jsignalwb.jsignalmonitor.JSMProperties;

/**
 *
 * @author Roman Segador
 */
public class MarkAction extends AbstractAction {
    private Channels channels;
    private JSMProperties jsmp;
    private Point p;
    private boolean interval;
    public MarkAction(Channels channels, JSMProperties jsmp, Point p, boolean interval) {
        this.channels = channels;
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
            JPopupMenu popup = new MarksPopupMenu(jsmp.getDataSource(),
                                                  channels.getChannelAtIndex((int) (p.getY() - channels.getVScaleOffset()) /
                    channels.getChannelHeight()),
                                                  jsmp.getTimeAtLocation((int) p.getX() - channels.getHLeftOffsetScale()));
            //Point p=MouseInfo.getPointerInfo().getLocation();
            popup.show(channels, (int) p.getX(), (int) p.getY());
            /*jsmp.getDataSource().notifyMarkAdded(channels.getChannelAtIndex((int)(p.getY()-channels.getVScaleOffset()) / channels.getChannelHeight()),
                    jsmp.getTimeAtLocation((int)p.getX()-channels.getHLeftOffsetScale()));*/
        } else {
            jsmp.setIntervalSelection(true);
            jsmp.setFirstTimeClicked(jsmp.getTimeAtLocation((int) p.getX() - channels.getHLeftOffsetScale()));
            jsmp.setClicked(true);
        }

    }

}
