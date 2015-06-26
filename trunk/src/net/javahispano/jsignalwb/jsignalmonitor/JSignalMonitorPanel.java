/*
 * JSignalMonitorScrollBar.java
 *
 * Created on 25 de abril de 2007, 18:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.LineBorder;

import net.javahispano.jsignalwb.jsignalmonitor.marks.AnnotationsSplitPanel;
import net.javahispano.jsignalwb.jsignalmonitor.printsupport.PrintUtilities;

/**
 *
 * @author Roman
 */
class JSignalMonitorPanel extends JPanel implements AdjustmentListener, JSignalMonitorScrollListener,
        PropertyChangeListener {
    public JScrollBar jScrollBar;
    private Channels channels;
    private JPanel leftPanel;
    private JSplitPane splitPanel;
    private JSplitPane splitPanel2;
    private AnnotationsSplitPanel splitPanel3;
    private JSMProperties jsmProperties;
    private HashMap<String, JPanel> configPanels;
    private GridLayout gl;
    private JScrollBar JScrollBar;
    /** Creates a new instance of JSignalMonitorScrollBar */
    public JSignalMonitorPanel(JSMProperties jsmProperties) {
        this.jsmProperties = jsmProperties;
        jsmProperties.addScrollValueChangeListener(this);
        channels = new Channels(jsmProperties);
        leftPanel = new JPanel();
        configPanels = new HashMap<String, JPanel>();
        //leftPanel.setBorder(new LineBorder(Color.BLACK));

        leftPanel.setPreferredSize(new Dimension(150, (int) channels.getSize().getHeight()));
        leftPanel.setMinimumSize(new Dimension());
        //leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        channels.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                refresh();
            }
        });

        jScrollBar = new JScrollBar();
        jScrollBar.setOrientation(JScrollBar.HORIZONTAL);
        jScrollBar.addAdjustmentListener(this);
        splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, leftPanel, channels);
        splitPanel.setOneTouchExpandable(true);
        splitPanel.setDividerSize(8);
        splitPanel3 = new AnnotationsSplitPanel(jsmProperties, channels.getHLeftOffsetScale());
        splitPanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, splitPanel3, splitPanel);
        splitPanel2.setOneTouchExpandable(true);
        splitPanel2.setDividerSize(8);
        splitPanel2.setDividerLocation(60);
        splitPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);
        splitPanel3.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);
        gl = new GridLayout(1, 1);
        leftPanel.setLayout(gl);
        setLayout(new BorderLayout());
        add(jScrollBar, BorderLayout.SOUTH);
        add(splitPanel2, BorderLayout.CENTER);
        splitPanel.setDividerLocation(120);
    }

    public void printSignals(int orientation) {
        PrintUtilities printUtilities = new PrintUtilities(this);
        printUtilities.setOrientation(orientation);
        try {
            printUtilities.printComponent(this.channels);
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showConfirmDialog(this, "Error attempting to print", "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        this.repaint();
    }

    public void printAll(int orientation) {
        PrintUtilities printUtilities = new PrintUtilities(this);
        printUtilities.setOrientation(orientation);
        try {
            printUtilities.printComponent(this);
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showConfirmDialog(this, "Error attempting to print", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.repaint();
    }


    public long getScrollValue() {
        return jsmProperties.getScrollBaseTime() + (long) (1000 * (jScrollBar.getValue() / jsmProperties.getFrec()));
    }

    public void setScrollValue(long scrollValue) {
        if (scrollValue <= jsmProperties.getMaxTime() &&
            scrollValue >= jsmProperties.getScrollBaseTime()) {
            int newValue = Math.round(((scrollValue -
                 jsmProperties.getScrollBaseTime()) * jsmProperties.getFrec()) /1000f);
            jScrollBar.setValue(newValue);
            if (jScrollBar.getValue()!=newValue) {
                //hay que aplicar correcci+n por culpa del VisibleAmount
                jScrollBar.setMaximum(newValue+jScrollBar.getVisibleAmount());
                 jScrollBar.setValue(newValue);
            }
            refreshData();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().equals(splitPanel)) {
            splitPanel3.setDividerLocation((Integer) (evt.getNewValue()));
        } else if (evt.getSource().equals(splitPanel3)) {
            splitPanel.setDividerLocation((Integer) (evt.getNewValue()));
        }
    }

    public void scrollValueChanged(JSignalMonitorScrollEvent evt) {
        setScrollValue(evt.getScrollValue());
    }

    public Channels getChannels() {
        return channels;
    }

    public void refreshData() {

        channels.refreshData(jsmProperties.getDataSource(), getFirstVisibleTime(),
                             getLastVisibleTime(), jsmProperties.getFrec());
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                channels.repaint();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

        splitPanel3.refreshAnnotations(getFirstVisibleTime(), getLastVisibleTime());

    }

    public void refresh() {
        channels.sizeChange();
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                splitPanel3.refreshCategories();

                refreshData();
                refreshScrollBar();
                refreshLeftPanel();
                channels.refreshGridsConfig();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

    }

    /** actualiza los valores del scroll. Debe ser invocado cuando varie el maximo o minimo valor
     *  representado o cuando se modifique el tamanho del panel*/
    public void refreshScrollBar() {
        //long tempValue=getScrollValue();
        //System.out.println(TimeRepresentation.timeToString(tempValue));
        int temp = Math.round(((jsmProperties.getMaxTime() - jsmProperties.getScrollBaseTime()) * jsmProperties.getFrec()) /
                              1000f);
        if (jScrollBar.getMaximum() != temp && temp > 0) {
            if (jScrollBar.getValue() > temp) {
                jScrollBar.setValue(temp);
            }
            jScrollBar.setMaximum(temp);
        }
        float maxMinTimeDif = ((jsmProperties.getMaxTime() - jsmProperties.getScrollBaseTime()) / jsmProperties.getFrec());
        float maxMinVisibleDif = ((getLastVisibleTime() - getFirstVisibleTime()) / jsmProperties.getFrec());
        if (maxMinTimeDif > 0) {
            jScrollBar.setVisibleAmount(Math.round(jScrollBar.getMaximum() * (maxMinVisibleDif / maxMinTimeDif)));
        }

        //jsmProperties.setScrollValue(tempValue);
        //System.out.println(TimeRepresentation.timeToString(getScrollValue()));
        //jScrollBar.setMaximum(jScrollBar.getMaximum());
    }

    private long getFirstVisibleTime() {
        //return jsmProperties.getScrollBaseTime() + 1000*(long)(jScrollBar.getValue() / jsmProperties.getFrec());
        return getScrollValue();
    }

    private long getLastVisibleTime() {
        //return getFirstVisibleTime() +1000*((long)((channels.getSize().getWidth()+10-channels.getHLeftOffsetScale())/jsmProperties.getFrec()));
        return jsmProperties.getTimeAtLocation((channels.getSize().width - channels.getHLeftOffsetScale() - 10));
    }

    public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
        jsmProperties.setScrollValue(getScrollValue());
        refreshData();
    }

    public long getScrollBaseTime() {
        return jsmProperties.getScrollBaseTime();
    }

    public float getDataRate() {
        return jsmProperties.getFrec();
    }

    public void setDataRate(float rate) {
        jsmProperties.setFrec(rate);
        refreshData();
    }

    public void setVerticalZoom(String signalName, float value) {
        channels.vZoomToSignal(signalName, value);
    }

    public int getVerticalZoom(String signalName) {
        return channels.getVerticalZoom(signalName);
    }

    public void setJsmProperties(JSMProperties jsmProperties) {
        this.jsmProperties = jsmProperties;
        channels.setJsmProperties(jsmProperties);
    }

    public ChannelProperties getChannelProperties(String ChannelName) {
        return channels.getChannelProperties(ChannelName);
    }

    private void setXYInfoPanelVisible(boolean visible) {
        for (int index = 0; index < channels.getChannelsSize(); index++) {
            if (configPanels.get(channels.getChannelAtIndex(index)) instanceof ChannelInfoPanel) {
                ChannelInfoPanel cip = (ChannelInfoPanel) (configPanels.get(channels.getChannelAtIndex(index)));
                cip.setPointVisible(visible);
            }
        }
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                leftPanel.repaint();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

    }

    public boolean addChannel(Channel c) {
        boolean flag = channels.addChannel(c);
        if (flag) {
            ChannelInfoPanel cip = new ChannelInfoPanel(c, this);
            jsmProperties.addMouseTimeChangeListener(cip);
            configPanels.put(c.getChannelProperties().getName(), cip);
            setXYInfoPanelVisible(jsmProperties.isRepresentingValues());
        }
        return flag;
    }

    public boolean removeChannel(String channelName) {
        boolean flag = channels.removeChannel(channelName);
        if (flag) {
            JPanel jp = configPanels.get(channelName);
            if (jp instanceof MouseTimeChangeListener) {
                jsmProperties.removeMouseTimeChangeListener(
                        (MouseTimeChangeListener) jp);
            }
            if (configPanels.remove(channelName) == null) {
                flag = false;
            }
        }
        return flag;
    }

    public JSignalMonitorDataSource getDataSource() {
        return jsmProperties.getDataSource();
    }

    public void refreshLeftPanel() {

        int channelSize = channels.getChannelsSize();
        leftPanel.removeAll();
        gl.setRows(channelSize);
        gl.setVgap(10);
        for (int index = 0; index < channelSize; index++) {
            JPanel p = configPanels.get(channels.getChannelAtIndex(index));
            if (p != null) {
                p.setBorder(new LineBorder(Color.BLACK));
                if (p instanceof ChannelInfoPanel) {
                    ((ChannelInfoPanel) p).refreshValues();
                    ((ChannelInfoPanel) p).refreshFields();
                }
                leftPanel.add(p, index);
            }
        }
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                leftPanel.validate();
                leftPanel.repaint();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

    }

    public int getChannelHeight() {
        return channels.getChannelHeight();
    }

    /**
     *@return numero de canales cargados
     */
    public int getChannelsSize() {
        return channels.getChannelsSize();
    }

    public int getChannelPosition(String channelName) {
        return channels.getChannelPosition(channelName);
    }

    public String getChannelAtIndex(int index) {
        return channels.getChannelAtIndex(index);
    }

    public void swapChannelsPositions(String channel1, String channel2) {
        channels.swapPositions(channel1, channel2);
    }

    public boolean hasChannel(String channelName) {
        return channels.hasChannel(channelName);
    }

    public void resetChannels() {
        for (String channelName : channels.getChannelNames()) {
            removeChannel(channelName);
        }
    }

    public JSignalMonitorGrid getChannelGrid(String channelName) {
        return channels.getChannelGrid(channelName);
    }

    public void setChannelGrid(String channelName, JSignalMonitorGrid jsmGrid) {
        channels.setChannelGrid(channelName, jsmGrid);
    }

    public JSMProperties getJSignalMonitorProperties() {
        return jsmProperties;
    }

    public long getVisibleTime() {
        long start = jsmProperties.getScrollValue();
        long end = jsmProperties.getTimeAtLocation((channels.getSize().width - channels.getHLeftOffsetScale() - 10));
        return end - start;
    }

    public void showAnnotationsPanel(boolean show) {
        if (show) {
            splitPanel2.setDividerLocation(splitPanel2.getLastDividerLocation());
        } else {
            splitPanel2.setDividerLocation(0);
        }
    }

    public void showLeftPanel(boolean show) {
        if (show) {
            splitPanel.setDividerLocation(splitPanel.getLastDividerLocation());
        } else {
            splitPanel.setDividerLocation(0);
            jScrollBar.setVisible(false);
        }
    }

    public int getVScaleOffset() {
        return channels.getVScaleOffset();
    }

    public void setVScaleOffset(int vScaleOffset) {
        channels.setVScaleOffset(vScaleOffset);
    }

    public int getHLeftOffsetScale() {
        return channels.getHLeftOffsetScale();
    }

    public void setHLeftOffsetScale(int hLeftOffsetScale) {
        channels.setHLeftOffsetScale(hLeftOffsetScale);
    }

    public float getFrecForFullView() {
        return jsmProperties.getFrecForFullView(
                channels.getSize().width - channels.getHLeftOffsetScale() - 10);
    }

    public float getFrecForTimeInterval(long startTime, long endTime) {
        return jsmProperties.getFrecForTimeInterval(startTime, endTime,
                channels.getSize().width - channels.getHLeftOffsetScale() - 10);
    }
}
