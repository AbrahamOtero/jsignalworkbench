/*
 * ChannelProperties.java
 *
 * Created on 17 de abril de 2007, 13:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;


/**
 * no forma parte del API publica.
 *
 * @author Roman
 */
public class ChannelProperties {
    private Stroke dataStroke;
    private float dataRate;
    private long startTime;
    private long endTime;
    private int dataSize;
    private float zoom;
    private Color dataColor;
    private String name;
    private String magnitude;
//    private float abscissaOffset;
    private float abscissaValue;
    private boolean visible;
    private boolean invadeNearChannels;
    private boolean hasColors;
    private float maxValue;
//    private float minValue;
    private float channelHeight;

    /** Creates a new instance of ChannelProperties */
    public ChannelProperties(String name, long startTime, float dataRate, int dataSize) {
        this(name, startTime, dataRate, dataSize, false);
    }

    public ChannelProperties(String name, long startTime, float dataRate, int dataSize,
                             boolean hasColors) {
        setDataStroke(new BasicStroke(1));
        this.name = name;
        this.startTime = startTime;
        this.dataRate = dataRate;
        this.dataSize = dataSize;
        refreshEndTime();
        setZoom(1);
        setDataColor(Color.BLACK);
//        abscissaOffset=1;
        abscissaValue = maxValue = channelHeight = 1;
        visible = true;
        invadeNearChannels = true;
        magnitude = "unknown";
        this.hasColors = hasColors;

    }

    Stroke getDataStroke() {
        return dataStroke;
    }

    void setDataStroke(Stroke dataStroke) {
        this.dataStroke = dataStroke;
    }

    /**
     * getDataRate
     *
     * @return float
     * @duda ?dataRate?
     */
    public float getDataRate() {
        return dataRate;
    }

    public void setDataRate(float dataRate) {
        this.dataRate = dataRate;
        refreshEndTime();
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        if (zoom <= 0) {
            throw new RuntimeException("Try to set a non valid value at zoom" +
                                       " propertie. Must be a positive value");
        }
        float middleValue = (maxValue + abscissaValue) / 2f;
        maxValue = (channelHeight * (1 / zoom)) * 0.5f + middleValue;
        abscissaValue = maxValue - (channelHeight * (1 / zoom));
        this.zoom = zoom;
    }

    public Color getDataColor() {
        return dataColor;
    }

    public void setDataColor(Color dataColor) {
        this.dataColor = dataColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public float getAbscissaOffset() {
//        return abscissaOffset;
//    }

//    public void setAbscissaOffset(float abscissaOffset) {
//        //this.abscissaOffset = abscissaOffset;
//    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        refreshEndTime();
    }

    public boolean hasEmphasis() {
        return hasColors;
    }

    public void setHasEmphasis(boolean hasColors) {
        this.hasColors = hasColors;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public long getEndTime() {
        return endTime;
    }

    private void refreshEndTime() {
        //+1 para curarnos en salud en problemas de redondeo;
        //mas vale que se muestre un poco de senhal de mas que de menos
        endTime = startTime + (long) (((dataSize + 1) / dataRate) * 1000);
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
        refreshEndTime();
    }

    public float getAbscissaValue() {
        return abscissaValue;
    }

    public void setAbscissaValue(float abscissaValue) {

//        if(abscissaValue<minValue){
//                abscissaValue=minValue;
//                abscissaOffset=0;
//            }else if(abscissaValue>maxValue){
//                abscissaValue=maxValue;
//                abscissaOffset=1;
//            }else{
//               abscissaOffset=((maxValue-abscissaValue)/(maxValue-minValue));
//            }
        maxValue = abscissaValue + (channelHeight / zoom);
        this.abscissaValue = abscissaValue;
    }


    public boolean setVisibleRange(float abscissaValue, float maxValue, float range) {
        float newMax = maxValue * range;
        float newMin = abscissaValue * range;
        return setVisibleRange(newMin, newMax);
    }

    public boolean setVisibleRange(float abscissaValue, float maxValue) {
        this.abscissaValue = abscissaValue;
        this.maxValue = maxValue;
        zoom = channelHeight / (maxValue - abscissaValue);
        return true;
    }

    public void refreshChannelHeight(float newChannelHeight) {
        if (newChannelHeight > 0) {
            zoom *= (newChannelHeight / channelHeight);
            channelHeight = newChannelHeight;
        }
    }

    public float getMaxValue() {
        return maxValue;
    }

//    public float getMinValue() {
//        return minValue;
//    }

    public boolean isInvadeNearChannels() {
        return invadeNearChannels;
    }

    public void setInvadeNearChannels(boolean invadeNearChannels) {
        this.invadeNearChannels = invadeNearChannels;
    }

}
