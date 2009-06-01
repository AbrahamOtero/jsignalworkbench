/*
 * PluginAdapter.java
 *
 * Created on 23 de mayo de 2007, 19:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

import net.javahispano.jsignalwb.*;

/**
 * Clase que implementa prácticamente todos los métodos de {@link  Plugin}
 * proporcionando un comportamiento por efecto. Se recomienda extender esta
 * clase en vez de incrementar {@link Plugin}, especialmente para la
 * construcción de prototipos que no estén muy integrados con JSignalWorkbench.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public abstract class PluginAdapter implements Plugin {

    /**
     * Proporciona una descripcion corta del plugin
     *
     * @return Emplea el propio nombre del club y como descripcion
     */
    public String getShortDescription() {
        return getName();
    }

    /**
     * Proporciona una descripcion mas extensa del plugin
     *
     * @return Emplea el propio nombre del club y como descripcion
     */
    public String getDescription() {
        return getName();
    }

    /**
     * Proporciona un icono por defecto en caso de que el algoritmo no contenga
     * uno propio.
     *
     * @return Icon
     */
    public Icon getIcon() {
        return generateImage(getName());
    }

    /**
     * Por defecto no proporciona una interfaz de usuario de configuracion
     *
     * @return false
     */
    public boolean hasOwnConfigureGUI() {
        return false;
    }


    public void launchConfigureGUI(JSWBManager jswbManager) {
        throw new UnsupportedOperationException();
    }

    /**
     * Por defecto devuelve la version 1.0
     *
     * @return 1.0
     */
    public String getPluginVersion() {
        return "1.0";
    }

    /**
     * hasDataToSave
     *
     * @return False
     */
    public boolean hasDataToSave() {
        return false;
    }


    public String getDataToSave() {
        throw new UnsupportedOperationException();
    }

     public void setSavedData(String data) {
        throw new UnsupportedOperationException();
    }

    public boolean createFile() {
        return false;
    }

    public void setFile(File file) {
        throw new UnsupportedOperationException();
    }

    /**
     * Genera un icono con la primera y última letra de la cadena de texto que
     * se le pasa.
     *
     * @param name String
     * @return Icon
     */
    protected Icon generateImage(String name){
        if (name.equals("")) {
            name= "No icon";
        }
        name=name.toUpperCase();
        BufferedImage bufferedImage=new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);
        char first=name.charAt(0);
        char last=name.charAt(name.length()-1);
        Graphics2D g2d=bufferedImage.createGraphics();
        Font font=new Font(Font.SANS_SERIF, Font.BOLD, 13);
        g2d.setBackground(Color.LIGHT_GRAY);
        g2d.clearRect(0,0,20,20);
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(first),1,10);
        g2d.drawString(String.valueOf(last),10,20);
        return new ImageIcon(bufferedImage);
    }

}
