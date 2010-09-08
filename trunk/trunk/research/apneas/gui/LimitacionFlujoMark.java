package research.apneas.gui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import research.apneas.LimitacionFlujo;

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
public class LimitacionFlujoMark extends DefaultIntervalMark {
    private String title;
    private final int extraheightPixels = 10;
    private LimitacionFlujo limitacionFlujo;

    /**
     * La limitacionFlujo se le pasa por referencia; si es modificada se modificara en el entorno.
     *
     * @param limitacionFlujo LimitacionFlujo
     */
    public LimitacionFlujoMark(LimitacionFlujo limitacionFlujo, Color color) {
        super.setTitle("Respiratory airflow limitation");
        super.setColor(color);
        this.color = color;
        this.limitacionFlujo = limitacionFlujo;
    }

    public String getName() {
        return "Respiratory airflow limitation " + limitacionFlujo.getDuracion() + " seg.";
    }

    public void showMarkInfo(Window owner) {
        PanelLimitacionFlujo w = new PanelLimitacionFlujo(limitacionFlujo);
        w.showJWindow(owner);
    }

    public boolean hasDataToSave() {
        return true;
    }

    public String getDataToSave() {
        return super.getDataToSave();
    }

    public void setSavedData(String data) {
        super.setSavedData(data);
    }

    public String getToolTipText() {
        return title;
    }

    public boolean isOwnPainted() {
        return true;
    }

    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        Stroke oldStroke = g2d.getStroke();
        Color color2 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), 50);
        Color color3 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), 150);
        int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
                                  markPaintInfo.getMaxValueY());
        int minY = (int) Math.min(markPaintInfo.getPoint().getY() +
                                  markPaintInfo.getHeight(),
                                  markPaintInfo.getMinValueY());
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

}
