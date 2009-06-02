/*
 * LeftPanelConfiguration.java
 *
 * Created on 3 de septiembre de 2007, 18:53
 */

package net.javahispano.jsignalwb.jsignalmonitor;

/**
 *
 * @author Roman Segador
 */
public class LeftPanelConfiguration {
    private boolean arrowsVisible;
    private boolean nameVisible;
    private boolean magnitudeVisible;
    private boolean frecuencyVisible;
    private boolean zoomVisible;
    private boolean pointVisible;
    public LeftPanelConfiguration() {
        this(true, true, false, false, false, true);
    }

    public LeftPanelConfiguration(boolean arrowsVisible, boolean nameVisible,
                                  boolean magnitudeVisible, boolean frecuencyVisible, boolean zoomVisible,
                                  boolean pointVisible) {
        this.arrowsVisible = arrowsVisible;
        this.nameVisible = nameVisible;
        this.magnitudeVisible = magnitudeVisible;
        this.frecuencyVisible = frecuencyVisible;
        this.zoomVisible = zoomVisible;
        this.pointVisible = pointVisible;
    }

    public boolean isArrowsVisible() {
        return arrowsVisible;
    }

    public void setArrowsVisible(boolean arrowsVisible) {
        this.arrowsVisible = arrowsVisible;
    }

    public boolean isNameVisible() {
        return nameVisible;
    }

    public void setNameVisible(boolean nameVisible) {
        this.nameVisible = nameVisible;
    }

    public boolean isMagnitudeVisible() {
        return magnitudeVisible;
    }

    public void setMagnitudeVisible(boolean magnitudeVisible) {
        this.magnitudeVisible = magnitudeVisible;
    }

    public boolean isFrecuencyVisible() {
        return frecuencyVisible;
    }

    public void setFrecuencyVisible(boolean frecuencyVisible) {
        this.frecuencyVisible = frecuencyVisible;
    }

    public boolean isZoomVisible() {
        return zoomVisible;
    }

    public void setZoomVisible(boolean zoomVisible) {
        this.zoomVisible = zoomVisible;
    }

    public boolean isPointVisible() {
        return pointVisible;
    }

    public void setPointVisible(boolean pointVisible) {
        this.pointVisible = pointVisible;
    }

    public boolean[] getProperties() {
        boolean properties[] = new boolean[6];
        properties[0] = arrowsVisible;
        properties[1] = nameVisible;
        properties[2] = magnitudeVisible;
        properties[3] = frecuencyVisible;
        properties[4] = zoomVisible;
        properties[5] = pointVisible;
        return properties;
    }

    public void setProperties(boolean properties[]) {
        if (properties.length != 6) {
            throw new RuntimeException("Left panel configuration needs exactly 6 arguments");
        }
        arrowsVisible = properties[0];
        nameVisible = properties[1];
        magnitudeVisible = properties[2];
        frecuencyVisible = properties[3];
        zoomVisible = properties[4];
        pointVisible = properties[5];
    }


}
