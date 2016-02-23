
package com.sequencing.GUI;

import com.sequencing.Sequencing;
import static java.lang.Boolean.FALSE;

public class Configure extends javax.swing.JFrame {

    /**
     * Creates new form Configure
     */
    public Configure() {
        initComponents();
        heightText.setText(Integer.toString(Sequencing.HEIGHT));
        heightSlider.setValue(Sequencing.HEIGHT);
        widthText.setText(Double.toString(Sequencing.WIDTH));
        conf.show();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        conf = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        heightSlider = new javax.swing.JSlider();
        heightText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        widthText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        modifyButton = new javax.swing.JButton();

        conf.getContentPane().setLayout(new java.awt.GridLayout(0, 2));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(0, 2));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HEIGHT:");
        getContentPane().add(jLabel1);

        jPanel1.setLayout(new java.awt.GridLayout());

        heightSlider.setMaximum(1000);
        heightSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightSliderStateChanged(evt);
            }
        });
        jPanel1.add(heightSlider);

        heightText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        heightText.setMaximumSize(new java.awt.Dimension(200, 60));
        heightText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightTextActionPerformed(evt);
            }
        });
        jPanel1.add(heightText);

        getContentPane().add(jPanel1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("WIDTH:");
        getContentPane().add(jLabel2);

        widthText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(widthText);

        jLabel3.setText("MINIMUM WIDTH:");
        getContentPane().add(jLabel3);
        getContentPane().add(jTextField2);

        modifyButton.setText("MODIFY");
        modifyButton.setMargin(new java.awt.Insets(10, 20, 10, 20));
        modifyButton.setMaximumSize(new java.awt.Dimension(50, 15));
        modifyButton.setMinimumSize(new java.awt.Dimension(50, 15));
        modifyButton.setPreferredSize(new java.awt.Dimension(50, 15));
        modifyButton.setRequestFocusEnabled(false);
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });
        getContentPane().add(modifyButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void heightSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_heightSliderStateChanged
        heightText.setText(Integer.toString(heightSlider.getValue()));
    }//GEN-LAST:event_heightSliderStateChanged

    private void heightTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heightTextActionPerformed
        heightSlider.setValue(Integer.parseInt(heightText.getText()));    }//GEN-LAST:event_heightTextActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        Sequencing.HEIGHT = Integer.parseInt(heightText.getText());
        Sequencing.WIDTH = Double.parseDouble(widthText.getText());
        
        this.setVisible(FALSE);
    }//GEN-LAST:event_modifyButtonActionPerformed

   
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configure().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog conf;
    private javax.swing.JSlider heightSlider;
    private javax.swing.JTextField heightText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton modifyButton;
    private javax.swing.JTextField widthText;
    // End of variables declaration//GEN-END:variables
}
