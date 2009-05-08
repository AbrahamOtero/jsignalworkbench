/*
 * SaverRunner.java
 *
 * Created on 11 de septiembre de 2007, 18:44
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
public class SaverRunner extends SwingWorker<Boolean, Void>{
    private Saver saver;
    private File file;
    public SaverRunner(Saver saver, File file) {
        this.saver = saver;
        this.file = file;
    }

     protected Boolean doInBackground() throws Exception {
        try {
            saver.save(file,JSWBManager.getJSWBManagerInstance());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error ejecutando el plugin "+
                    saver.getName()+"\n"+ex.getMessage());
            ex.printStackTrace();
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
                  saver.getName()+" versión "+
                  saver.getPluginVersion(),
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
