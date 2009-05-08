/*
 * DefaultIntervalMarkYellow.java
 *
 * Created on 9 de julio de 2007, 12:06
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Román Segador
 */
public class DefaultIntervalMarkYellow extends DefaultIntervalMark{
    
    public DefaultIntervalMarkYellow() {
        super();
    }
    
    public String getName() {
        return "Default Interval Mark Yellow";
    }
     public Image getImage() {
        BufferedImage im=new BufferedImage(5,15,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d=im.createGraphics();
        
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(0,0,5,15);
        return im;
    }
    
}
