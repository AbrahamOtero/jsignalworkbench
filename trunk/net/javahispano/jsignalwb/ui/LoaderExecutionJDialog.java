/*
 * LoaderExecutionJDialog.java
 *
 * Created on 11 de septiembre de 2007, 6:42
 */

package net.javahispano.jsignalwb.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.SwingWorker;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Loader;
import net.javahispano.jsignalwb.plugins.LoaderRunner;

/**
 * No forma parte de la API
 */
public class LoaderExecutionJDialog extends javax.swing.JDialog
        implements PropertyChangeListener{
    private Loader loader;
    private SwingWorker sw;
    /**
     * Creates new form LoaderExecutionJDialog
     * @param loader {@link Loader} a utilizar para la carga
     * @param file {@link File} indica al {@link Loader} que cargar
     * @param jswbManager {@link JSWBManager} manager general de la aplicación
     */
    public LoaderExecutionJDialog(Loader loader, File file) {
        super(JSWBManager.getJSWBManagerInstance().getParentWindow(),"Loader");
        setModal(true);
        this.loader=loader;
        initComponents();
        JSWBManager jswbManager=JSWBManager.getJSWBManagerInstance();
        LoaderRunner lr=new LoaderRunner(loader,file);
        lr.addPropertyChangeListener(this);
        setLocationRelativeTo(this.getOwner());
        sw=(SwingWorker)lr;
        jswbManager.setJSMIgnoreRepaintMode(true);
        lr.execute();        
        setVisible(true);
        jswbManager.setJSMIgnoreRepaintMode(false);
    }

    /**
     * Detecta cuando ha finalizado la ejecución del {@link Loader }
     * @param evt {@link PropertyChangeEvent} informa del estado de ejecución
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if("state".equals(evt.getPropertyName())){
            if(evt.getNewValue().equals(LoaderRunner.StateValue.DONE))
            this.dispose();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Loading...");

        jProgressBar1.setIndeterminate(true);

        jButton1.setText("Stop");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loader.cancelExecution();
        sw.cancel(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
    
}
