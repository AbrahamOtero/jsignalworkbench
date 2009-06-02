/*
 * AnnotationsPanel.java
 *
 * Created on 18 de julio de 2007, 9:13
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.javahispano.jsignalwb.jsignalmonitor.JSMProperties;

/**
 *
 * @author Roman Segador
 */
public class AnnotationsPanel extends JPanel {
    private ArrayList<String> categories;
    private JSMProperties jsmProperties;
    private HashMap<JSignalMonitorAnnotation, Rectangle> annotations;
    private int hLeftOffset;
    private Point mousePosition;
    private Color g2dColor;
    public AnnotationsPanel(JSMProperties jsmProperties, int hLeftOffset) {
        this.jsmProperties = jsmProperties;
        categories = null;
        annotations = new HashMap<JSignalMonitorAnnotation, Rectangle>();
        this.hLeftOffset = hLeftOffset;
        mousePosition = new Point();
        setBackground(Color.WHITE);
        g2dColor = new Color(0, 255, 0, 75);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                annotationsMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                annotationsMouseClicked(evt);
            }
        });
    }

    private void annotationsMouseClicked(MouseEvent evt) {
        if (evt.getButton() == evt.BUTTON1) {
            boolean existentAnnotation = false;
            Iterator<Rectangle> it = annotations.values().iterator();
            while (it.hasNext()) {
                Rectangle rect = it.next();
                if (rect.contains(evt.getPoint())) {
                    Iterator<JSignalMonitorAnnotation> it2 = annotations.keySet().iterator();
                    while (it2.hasNext()) {
                        JSignalMonitorAnnotation annotation = it2.next();
                        if (annotations.get(annotation).equals(rect)) {
                            existentAnnotation = true;
                            annotation.showMarkInfo(getParentFrame());
                            break;
                        }
                    }
                    break;
                }
            }
            if (!existentAnnotation) {
                if (jsmProperties.isIntervalSelection()) {
                    if (!jsmProperties.isClicked()) {
                        jsmProperties.setFirstTimeClicked(jsmProperties.getTimeAtLocation(evt.getX() - hLeftOffset));
                        jsmProperties.setClicked(true);
                    } else {
                        jsmProperties.setIntervalSelection(false);
                        long secondTimeClicked = jsmProperties.getTimeAtLocation(evt.getX() - hLeftOffset);
                        if (jsmProperties.isMarkCreation()) {
                            /*jsmProperties.getDataSource().notifyMarkAdded(
                                positions.get((evt.getY()-getVScaleOffset()) / this.channelHeight),
                                jsmProperties.getFirstTimeClicked(),secondTimeClicked);*/
                            JPopupMenu popup = new AnnotationsPopupMenu(jsmProperties.getDataSource(),
                                    Math.min(jsmProperties.getFirstTimeClicked(), secondTimeClicked),
                                    Math.max(jsmProperties.getFirstTimeClicked(), secondTimeClicked));
                            popup.show(this, evt.getX(), evt.getY());
                        }
                        Runnable uiUpdateRunnable = new Runnable() {
                            public void run() {
                                repaint();
                            }
                        };
                        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

                    }
                } else if (jsmProperties.isMarkCreation()) {
                    JPopupMenu popup = new JPopupMenu();
                    popup.add(new AnnotationAction(this, jsmProperties, evt.getPoint(), false));
                    popup.add(new AnnotationAction(this, jsmProperties, evt.getPoint(), true));
                    popup.show(this, evt.getX(), evt.getY());
                }

            }
        }
    }

    private void annotationsMouseMoved(MouseEvent evt) {
        boolean flag = false;
        Iterator<Rectangle> it = annotations.values().iterator();
        while (it.hasNext()) {
            Rectangle rect = it.next();
            if (rect.contains(evt.getPoint())) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                Iterator<JSignalMonitorAnnotation> it2 = annotations.keySet().iterator();
                while (it2.hasNext()) {
                    JSignalMonitorAnnotation annotation = it2.next();
                    if (annotations.get(annotation).equals(rect)) {
                        setToolTipText(annotation.getToolTipText());
                        break;
                    }
                }
                break;
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            setToolTipText(null);
        }
        if (jsmProperties.isIntervalSelection() && jsmProperties.isClicked()) {
            flag = true;
            mousePosition.setLocation(evt.getX(), 0);
        }
        if (flag) {
            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    repaint();
                }
            };
            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (categories.size() > 0) {
            for (int index = 1; index <= categories.size(); index++) {
                g2d.drawLine(0, (index) * getFieldSize(), (int) getSize().getWidth(), (index) * getFieldSize());
            }
        } else {
            g2d.drawString("No annotations", 50, 20);
        }
        if (annotations.size() > 0) {
            Iterator<JSignalMonitorAnnotation> it = annotations.keySet().iterator();
            JSignalMonitorAnnotation annotation;
            Rectangle rect;
            while (it.hasNext()) {
                annotation = it.next();
                rect = annotations.get(annotation);
                if (annotation.isOwnPainted()) {
                    annotation.paint(g2d, rect.getLocation(), rect.height, rect.width);
                } else {
                    g2d.drawImage(annotation.getImage(),
                                  rect.x, rect.y, rect.width, rect.height, null);
                }
            }
        }
        if (jsmProperties.isIntervalSelection() && jsmProperties.isClicked()) {
            g2d.setColor(g2dColor);
            if (mousePosition.getX() != 0) {
                int firstClickPosition = getPanelPosition(jsmProperties.getFirstTimeClicked());
                g2d.fillRect((int) Math.min(firstClickPosition, mousePosition.getX()), 0,
                             (int) Math.abs(firstClickPosition - mousePosition.getX()), getHeight());
            }
        }
    }

    public void refreshCategories(ArrayList<String> categories) {
        this.categories = categories;
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                repaint();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

    }

    private int getFieldSize() {
        if (categories.size() == 0) {
            return 1;
        } else {
            return (int) ((getSize().getHeight()) / categories.size());
        }
    }

    public int getHLeftOffsetScale() {
        return hLeftOffset;
    }

    public void refreshAnnotations(long firstValue, long lastValue) {
        annotations.clear();
        List<JSignalMonitorAnnotation> temp = jsmProperties.getDataSource().getAnnotations(firstValue, lastValue);
        for (JSignalMonitorAnnotation annotation : temp) {
            Image annotationImage = annotation.getImage();
            if (!annotation.isInterval()) {

                annotations.put(annotation,
                                new Rectangle(hLeftOffset +
                                              jsmProperties.getLocationAtTime(annotation.getAnnotationTime()),
                                              categories.indexOf(annotation.getCategory()) * getFieldSize() + 5,
                                              annotationImage.getWidth(null),
                                              Math.min(getFieldSize() - 10, annotationImage.getHeight(null))));
            } else {
                int startPosition = hLeftOffset + jsmProperties.getLocationAtTime(annotation.getAnnotationTime());
                int endPosition = hLeftOffset + jsmProperties.getLocationAtTime(annotation.getEndTime());
                if (startPosition < hLeftOffset) {
                    startPosition = hLeftOffset;
                }
                if (endPosition > getSize().width - 10) {
                    endPosition = getSize().width - 10;
                }
                Rectangle rect;
                if (annotation.isOwnPainted()) {

                    rect = new Rectangle(startPosition,
                                         categories.indexOf(annotation.getCategory()) * getFieldSize() + 5,
                                         endPosition - startPosition,
                                         getFieldSize() - 10);
                } else {
                    rect = new Rectangle(startPosition,
                                         categories.indexOf(annotation.getCategory()) * getFieldSize() + 5,
                                         endPosition - startPosition,
                                         Math.min(getFieldSize() - 10, annotationImage.getHeight(null)));
                }
                annotations.put(annotation, rect);
            }
        }
    }

    private Window getParentFrame() {
        Container c = this.getParent();
        while (c != null && c.isLightweight()) {
            c = c.getParent();
            if (c instanceof Frame) {
                return (Window) c;
            }
        }
        return null;
    }

    private int getPanelPosition(long time) {
        if (time < jsmProperties.getScrollValue()) {
            return getHLeftOffsetScale();
        } else if (time > jsmProperties.getTimeAtLocation((int) (getSize().getWidth() - getHLeftOffsetScale()))) {
            return (int) getSize().getWidth();
        }
        return (int) (((time - jsmProperties.getScrollValue()) / 1000f) * jsmProperties.getFrec()) +
                getHLeftOffsetScale();
    }
}
