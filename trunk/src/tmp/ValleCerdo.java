package tmp;

import net.javahispano.jsignalwb.Signal;
import java.awt.Image;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import java.awt.Graphics2D;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import java.awt.Window;
import java.awt.image.BufferedImage;
import net.javahispano.jsignalwb.plugins.MarkPlugin;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class ValleCerdo extends MarkPluginAdapter implements Comparable {
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
    private int borderTransparencyLevel = 200;
    public ValleCerdo() {
        markTime = 0;
        endTime = 0;
        title = "Write here the mark title...";
        comentary = "Write here your comentary....";
        color = Color.BLUE;
        jswbManager = null;
        refreshBufferedImage();
    }

    public String getName() {
        return "Valle";
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

        g2d.setStroke(new BasicStroke(2));

        int x = markPaintInfo.getPoint().x;
        int y = maxY - extraheightPixels - 2;
        int width = markPaintInfo.getWidth();
        int height = minY - maxY + 2 * extraheightPixels + 3;
        g2d.draw(new java.awt.geom.RoundRectangle2D.Float(x, y, width,
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
    public void showMarkInfo(Window owner) {
       //  new DefaultIntervalMarkInfoPanel(signal, this).showJWindow(owner);
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getToolTipText() {
        return title;
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
