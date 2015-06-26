/*
 * ChannelConfigPanel.java
 *
 * Created on 19 de junio de 2007, 12:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.javahispano.jsignalwb.*;

/**
 *
 * @author Roman
 */
class ChannelInfoPanel extends JPanel implements MouseTimeChangeListener {
    public ChannelInfoPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Channel channel;
    private JSignalMonitorPanel jsmp;
    private JLabel name;
    private JLabel magnitude;
    private JLabel zoom;
    private JLabel frecuency;
    private JLabel point;
    private JLabel value;
    private JPanel arrows;
    private JLabel configure;
    private JLabel configureArrows;
    private LeftPanelConfiguration configuration;
    private ImageIcon icon, iconH;
    private JPanel p = new JPanel();
    private Font font;
    private JList jList1 = new JList();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    /** Creates a new instance of ChannelInfoPanel */
    public ChannelInfoPanel(Channel channel, JSignalMonitorPanel jsmp) {
        this.channel = channel;
        this.jsmp = jsmp;
        this.configuration = jsmp.getJSignalMonitorProperties().getLeftPanelConfiguration();
        try {
            this.jbInit();
        } catch (Exception ex) {
        }

        //setPointVisible(true);
    }

    public void showInfoConfigPanel() {
        new ChannelInfoConfigPanel(jsmp).showJWindow(jsmp);
    }

    public void setNameVisible(boolean visible) {
        //nameVisible=visible;
        if (visible) {
            if (name == null) {
                name = new JLabel();
                name.setFont(font);
                name.setForeground(
                        jsmp.getJSignalMonitorProperties().getLookAndFeelConfiguration().getColorFont());
                name.setToolTipText("The signal name...");
                name.setText(channel.getChannelProperties().getName());
                p.add(name);
            }
        }
        name.setVisible(visible);

    }

    public void setMagnitudeVisible(boolean visible) {
        //magnitudeVisible=visible;
        if (visible) {
            if (magnitude == null) {
                magnitude = new JLabel();
                magnitude.setForeground(Color.BLUE);
                magnitude.setFont(font);
                magnitude.setToolTipText("The magnitude of the values....");
            }
            magnitude.setText("Mag: " + channel.getChannelProperties().getMagnitude());
            p.add(magnitude);
        } else {
            p.remove(magnitude);
        }
    }

    public void setZoomVisible(boolean visible) {
        //zoomVisible=visible;
        if (visible) {
            if (zoom == null) {
                zoom = new JLabel();
                zoom.setForeground(Color.BLUE);
                zoom.setFont(font);
                zoom.setToolTipText("The zoom for the vertical values...");
            }
            zoom.setText("Zoom: " + channel.getChannelProperties().getZoom());
            p.add(zoom);
        } else {
            p.remove(zoom);
        }
    }

    public void setFrecuencyVisible(boolean visible) {
        //frecuencyVisible=visible;
        if (visible) {
            if (frecuency == null) {
                frecuency = new JLabel();
                frecuency.setForeground(Color.BLUE);
                frecuency.setFont(font);
                frecuency.setToolTipText("Zoom for time scale...");
            }
            frecuency.setText("Zoom H.: " + jsmp.getDataRate());
            p.add(frecuency);
        } else {
            p.remove(frecuency);
        }
    }

    public void setArrowsVisible(boolean visible) {
        //arrowsVisible=visible;
        if (visible) {
            if (arrows == null) {
                arrows = new ChannelArrowsControlPanel(channel.getChannelProperties().getName(), jsmp);
                p.add(Box.createHorizontalStrut(10));
                p.add(arrows);
            }
        }
        configure.setVisible(!visible);
        arrows.setVisible(visible);
    }

    public void setPointVisible(boolean visible) {
        //pointVisible=visible;
        if (visible) {
            if (point == null) {
                point = new JLabel();
                value = new JLabel();
                point.setForeground(Color.BLUE);
                point.setFont(font);
                point.setToolTipText("The time at the mouse position...");
                value.setForeground(Color.BLUE);
                value.setFont(font);
                value.setToolTipText("The value of the signal at the mouse position...");
            }
            point.setText("X: 0");
            value.setText("Y: 0");
            p.add(point);
            p.add(value);

        } else {
            if (point != null && value != null) {
                p.remove(point);
                p.remove(value);
            }
        }
    }

    /*   public void setInvadeNearChannels(boolean invade){
           if(invade!=channel.isInvadeNearChannels()){
               channel.setInvadeNearChannels(invade);
               jsmp.refresh();
           }
       }*/

    public void refreshValues() {
        if (zoom != null) {
            zoom.setText("Zoom: " + channel.getChannelProperties().getZoom());
        }
        if (frecuency != null) {
            frecuency.setText("Zoom H.: " + jsmp.getDataRate());
        }
        if (magnitude != null) {
            magnitude.setText("Mag: " + channel.getChannelProperties().getMagnitude());
        }
    }

    public void refreshFields() {
        setArrowsVisible(configuration.isArrowsVisible());
        p.add(Box.createHorizontalStrut(1000));
        setNameVisible(configuration.isNameVisible());
        p.add(Box.createHorizontalStrut(1000));
        setMagnitudeVisible(configuration.isMagnitudeVisible());
        p.add(Box.createHorizontalStrut(1000));
        setFrecuencyVisible(configuration.isFrecuencyVisible());
        p.add(Box.createHorizontalStrut(1000));

        setZoomVisible(configuration.isZoomVisible());
        if (jsmp.getJSignalMonitorProperties().isRepresentingValues() &&
            configuration.isPointVisible()) {
            setPointVisible(true);
        } else {
            setPointVisible(false);
        }
    }


    public void MouseTimeChangeActionPerformed(long time) {
        if (point != null) {
            point.setText("X: " + TimeRepresentation.timeToString(time, false, true, true));
            if (time >= channel.getChannelProperties().getStartTime() &&
                time < channel.getChannelProperties().getEndTime()) {
                float channelValue = jsmp.getDataSource().getChannelValueAtTime(
                        channel.getChannelProperties().getName(), time);
                if (Float.isNaN(channelValue)) {
                    value.setText("Y: --.--");
                } else {
                    value.setText("Y: " + channelValue);
                }
            } else {
                value.setText("Y: --.--");
            }
        }
    }

    public String getChannelName() {
        return channel.getChannelProperties().getName();
    }

    private void jbInit() throws Exception {
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 5, 0);
        p.setLayout(fl);
        p.setBackground(Color.white);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        Font tmp = jsmp.getJSignalMonitorProperties().getLookAndFeelConfiguration().getSmallFont();
        font = new Font(tmp.getFontName(), Font.PLAIN, tmp.getSize() - 2);
        configure = new JLabel();

        icon = new javax.swing.ImageIcon(ChannelInfoPanel.class.getResource("images/more.png"));
        iconH = new javax.swing.ImageIcon(ChannelInfoPanel.class.getResource("images/moreH.png"));

        configure.setIcon(icon);
        configure.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configure.setToolTipText("Select the fields to show at the info panel...");
        configure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showInfoConfigPanel();
            }
        });
        configureArrows = new JLabel();

        configureArrows.setIcon(icon);
        configureArrows.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configureArrows.setToolTipText("Select the fields to show at the info panel...");
        configureArrows.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showInfoConfigPanel();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(iconH);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel c = (JLabel) evt.getSource();
                c.setIcon(icon);
            }

        });
        jPanel1.setLayout(borderLayout1);
        jPanel1.setBackground(Color.white);
        labelUp.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                labelUp_mousePressed(e);
            }
        });
        labelDown.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                labelDown_mousePressed(e);
            }
        });
        p.add(configure);
        configure.setVisible(false);
        setArrowsVisible(true);
        arrows.add(configureArrows, BorderLayout.CENTER);
        p.add(Box.createHorizontalStrut(1000));
        this.add(p, java.awt.BorderLayout.CENTER);
        this.add(jPanel1, java.awt.BorderLayout.EAST);
        final int arrowSize = 12;
        Image image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/downB.png"));

        iconDown = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        labelDown.setIcon(iconDown);
        labelDown.setToolTipText("zoom -5");
        jPanel1.add(this.labelDown, java.awt.BorderLayout.SOUTH);

        image = Toolkit.getDefaultToolkit().createImage(
                getClass().getResource("images/upB.png"));

        iconUp = new ImageIcon(image.getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH));
        labelUp.setIcon(iconUp);
        labelUp.setToolTipText("zoom -5");
        jPanel1.add(this.labelUp, java.awt.BorderLayout.NORTH);

        setNameVisible(true);
        setMagnitudeVisible(true);
        setFrecuencyVisible(true);
        setZoomVisible(true);

    }

    private javax.swing.JLabel labelDown = new JLabel();
    private javax.swing.JLabel labelUp = new JLabel();

    private Icon iconUp, iconDown;
    public void labelDown_mouseClicked(MouseEvent e) {
        desplazaSignal(true);
    }

    private void desplazaSignal(boolean arriba) throws SignalNotFoundException {
        String signalName = channel.getChannelProperties().getName();
        SignalManager signalManager = JSWBManager.getJSWBManagerInstance().getSignalManager();
        float abscissaValue = signalManager.getAbscissaValue(signalName);
        float maxValue = signalManager.getMaxValue(signalName);
        float desplazamiento = (abscissaValue - maxValue) / 4;
        if (arriba) {
            desplazamiento*=-1;
        }
        signalManager.setSignalVisibleRange(signalName, abscissaValue + desplazamiento,
                                            maxValue + desplazamiento);
        JSWBManager.getJSWBManagerInstance().refreshJSM(false);
    }

    public void labelUp_mousePressed(MouseEvent e) {
        desplazaSignal(false);
    }

    public void labelDown_mousePressed(MouseEvent e) {
        desplazaSignal(true);
    }

    /* public boolean isArrowsVisible() {
         return arrowsVisible;
     }

     public boolean isNameVisible() {
         return nameVisible;
     }

     public boolean isMagnitudeVisible() {
         return magnitudeVisible;
     }

     public boolean isFrecuencyVisible() {
         return frecuencyVisible;
     }

     public boolean isZoomVisible() {
         return zoomVisible;
     }

     public boolean isPointVisible() {
         return pointVisible;
     }*/
    /* public boolean isInvadeNearChannels(){
         return channel.isInvadeNearChannels();
     }*/



}
