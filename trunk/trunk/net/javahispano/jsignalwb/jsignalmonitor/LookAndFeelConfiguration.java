package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.Color;
import java.awt.Font;

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
public class LookAndFeelConfiguration {

    private Font smallFont = new java.awt.Font("Tahoma", Font.BOLD, 11),
    mediumFont = new java.awt.Font("Tahoma", Font.BOLD, 11),
    largeFont = new java.awt.Font("Tahoma", Font.BOLD, 11);
    private Color colorFont = Color.BLUE;

    public Color getColorFont() {
        return colorFont;
    }

    public Font getLargeFont() {
        return largeFont;
    }

    public Font getMediumFont() {
        return mediumFont;
    }

    public Font getSmallFont() {
        return smallFont;
    }

    public void setSmallFont(Font smallFont) {
        this.smallFont = smallFont;
    }

    public void setMediumFont(Font mediumFont) {
        this.mediumFont = mediumFont;
    }

    public void setLargeFont(Font largeFont) {
        this.largeFont = largeFont;
    }

    public void setFontColor(Color colorFont) {
        this.colorFont = colorFont;
    }
}
