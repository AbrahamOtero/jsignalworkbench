/*
 * OldGridPlugin.java
 *
 * Created on 29 de agosto de 2007, 19:15
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Window;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;

/**
 *
 * @author Roman Segador
 */
public class OldGridPlugin extends GridPluginAdapter {
    private int HBigSpace;
    private int HSmallSpace;
    private Stroke HBigStroke;
    private Stroke HSmallStroke;
    private int HBigSize;
    private int HSmallSize;
    private int VBigSpace;
    private int VSmallSpace;
    private Stroke VBigStroke;
    private Stroke VSmallStroke;
    private int VBigSize;
    private int VSmallSize;
    private int axeOffset;
    private int AxesAbscissa;
    private float abscissaValue;
    private float maxValue;
    private float minValue;
    private Color gridColor;
    private Font font;
    private BufferedImage bi;
    private GridConfiguration gridConfig;


    public OldGridPlugin() {
        this(300, 100, 4, 1, 15, 8, 50, 10, 2, 1, 15, 10, 0, 60);
    }

    public OldGridPlugin(int HBigSpace,
                       int HSmallSpace,
                       int HBigStroke,
                       int HSmallStroke,
                       int HBigSize,
                       int HSmallSize,
                       int VBigSpace,
                       int VSmallSpace,
                       int VBigStroke,
                       int VSmallStroke,
                       int VBigSize,
                       int VSmallSize,
                       int axeOffset,
                       int MaxMinOffset) {
        setHBigSpace(HBigSpace);
        setHSmallSpace(HSmallSpace);
        setHBigStroke(new BasicStroke(HBigStroke));
        setHSmallStroke(new BasicStroke(HSmallStroke));
        setHBigSize(HBigSize);
        setHSmallSize(HSmallSize);
        setVBigSpace(VBigSpace);
        setVSmallSpace(VSmallSpace);
        setVBigStroke(new BasicStroke(VBigStroke));
        setVSmallStroke(new BasicStroke(VSmallStroke));
        setVBigSize(VBigSize);
        setVSmallSize(VSmallSize);
        setAxeOffset(axeOffset);
        setGridColor(new Color(0,0,255,50));
        font=new Font(Font.SANS_SERIF, Font.BOLD, 10);
        bi=new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        this.gridConfig=null;
    }

    public String getName() {
        return "Old Default Grid";
    }

    public int getLeyendWidth() {
        return HSmallSpace;
    }

    public int getLeyendHeight() {
        return VSmallSpace;
    }

    public int getHBigSpace() {
        return HBigSpace;
    }

    public void setHBigSpace(int HBigSpace) {
        this.HBigSpace = HBigSpace;
    }

    public int getHSmallSpace() {
        return HSmallSpace;
    }

    public void setHSmallSpace(int HSmallSpace) {
        this.HSmallSpace = HSmallSpace;
    }

    public Stroke getHBigStroke() {
        return HBigStroke;
    }

    public void setHBigStroke(Stroke HBigStroke) {
        this.HBigStroke = HBigStroke;
    }

    public Stroke getHSmallStroke() {
        return HSmallStroke;
    }

    public void setHSmallStroke(Stroke HSmallStroke) {
        this.HSmallStroke = HSmallStroke;
    }

    public int getHBigSize() {
        return HBigSize;
    }

    public void setHBigSize(int HBigSize) {
        this.HBigSize = HBigSize;
    }

    public int getHSmallSize() {
        return HSmallSize;
    }

    public void setHSmallSize(int HSmallSize) {
        this.HSmallSize = HSmallSize;
    }

    public int getVBigSpace() {
        return VBigSpace;
    }

    public void setVBigSpace(int VBigSpace) {
        this.VBigSpace = VBigSpace;
    }

    public int getVSmallSpace() {
        return VSmallSpace;
    }

    public void setVSmallSpace(int VSmallSpace) {
        this.VSmallSpace = VSmallSpace;
    }

    public Stroke getVBigStroke() {
        return VBigStroke;
    }

    public void setVBigStroke(Stroke VBigStroke) {
        this.VBigStroke = VBigStroke;
    }

    public Stroke getVSmallStroke() {
        return VSmallStroke;
    }

    public void setVSmallStroke(Stroke VSmallStroke) {
        this.VSmallStroke = VSmallStroke;
    }

    public int getVBigSize() {
        return VBigSize;
    }

    public void setVBigSize(int VBigSize) {
        this.VBigSize = VBigSize;
    }

    public int getVSmallSize() {
        return VSmallSize;
    }

    public void setVSmallSize(int VSmallSize) {
        this.VSmallSize = VSmallSize;
    }


    private void paintVAxes(Graphics2D g2d, Point p, int height, int width) {

        /*g2d.setStroke(this.getVBigStroke());

        g2d.draw(new Line2D.Double(p.getX(), p.getY(), p.getX(),
                                   p.getY() + height));
        int space = this.getHBigSpace();
        for (int xPosition = (int) p.getX() + (space - (scrollValue % space));
                             xPosition < p.getX() + width; xPosition += space) {
            g2d.draw(new Line2D.Double(xPosition,
                                       this.getAxesAbscissa() - this.getHBigSize(),
                                       xPosition,
                                       this.getAxesAbscissa() + this.getHBigSize()));
        }
        g2d.setStroke(this.getVSmallStroke());

        int space = this.getHSmallSpace();
        for (int xPosition = (int) p.getX() + (space - (scrollValue % space));
                             xPosition < p.getX() + width; xPosition += space) {
            g2d.draw(new Line2D.Float(xPosition,
                                      this.getAxesAbscissa() - this.getHSmallSize(),
                                      xPosition,
                                      this.getAxesAbscissa() + this.getHSmallSize()));
        }*/
        g2d.setStroke(getVSmallStroke());
        for(int i=0;i<width;i+=getHSmallSpace()){
            g2d.drawLine(i,0,i,height);
        }
    }

    private void paintHAxes(Graphics2D g2d, Point p, int height, int width) {
        /*g2d.setStroke(this.getHBigStroke());

        g2d.draw(new Line2D.Double(p.getX(), this.getAxesAbscissa(),
                                   p.getX() + width, this.getAxesAbscissa()));
        int space = this.getVBigSpace();
        for (int yPosition = this.getAxesAbscissa() + space;
                             yPosition < p.getY() + height; yPosition += space) {
            g2d.draw(new Line2D.Double(p.getX() - this.getVBigSize(), yPosition,
                                       p.getX() + this.getVBigSize(), yPosition));
        }
        for (int yPosition = this.getAxesAbscissa() + space; yPosition > p.getY();
                             yPosition -= space) {
            g2d.draw(new Line2D.Double(p.getX() - this.getVBigSize(), yPosition,
                                       p.getX() + this.getVBigSize(), yPosition));
        }

        g2d.setStroke(this.getHSmallStroke());

        space = this.getVSmallSpace();
        for (int yPosition = this.getAxesAbscissa() + space;
                             yPosition < p.getY() + height;
                             yPosition += space) {
            g2d.draw(new Line2D.Double(p.getX() - this.getVSmallSize(),
                                       yPosition, p.getX() + this.getVSmallSize(),
                                       yPosition));
        }
        for (int yPosition = this.getAxesAbscissa() + space;
                             yPosition > p.getY();
                             yPosition -= space) {
            g2d.draw(new Line2D.Double(p.getX() - this.getVSmallSize(),
                                       yPosition, p.getX() + this.getVSmallSize(),
                                       yPosition));
        }*/
        g2d.setStroke(getHSmallStroke());
        for(int i=0;i<height;i+=getVSmallSpace()){
            g2d.drawLine(0,i,width,i);
        }
    }

    /* Objetivo--> Pintar las etiquetas de los valores máximo y minimo.*/
    public void paintValues(Graphics2D g2d, Point p, int height) {
        g2d.setColor(Color.BLUE);
        g2d.setFont(font);
        g2d.drawString(String.format("Max: %4.2f", getMaxValue()),5,10);
                       //(float) (p.getX()) +5, (float) (p.getY()));
        g2d.drawString(String.format("Min: %4.2f", getMinValue()),5,height-5);
                       //(float) (p.getX() +5), (float) (p.getY() + height));
    }

    public void launchConfigureGridGUI(Window owner) {
        JOptionPane.showMessageDialog(owner,"No configuration available");
    }


    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                            GridConfiguration gridConfig) {
        if(height<=0 || width<=0)
            return;
        if(bi.getHeight()!=height || bi.getWidth()!=width|| this.gridConfig!=gridConfig){
            this.gridConfig=gridConfig;
            bi=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D big2d=bi.createGraphics();
        //int scrollValue = gridConfig.getScrollValue();
        float vZoom = gridConfig.getVZoom();
        float hZoom = gridConfig.getFrec();
        this.setAxesAbscissa(gridConfig.getAbscissaPosition());
        abscissaValue=gridConfig.getAbscissaValue();
        big2d.setColor(Color.WHITE);
        big2d.fillRect(0,0, width,height);
        big2d.setColor(getGridColor());

        setMaxValue(gridConfig.getMaxValue());
        setMinValue(gridConfig.getMinValue());

        this.paintValues(big2d, p, height);
        big2d.setColor(getGridColor());
        this.paintVAxes(big2d, p, height, width);
        this.paintHAxes(big2d, p, height, width);
        g2d.setStroke(getHBigStroke());
        }g2d.drawImage(bi,(int)p.getX(),(int)p.getY(),null);
        g2d.setColor(getGridColor());
        g2d.setStroke(getHBigStroke());
        g2d.drawLine((int)p.getX(),gridConfig.getAbscissaPosition(),width,gridConfig.getAbscissaPosition());

    }

    private int getAxeOffset() {
        return axeOffset;
    }

    private void setAxeOffset(int offset) {
        this.axeOffset = offset;
    }

    private int getAxesAbscissa() {
        return AxesAbscissa;
    }

    private void setAxesAbscissa(int AxesAbscissa) {
        this.AxesAbscissa = AxesAbscissa;
    }

    private float getMaxValue() {
        return maxValue;
    }

    private void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    private float getMinValue() {
        return minValue;
    }

    private void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    private Color getGridColor() {
        return gridColor;
    }

    private void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }




}
