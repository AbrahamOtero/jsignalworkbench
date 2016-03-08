/*
 * DefaultIntervalMark.java
 *
 * Created on 5 de julio de 2007, 21:22
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;
import net.javahispano.jsignalwb.plugins.MarkPlugin;

/**
 *
 * @author Roman Segador
 */
public class DefaultIntervalMark extends MarkPluginAdapter implements Comparable {
    private long markTime;
    private long endTime;
    private String title;
    private String comentary;
    protected Color color;
    private BufferedImage im;
    private JSWBManager jswbManager;
    private int extraheightPixels = 10;
    private MarkPaintInfo markPaintInfo;
    private int innerTransparencyLevel = 50;
    private int borderTransparencyLevel = 150;
    public DefaultIntervalMark() {
        markTime = 0;
        endTime = 0;
        title = "Write here the mark title...";
        comentary = "Write here your comentary....";
        color = Color.GREEN;
        jswbManager = null;
        refreshBufferedImage();
    }

    public String getName() {
        return "Default Interval Mark";
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    /**
     * Devuelve el numero de milisegundos que han transcurrido desde el 1 de
     * enero de 1970 hasta el principio de la marca.
     *
     * @return long
     */
    public long getMarkTime() {
        return markTime;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isInterval() {
        return true;
    }

    /**
     * Devuelve el numero de milisegundos que han transcurrido desde el 1 de
     * enero de 1970 hasta el fin de la marca.
     *
     * @return long
     */
    public long getEndTime() {
        return endTime;
    }

    public void setJSWBManager(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
    }

    public void showMarkInfo(Window owner) {
        new DefaultIntervalMarkInfoPanel(signal, this).showJWindow(owner);
    }

    public Image getImage() {
        return im;
    }

    public boolean isOwnPainted() {
        return true;
    }

    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        //if(this.markPaintInfo==null || !this.markPaintInfo.equals(markPaintInfo)){
        this.markPaintInfo = markPaintInfo;
        Stroke oldStroke = g2d.getStroke();
        Color color2 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), innerTransparencyLevel);
        Color color3 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), borderTransparencyLevel);
        int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
                                  markPaintInfo.getMaxValueY());
        int minY = (int) Math.min(markPaintInfo.getPoint().getY() +
                                  markPaintInfo.getHeight(),
                                  markPaintInfo.getMinValueY());
        //g2d.fillRect((int)markPaintInfo.getPoint().getX(),maxY,markPaintInfo.getWidth(),minY-maxY);
        g2d.setColor(color3);

        g2d.setStroke(new BasicStroke(3));

        int x = markPaintInfo.getPoint().x;
        int y = maxY - extraheightPixels - 2;
        int width = markPaintInfo.getWidth();
        int height = minY - maxY + 2 * extraheightPixels + 3;
        g2d.draw(new java.awt.geom.RoundRectangle2D.Float(x - 2, y, width + 3,
                height, 15, 15));
        g2d.setColor(color2);
        g2d.fillRect(x, y, width, height);
        //dejamos las cosas tal y como estaban
        g2d.setStroke(oldStroke);
        // }
    }

    public JSWBManager getJSWBManager() {
        return jswbManager;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public String getComentary() {
        return comentary;
    }

    public String getTitle() {
        return title;
    }

    public boolean hasDataToSave() {
        return true;
    }

    public String getDataToSave() {
        return "title:" + title + "|| comentary:" + comentary + "|| color:" +
                color.getRGB();
    }


    public void setSavedData(String data) {
        data = data.substring(data.indexOf("title:") + 6);
        title = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("comentary:") + 10);
        comentary = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("color:") + 6);
        color = new Color(Integer.parseInt(data));
        refreshBufferedImage();
    }

    public String getToolTipText() {
        return title;
    }

    public Color getColor() {
        Color c = new Color(color.getRed(), color.getGreen(), color.getBlue());
        return c;
    }

    /**
     * getExtraheightPixels
     *
     * @return Numero de pixeles que se sumaran y restaran a los valores
     *   maximos y minimos, respectivamente, del intervalo de la marca.
     */
    public int getExtraheightPixels() {
        return extraheightPixels;
    }

    public int getInnerTransparencyLevel() {
        return innerTransparencyLevel;
    }

    public int getBorderTransparencyLevel() {
        return borderTransparencyLevel;
    }

    public void setColor(Color color) {
        this.color = color;
        refreshBufferedImage();
    }

    /**
     *
     * @param extraheightPixels Numero de pixeles que se sumaran y restaran a
     *   los valores maximos y minimos, respectivamente, del intervalo de la
     *   marca.
     */
    public void setExtraheightPixels(int extraheightPixels) {
        this.extraheightPixels = extraheightPixels;
    }

    /**
     * Un valor de transparencia para la marca entre cero y 255.
     *
     * @param innerTransparencyLevel int
     */
    public void setInnerTransparencyLevel(int innerTransparencyLevel) {
        this.innerTransparencyLevel = innerTransparencyLevel;
    }

    /**
     * Un valor de transparencia para el borde de la marca entre cero y 255.
     *
     * @param innerTransparencyLevel int
     */
    public void setBorderTransparencyLevel(int borderTransparencyLevel) {
        this.borderTransparencyLevel = borderTransparencyLevel;
    }

    private void refreshBufferedImage() {
        im = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = im.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 5, 15);
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this
     *   object is less than, equal to, or greater than the specified object.
     * @todo Implement this java.lang.Comparable method
     */
    public int compareTo(Object o) {
        MarkPlugin i = (MarkPlugin) o;
        if (i.getMarkTime() < this.getMarkTime()) {
            return 1;
        } else if (i.getMarkTime() > this.getMarkTime()) {
            return -1;
        }
        return 0;
    }

    public int hashCode() {
        return (int) (this.getMarkTime() | this.getEndTime());
    }
}
