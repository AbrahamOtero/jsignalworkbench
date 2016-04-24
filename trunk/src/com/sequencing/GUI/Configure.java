package com.sequencing.GUI;




import com.sequencing.Sequencing;
import java.awt.Color;
import static java.lang.Boolean.FALSE;
import javax.swing.JColorChooser;
import net.javahispano.jsignalwb.JSWBManager;

public class Configure extends javax.swing.JDialog {

    /**
     * Creates new form Configure
     */
    public Configure() {
        initComponents();
        heightText.setText(Integer.toString(Sequencing.HEIGHT));
        heightSlider.setValue(Sequencing.HEIGHT);
        this.setLocationRelativeTo(JSWBManager.getParentWindow());
        setVisible(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colourDialog = new javax.swing.JDialog();
        colourWindow = new javax.swing.JColorChooser();
        confirmButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        heightSlider = new javax.swing.JSlider();
        heightText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        colourPanel = new javax.swing.JPanel();
        chooseButton = new javax.swing.JButton();
        modifyButton = new javax.swing.JButton();

        colourDialog.setMinimumSize(new java.awt.Dimension(500, 250));

        colourWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colourWindowMouseClicked(evt);
            }
        });
        colourDialog.getContentPane().add(colourWindow, java.awt.BorderLayout.CENTER);

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });
        colourDialog.getContentPane().add(confirmButton, java.awt.BorderLayout.PAGE_END);

        getContentPane().setLayout(new java.awt.GridLayout(0, 2));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HEIGHT:");
        getContentPane().add(jLabel1);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

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

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CHOOSE COLOUR:");
        getContentPane().add(jLabel3);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        colourPanel.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.add(colourPanel);

        chooseButton.setText("Choose");
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButtonActionPerformed(evt);
            }
        });
        jPanel2.add(chooseButton);

        getContentPane().add(jPanel2);

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
        Sequencing.setHEIGHT(Integer.parseInt(heightText.getText()));
        
        Color colour = colourPanel.getBackground();
        Sequencing.setCOLOUR(colour);
        
        this.setVisible(FALSE);
    }//GEN-LAST:event_modifyButtonActionPerformed

    private void chooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButtonActionPerformed
        colourDialog.setVisible(true);
    }//GEN-LAST:event_chooseButtonActionPerformed

    private void colourWindowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colourWindowMouseClicked
        
    }//GEN-LAST:event_colourWindowMouseClicked

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        colourPanel.setBackground(colourWindow.getColor());
        colourDialog.update(null);
        
        this.colourDialog.setVisible(FALSE);
    }//GEN-LAST:event_confirmButtonActionPerformed

   
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configure().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooseButton;
    private javax.swing.JDialog colourDialog;
    private javax.swing.JPanel colourPanel;
    private javax.swing.JColorChooser colourWindow;
    private javax.swing.JButton confirmButton;
    private javax.swing.JSlider heightSlider;
    private javax.swing.JTextField heightText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton modifyButton;
    // End of variables declaration//GEN-END:variables
}
