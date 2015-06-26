/*
 * AnnotationsSplitPanel.java
 *
 * Created on 18 de julio de 2007, 13:46
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.util.ArrayList;

import javax.swing.JSplitPane;

import net.javahispano.jsignalwb.jsignalmonitor.JSMProperties;

/**
 *
 * @author Roman Segador
 */
public class AnnotationsSplitPanel extends JSplitPane {
    private AnnotationCategoriesPanel acp;
    private AnnotationsPanel ap;
    private JSMProperties jsmProperties;
    public AnnotationsSplitPanel(JSMProperties jsmProperties, int hLeftOffset) {
        super();
        this.jsmProperties = jsmProperties;
        acp = new AnnotationCategoriesPanel();
        ap = new AnnotationsPanel(jsmProperties, hLeftOffset);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setContinuousLayout(false);
        setLeftComponent(acp);
        setRightComponent(ap);
        acp.refreshCategories(jsmProperties.getDataSource().getAvailableCategoriesOfAnnotations());
        ap.refreshCategories(jsmProperties.getDataSource().getAvailableCategoriesOfAnnotations());
        setOneTouchExpandable(true);
        setDividerSize(8);
        setDividerLocation(100);
    }

    public void refreshCategories() {
        ArrayList<String> categories = jsmProperties.getDataSource().getAvailableCategoriesOfAnnotations();
        acp.refreshCategories(categories);
        ap.refreshCategories(categories);

    }

    public void refreshAnnotations(long firstValue, long lastValue) {
        ap.refreshAnnotations(firstValue, lastValue);
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                ap.repaint();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

    }

}
