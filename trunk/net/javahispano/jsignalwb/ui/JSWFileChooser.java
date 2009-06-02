/*
 * JSWFileChooser.java
 *
 * Created on 12 de junio de 2007, 19:27
 */

package net.javahispano.jsignalwb.ui;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.*;

import net.javahispano.jsignalwb.EnvironmentConfiguration;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Loader;
import net.javahispano.jsignalwb.plugins.Saver;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

/**
 *
 * @author Roman Segador
 */
public class JSWFileChooser extends JFileChooser implements PropertyChangeListener {
    PluginManager pm;
    // ArrayList<FileChooserFileFilter> filters;
    boolean isDefaultFilter;
    FileChooserFileFilter defaultFilter;

    public JSWFileChooser(PluginManager pm) {
        super(".");
        this.pm = pm;
        super.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.addPropertyChangeListener(FILE_FILTER_CHANGED_PROPERTY, this);
        isDefaultFilter = true;
        defaultFilter = null;
        //filters=new ArrayList<FileChooserFileFilter>();
        setAcceptAllFileFilterUsed(false);
    }

    public boolean isTraversable(File f) {
        boolean res = super.isTraversable(f);
        if (isDefaultFilter) {
            if (f.getName().toLowerCase().endsWith(".jsw")) {
                //File parent=this.getSelectedFile().getParentFile();
                this.changeToParentDirectory();
                //super.setSelectedFile(parent);
            }
        }
        return res;
    }

    public Icon getIcon(File f) {
        if (isDefaultFilter) {
            FileFilter ff = new FileFilter() {
                public boolean accept(File f) {

                    if (f.getName().toLowerCase().endsWith(".jsw")) {
                        //System.out.println(name);
                        return true;
                    } else {
                        return false;
                    }

                }
            };
            if (f.isDirectory() && f.listFiles(ff) != null && f.listFiles(ff).length > 0) {
                return new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                        JSWBManager.class.getResource("images/jswIcon.jpg")));
                //return new ImageIcon(("c:/proyecto/JSW1/misc/notAvailable.jpg"));
            }
        }
        return super.getIcon(f);
    }

    public int showOpenDialog(Component parent) throws HeadlessException {
        this.resetChoosableFileFilters();

        for (Loader loader : pm.getAllLoaders()) {
            FileChooserFileFilter fcff = new FileChooserFileFilter(loader, loader.getAvalaibleExtensions());
            //filters.add(fcff);
            addChoosableFileFilter(fcff);
            final String defaultLoader = EnvironmentConfiguration.getInstancia().getDefaultLoader();
            if (fcff.getPlugin().getName().equals(defaultLoader)) {
                defaultFilter = fcff;
            }
            setFileFilter(defaultFilter);

        }
        return super.showOpenDialog(parent);
    }

    public int showSaveDialog(Component parent) throws HeadlessException {
        this.resetChoosableFileFilters();

        for (Saver saver : pm.getAllSavers()) {
            FileChooserFileFilter fcff = new FileChooserFileFilter(saver, saver.getAvalaibleExtension());
            //filters.add(fcff);
            addChoosableFileFilter(fcff);
            if (fcff.getPlugin().getName().equals("defaultSaver")) {
                defaultFilter = fcff;
            }
            setFileFilter(defaultFilter);

        }
        return super.showSaveDialog(parent);
    }


    /*public File getSelectedFile() {
        File f=super.getSelectedFile();
        /*if(isDefaultFilter){
              FileFilter ff=new FileFilter() {
                public boolean accept(File f) {

                    if (f.getName().toLowerCase().endsWith(".jsw")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
             if(f!=null&&f.isDirectory()&&f.listFiles(ff)!=null&&f.listFiles(ff).length>0){
                return f.listFiles(ff)[0];
            }
         }
         return f;
          }*/

     public FileChooserFileFilter getFileChooserFileFilter() {
         if (getFileFilter() instanceof FileChooserFileFilter) {
             return (FileChooserFileFilter) getFileFilter();
         } else {
             return null;
         }
     }

    public boolean isLoaderSelected() {
        FileChooserFileFilter ff = getFileChooserFileFilter();
        return (ff != null && (ff.getPlugin() instanceof Loader));
    }

    public boolean isSaverSelected() {
        FileChooserFileFilter ff = getFileChooserFileFilter();
        return (ff != null && (ff.getPlugin() instanceof Saver));
    }

    public Loader getLoaderSelected() {
        FileChooserFileFilter ff = getFileChooserFileFilter();
        if (isLoaderSelected()) {
            return (Loader) ff.getPlugin();
        }
        return null;
    }

    public Saver getSaverSelected() {
        FileChooserFileFilter ff = getFileChooserFileFilter();
        if (isSaverSelected()) {
            return (Saver) ff.getPlugin();
        }
        return null;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        if (defaultFilter != null && newValue != null && newValue.equals(defaultFilter)) {
            isDefaultFilter = true;
        }
    }
}
