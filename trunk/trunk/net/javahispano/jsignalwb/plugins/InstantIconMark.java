/*
 * InstantIconMark.java
 *
 * Created on 9 de julio de 2007, 13:49
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Román Segador
 */
public class InstantIconMark extends DefaultInstantMark{
    
    public InstantIconMark() {
        super();
        setIsImage(true);
    }

    public String getName() {
        return "Instant Icon Mark";
    }    
}
