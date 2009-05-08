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
//    private static int pluginIndex = 0;

    /**
     * Proporciona una descripcion corta del plugin
     *
     * @return String
     */
    public String getShortDescription() {
        return getName();
    }

    /**
     * Proporciona una descripcion mas extensa del plugin
     *
     * @return String
     */
    public String getDescription() {
        return getName();
    }

    /**
     * Proporciona un icono por defecto en caso de que el algoritmo no contenga
     * uno propio
     *
     * @return Icon
     */
    public Icon getIcon() {
//        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(
//                JSWBManager.class.getResource("images/notAvailable.jpg")));
        return generateImage(getName());
    }

    /**
     * Por defecto no proporciona una interfaz de usuario de configuracion
     *
     * @return boolean
     */
    public boolean hasOwnConfigureGUI() {
        return false;
    }

    /**
     * Por defecto lanzara una excepcion
     *
     * @param jswbManager JSWBManager
     */
    public void launchConfigureGUI(JSWBManager jswbManager) {
        throw new UnsupportedOperationException();
    }

    /**
     * Por defecto devuelve la version 1.0
     *
     * @return String
     */
    public String getPluginVersion() {
        return "1.0";
    }

    /**
     * hasDataToSave
     *
     * @return boolean
     */
    public boolean hasDataToSave() {
        return false;
    }



    /**
     * getDataToSave
     *
     * @return String
     */
    public String getDataToSave() {
        throw new UnsupportedOperationException();
    }

    /**
     * setSavedData
     *
     * @param data String
     */
    public void setSavedData(String data) {
        throw new UnsupportedOperationException();
    }

    public boolean createFile() {
        return false;
    }

    public void save() {
        throw new UnsupportedOperationException();
    }

    public void setFile(File file) {
        throw new UnsupportedOperationException();
    }

    protected Icon generateImage(String name){
        if (name.equals("")) {
            name= "No icon";
        }
        name=name.toUpperCase();
        BufferedImage bufferedImage=new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);
        char first=name.charAt(0);
        char last=name.charAt(name.length()-1);
        //String length=String.valueOf(name.length()-2);
        Graphics2D g2d=bufferedImage.createGraphics();
        Font font=new Font(Font.SANS_SERIF, Font.BOLD, 13);
        g2d.setBackground(Color.LIGHT_GRAY);
        g2d.clearRect(0,0,20,20);
//        g2d.setFont(font);
//        g2d.setColor(Color.LIGHT_GRAY);
        //g2d.drawString(length,2,18);
        g2d.setColor(Color.RED);
//        font=new Font(Font.SANS_SERIF, Font.BOLD, 13);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(first),1,10);
        g2d.drawString(String.valueOf(last),10,20);
        return new ImageIcon(bufferedImage);
    }

}
