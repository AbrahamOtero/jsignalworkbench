package net.javahispano.jsignalwb.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

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
public class OpenFileAndAddSignalsAction extends OpenFileAction {
    /**
     * Creates a new instance of OpenFileAction
     * @param jswbManager JSWBManager
     */
    public OpenFileAndAddSignalsAction(JSWBManager jswbManager) {
        super(jswbManager);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
        this.putValue(NAME, "Add Signals");
        Image image = Toolkit.getDefaultToolkit().createImage(
                JSWBManager.class.getResource("images/load.jpg"));
        Icon icon = new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, icon);
        this.jswbManager.setDeleteSignalsInNextLoad(false);
    }

    protected void loadData(File file) {
        this.jswbManager.setDeleteSignalsInNextLoad(false);
        jswbManager.loadChannels(chooser.getLoaderSelected().getName(), file);
    }


}
