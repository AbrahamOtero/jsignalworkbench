/*
 * InfoLabel.java
 *
 * Created on 19 de febrero de 2007, 20:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;

/**
 *
 * @author roman
 */
class InfoLabel {
    //private String name;
    //private String magnitude;
    private int infoHOffset;
    private int infoVOffset;
    private int infoFieldsOffset;


    public InfoLabel() {
        this(20, 20, 20);
    }

    public InfoLabel(int infoHOffset,
                     int infoVOffset,
                     int infoFieldsOffset) {
        //setName(name);
        //setMagnitude(magnitude);
        setInfoHOffset(infoHOffset);
        setInfoVOffset(infoVOffset);
        setInfoFieldsOffset(infoFieldsOffset);
    }

    public void paintInfoLabel(Graphics2D g2d, Point p, String name, String magnitude) {
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));

        g2d.drawString("Name: " + name, (float) (p.getX() + getInfoHOffset()),
                       (float) (p.getY() + getInfoVOffset()));
        g2d.drawString("Magnitude: " + magnitude,
                       (float) (p.getX() + getInfoHOffset()),
                       (float) (p.getY() + getInfoVOffset() +
                                getInfoFieldsOffset()));
        /* g2d.drawString("Date:"+TimePositionConverter.timeToDateString(valX),(float)(p.getX()+getInfoHOffset()),(float)(p.getY()+getInfoVOffset()+2*getInfoFieldsOffset()));
         g2d.drawString("Time:"+TimePositionConverter.timeToHourString(valX),(float)(p.getX()+getInfoHOffset()),(float)(p.getY()+getInfoVOffset()+3*getInfoFieldsOffset()));
         g2d.drawString("Value:"+valY,(float)(p.getX()+getInfoHOffset()),(float)(p.getY()+getInfoVOffset()+4*getInfoFieldsOffset()));*/
    }

    /* public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getMagnitude() {
         return magnitude;
     }

     public void setMagnitude(String magnitude) {
         this.magnitude = magnitude;
     }*/

    public int getInfoHOffset() {
        return infoHOffset;
    }

    public void setInfoHOffset(int infoHOffset) {
        this.infoHOffset = infoHOffset;
    }

    public int getInfoVOffset() {
        return infoVOffset;
    }

    public void setInfoVOffset(int infoVOffset) {
        this.infoVOffset = infoVOffset;
    }

    public int getInfoFieldsOffset() {
        return infoFieldsOffset;
    }

    public void setInfoFieldsOffset(int infoFieldsOffset) {
        this.infoFieldsOffset = infoFieldsOffset;
    }

}
