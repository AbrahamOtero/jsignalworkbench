/*
 * getFileChooserFileFilter.java
 *
 * Created on 20 de junio de 2007, 11:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import net.javahispano.jsignalwb.plugins.Plugin;

/**
 *
 * @author Roman
 */
public class FileChooserFileFilter extends FileFilter {
    Plugin plugin;
    ArrayList<String> extensions;
    /** Creates a new instance of getFileChooserFileFilter */
    public FileChooserFileFilter(Plugin plugin, List<String> extensions) {
        this.plugin = plugin;
        this.extensions = new ArrayList<String>(extensions);
    }

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        for (String ext : extensions) {
            if (f.getName().toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        String desc = plugin.getShortDescription() + " ";
        for (String ext : extensions) {
            if (!"jsw".equals(ext)) {
                desc = desc + "(*." + ext + ")";
            }
        }
        return desc;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
