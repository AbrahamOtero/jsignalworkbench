/*
 * Channel.java
 *
 * Created on 20 de diciembre de 2006, 16:23
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;
import java.awt.image.ImageObserver;


/**
 *
 * @author Roman Segador
 */
class Channel implements ImageObserver {
    //private boolean visible;
    private int points[];
    //private int xPoints[];
    private Color colors[] = null;
    private InfoLabel infoLabel;
    private JSignalMonitorGrid grid;
    private int abscissaPosition;
    private ChannelProperties channelProperties;
    private GridConfiguration gridConfig;

    public Channel(String name, ChannelProperties properties) {
        this(properties, new InfoLabel(), new DefaultGrid());
    }

    /**
     * @todo bug - solucionado por abraham ya, creo
     *
     * @param channelProperties ChannelProperties
     * @param infoLabel InfoLabel
     * @param grid JSignalMonitorGrid
     */
    public Channel(ChannelProperties channelProperties, InfoLabel infoLabel,
                   JSignalMonitorGrid grid) {
        points = new int[1];
        // xPoints = new int[1];
        setInfoLabel(infoLabel);
        setGrid(grid);
        setChannelProperties(channelProperties);
        //gridConfig=null;
        //a veces daba una excepcion. Nunca es buena idea dejar un objeto a medias cuando se construye...
        this.refreshGridConfig(4);
    }

    public void setPoints(float[] points) {
        this.points = new int[points.length];
        applyZoom(points);
    }

    private int[] getXPositions(int nPoints, int firstValue) {
        int xPoints[] = new int[nPoints];
        for (int index = 0; index < nPoints; index++, firstValue++) {
            xPoints[index] = firstValue;
        }
        return xPoints;
    }

    public JSignalMonitorGrid getGrid() {
        return grid;
    }

    public void setGrid(JSignalMonitorGrid grid) {
        this.grid = grid;
    }

    private Color getColor(short code) {
        if (code == 0) {
            return Color.BLACK;
        }
        if (code < 20) {
            return Color.GREEN;
        }
        if (code < 40) {
            return Color.YELLOW;
        }
        if (code < 60) {
            return Color.PINK;
        }
        if (code < 80) {
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }

    public void paintData(Graphics2D g2d, Point p) {
        paintData(g2d, p, 0, 0);
    }

    public void paintData(Graphics2D g2d, Point p, int startOffset, int endOffset) {
        //float[] data = this.getPoints();
        g2d.setColor(getChannelProperties().getDataColor());
        g2d.setStroke(getChannelProperties().getDataStroke());
        //System.out.println(data.length+" "+this.getChannelProperties().getName());
        //int mod=position%getChannelProperties().getPointDist();
        int actualValue = (int) p.getX();
        boolean paintColors = channelProperties.hasEmphasis();
        //@todo bug - creo q solucionado
        //bajo ciertas condiciones aunque paintColors= true el array de colores
        //no ha sido inicializado(En tu codigo original en ese caso el array tenia
        //tamanho 1 y el indice se sala de el.
        if (paintColors && colors != null || startOffset != 0 || endOffset != 0) {
            actualValue += startOffset;
            for (int i = startOffset; i < (points.length - 1 - endOffset); ) {
                if (paintColors) {
                    g2d.setColor(colors[i]);
                }
                g2d.drawLine(actualValue, points[i], ++actualValue, points[++i]);
            }
        } else {
            g2d.setColor(this.channelProperties.getDataColor());
            g2d.drawPolyline(getXPositions(points.length, actualValue), points, points.length);
        }
    }

    /*public void paintMark(Graphics g2d,Point p){
        for(int index=0;index<marks.length;index++){
            if(marks[index]!=null){
                g2d.drawImage(marks[index].getImage(), p+ getChannelProperties().,(int)p.getY()+50,this);
            }
        }
         }*/

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }


    public InfoLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(InfoLabel infoLabel) {
        this.infoLabel = infoLabel;
    }

    private void applyZoom(float[] points) {
        float zoom = this.getChannelProperties().getZoom();
        for (int index = 0; index < points.length; index++) {
            this.points[index] = (int) (abscissaPosition -
                                        ((points[index] - getChannelProperties().getAbscissaValue()) * zoom));

        }
    }

    public int getAbscissaPosition() {
        return abscissaPosition;
    }

    public void setAbscissaPosition(int abscissaPosition) {
        this.abscissaPosition = abscissaPosition;
    }

    public boolean isVisible() {
        return channelProperties.isVisible();
    }

    public void setVisible(boolean visible) {
        channelProperties.setVisible(visible);
    }

    public boolean isInvadeNearChannels() {
        return channelProperties.isInvadeNearChannels();
    }

    public void setInvadeNearChannels(boolean invade) {
        channelProperties.setInvadeNearChannels(invade);
    }

    public ChannelProperties getChannelProperties() {
        return channelProperties;
    }

    public void setChannelProperties(ChannelProperties channelProperties) {
        this.channelProperties = channelProperties;
    }

    public boolean setColors(short[] colors) {

        if (this.points.length == colors.length) {
            this.colors = new Color[colors.length];
            for (int index = 0; index < colors.length; index++) {
                this.colors[index] = getColor(colors[index]);
            }
            return true;
        } else {
            return false;
        }
    }

    public void refreshGridConfig(float frec) {
        gridConfig = new GridConfiguration(channelProperties.getMaxValue(),
                                           channelProperties.getAbscissaValue(),
                                           abscissaPosition,
                                           channelProperties.getZoom(),
                                           frec);
    }

    public GridConfiguration getGridConfiguration() {
        return gridConfig;
    }
}
