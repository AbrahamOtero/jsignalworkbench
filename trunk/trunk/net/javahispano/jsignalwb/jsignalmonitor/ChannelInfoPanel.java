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

/**
 *
 * @author Roman
 */
class ChannelInfoPanel extends JPanel implements MouseTimeChangeListener {
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

    private Font font;
    /** Creates a new instance of ChannelInfoPanel */
    public ChannelInfoPanel(Channel channel, JSignalMonitorPanel jsmp) {
        this.channel = channel;
        this.jsmp = jsmp;
        this.configuration = jsmp.getJSignalMonitorProperties().getLeftPanelConfiguration();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 5, 0);

        setLayout(fl);
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
        add(configure);

        configure.setVisible(false);
        setArrowsVisible(true);
        arrows.add(configureArrows, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(1000));
        setNameVisible(true);
        setMagnitudeVisible(true);
        setFrecuencyVisible(true);
        setZoomVisible(true);

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
                add(name);
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
            add(magnitude);
        } else {
            remove(magnitude);
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
            add(zoom);
        } else {
            remove(zoom);
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
            add(frecuency);
        } else {
            remove(frecuency);
        }
    }

    public void setArrowsVisible(boolean visible) {
        //arrowsVisible=visible;
        if (visible) {
            if (arrows == null) {
                arrows = new ChannelArrowsControlPanel(channel.getChannelProperties().getName(), jsmp);
                add(Box.createHorizontalStrut(10));
                add(arrows);
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
            add(point);
            add(value);

        } else {
            if (point != null && value != null) {
                remove(point);
                remove(value);
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
        this.add(Box.createHorizontalStrut(1000));
        setNameVisible(configuration.isNameVisible());
        this.add(Box.createHorizontalStrut(1000));
        setMagnitudeVisible(configuration.isMagnitudeVisible());
        this.add(Box.createHorizontalStrut(1000));
        setFrecuencyVisible(configuration.isFrecuencyVisible());
        this.add(Box.createHorizontalStrut(1000));

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

    /*private void setConfigureMode(){
        if(arrowsCheckBox==null){
            arrowsCheckBox=new JCheckBox("Arrows",arrowsVisible);
        }
        else
            arrowsCheckBox.setSelected(arrowsVisible);

        if(magnitudeCheckBox==null){
            magnitudeCheckBox=new JCheckBox("Magnitude",magnitudeVisible);
        }
        else
            magnitudeCheckBox.setSelected(magnitudeVisible);

        if(nameCheckBox==null){
            nameCheckBox=new JCheckBox("Name",nameVisible);
        }
        else
            nameCheckBox.setSelected(nameVisible);

        if(frecuencyCheckBox==null){
            frecuencyCheckBox=new JCheckBox("Frecuency",frecuencyVisible);
        }
        else
            frecuencyCheckBox.setSelected(frecuencyVisible);

        if(zoomCheckBox==null){
            zoomCheckBox=new JCheckBox("Zoom",zoomVisible);
        }
        else
            zoomCheckBox.setSelected(zoomVisible);

        if(pointCheckBox==null){
            pointCheckBox=new JCheckBox("XY",pointVisible);
        }
        else
            pointCheckBox.setSelected(pointVisible);

        if(arrowsVisible)
            setArrowsVisible(false);
        if(magnitudeVisible)
            setMagnitudeVisible(false);
        if(nameVisible)
            setNameVisible(false);
        if(frecuencyVisible)
            setFrecuencyVisible(false);
        if(zoomVisible)
            setZoomVisible(false);
        if(pointVisible)
            setPointVisible(false);
        remove(configure);

        add(arrowsCheckBox);
        add(nameCheckBox);
        add(magnitudeCheckBox);
        add(frecuencyCheckBox);
        add(zoomCheckBox);
        add(pointCheckBox);
        ok=new JButton("OK");
        ok.setActionCommand("ok");
        ok.addActionListener(this);
        add(ok);
        this.validate();
        this.repaint();
         }

         public void actionPerformed(ActionEvent e) {
        if("ok".equals(e.getActionCommand())){
            remove(arrowsCheckBox);
            remove(nameCheckBox);
            remove(magnitudeCheckBox);
            remove(frecuencyCheckBox);
            remove(zoomCheckBox);
            remove(pointCheckBox);
            remove(ok);

            add(configure);

            setArrowsVisible(arrowsCheckBox.isSelected());
            setNameVisible(nameCheckBox.isSelected());
            setMagnitudeVisible(magnitudeCheckBox.isSelected());
            setFrecuencyVisible(frecuencyCheckBox.isSelected());
            setZoomVisible(zoomCheckBox.isSelected());
            setPointVisible(pointCheckBox.isSelected());

            validate();
            repaint();

        }
         }
     */
    public String getChannelName() {
        return channel.getChannelProperties().getName();
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
