/*
 * ChannelConfigPanel.java
 *
 * Created on 18 de junio de 2007, 13:11
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.Cursor;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author  Roman
 */
class ChannelArrowsControlPanel extends javax.swing.JPanel {
    private Cursor cursor;
    private String channelName;
    private JSignalMonitorPanel jsmPanel;
    private Icon iconUp, iconDown, iconLeft, iconRight, iconUpH, iconDownH, iconLeftH, iconRightH;

    /** Creates new form ChannelConfigPanel */
    public ChannelArrowsControlPanel(String channelName, JSignalMonitorPanel jsmPanel) {
        this.channelName = channelName;
        this.jsmPanel = jsmPanel;
        initComponents();
        MouseListener mlRight = new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChannelArrowsControlPanel.this.mouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconRightH);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconRight);
            }

        };
        MouseListener mlUp = new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChannelArrowsControlPanel.this.mouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconUpH);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconUp);
            }

        };
        MouseListener mlDown = new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChannelArrowsControlPanel.this.mouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconDownH);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconDown);
            }

        };
        MouseListener mlLeft = new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChannelArrowsControlPanel.this.mouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconLeftH);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconLeft);
            }

        };

        cursor = new Cursor(Cursor.HAND_CURSOR);
        labelUp.setCursor(cursor);
        labelDown.setCursor(cursor);
        labelLeft.setCursor(cursor);
        labelRight.setCursor(cursor);
        labelUp.addMouseListener(mlUp);
        labelDown.addMouseListener(mlDown);
        labelLeft.addMouseListener(mlLeft);
        labelRight.addMouseListener(mlRight);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        labelUp = new javax.swing.JLabel();
        labelDown = new javax.swing.JLabel();
        labelRight = new javax.swing.JLabel();
        labelLeft = new javax.swing.JLabel();

        final int arrowSize = 12;

        setLayout(new java.awt.BorderLayout(3, 3));

        setBackground(new java.awt.Color(255, 255, 255));
        labelUp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Image image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/up.png"));
        iconUp = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/upH.png"));
        iconUpH = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        labelUp.setIcon(iconUp);
        labelUp.setToolTipText("Zoom +5");
        add(labelUp, java.awt.BorderLayout.NORTH);

        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/down.png"));
        iconDown = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/downH.png"));
        iconDownH = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        labelDown.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDown.setIcon(iconDown);
        labelDown.setToolTipText("zoom -5");
        add(labelDown, java.awt.BorderLayout.SOUTH);

        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/right.png"));
        iconRight = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/rightH.png"));
        iconRightH = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        labelRight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRight.setIcon(iconRight);
        labelRight.setToolTipText("Frecuency +0.5");
        labelRight.setName("right");
        add(labelRight, java.awt.BorderLayout.EAST);

        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/left.png"));
        iconLeft = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/leftH.png"));
        iconLeftH = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        labelLeft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLeft.setIcon(iconLeft);
        labelLeft.setToolTipText("Frecuency -0.5");
        add(labelLeft, java.awt.BorderLayout.WEST);

    } // </editor-fold>//GEN-END:initComponents

    private void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getSource().equals(labelUp)) {
            jsmPanel.setVerticalZoom(channelName, ( (jsmPanel.getChannelProperties(channelName).getZoom() * 120)));
            //zoom.setText("Zoom: "+(int)(jsmPanel.getChannelProperties(channelName).getZoom()*arrowSize0)+"%");
            jsmPanel.refresh();
        } else if (evt.getSource().equals(labelDown)) {
            float newZoom =  (jsmPanel.getChannelProperties(channelName).getZoom() * 80);
            if (newZoom > 0) {
                jsmPanel.setVerticalZoom(channelName, newZoom);
                //zoom.setText("Zoom: "+newZoom+"%");
                jsmPanel.refresh();
            }
        } else if (evt.getSource().equals(labelRight)) {
            long temp = jsmPanel.getScrollValue();
            jsmPanel.setDataRate(jsmPanel.getDataRate() * 1.1f);
            //frecuency.setText("Frecuency: "+jsmPanel.getDataRate()+" Hz");
            jsmPanel.refresh();
            jsmPanel.setScrollValue(temp);
        } else if (evt.getSource().equals(labelLeft)) {
            float newFrec = jsmPanel.getDataRate() * 0.9f;
            if (newFrec > 0) {
                long temp = jsmPanel.getScrollValue();
                jsmPanel.setDataRate(newFrec);
                //frecuency.setText("Frecuency: "+newFrec+" Hz");
                jsmPanel.refresh();
                jsmPanel.setScrollValue(temp);
            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelDown;
    private javax.swing.JLabel labelLeft;
    private javax.swing.JLabel labelRight;
    private javax.swing.JLabel labelUp;
    // End of variables declaration//GEN-END:variables

}