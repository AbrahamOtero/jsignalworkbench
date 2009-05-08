/*
 * LoaderRunner.java
 *
 * Created on 11 de septiembre de 2007, 5:19
 */

package net.javahispano.jsignalwb.plugins;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Román Segador
 */
public class LoaderRunner extends SwingWorker<Boolean, Void>{
    private Loader loader;
    private File file;
    public LoaderRunner(Loader loader,File file) {
        this.loader=loader;
        this.file=file;
    }

    protected Boolean doInBackground() throws Exception {
        try {
            loader.load(file,JSWBManager.getJSWBManagerInstance());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    JSWBManager.getJSWBManagerInstance().getParentWindow(), 
                    "Error loading..."+ex.getMessage());
        }
        return Boolean.valueOf(true);
    }

     protected void done() {
        //super.done();
        Boolean end = Boolean.valueOf(false);
        try {
            end = get();
        } catch (Exception e) {
            if (!isCancelled()) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                  "Ha sucedido un error al ejecutar el cargador "+
                  loader.getName()+" versión "+
                  loader.getPluginVersion(),
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

        }
        /*if (algorithm.hasResultsGUI()) {
            algorithm.launchResultsGUI(jswbManager);
                 }*/
        if (end.booleanValue()) {
            JSWBManager.getJSWBManagerInstance().refreshJSM(false);
        }
    }

}
