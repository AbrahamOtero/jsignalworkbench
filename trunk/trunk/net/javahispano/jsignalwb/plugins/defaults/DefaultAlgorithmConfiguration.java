/*
 * DefaultAlgorithmConfiguration.java
 *
 * Created on 12 de abril de 2007, 17:11
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.Algorithm;


/**
 *
 * @author  Roman
 */
public class DefaultAlgorithmConfiguration extends javax.swing.JPanel implements
        PropertyChangeListener, IntervalSelectedListener {
    private JSWBManager jswbManager;
    private final Algorithm alg;
    private Window owner;
    private DefaultListModel dlmNo;
    private DefaultListModel dlmYes;
    private int numberSignals;
    private int intervalsNeeded;
    private ArrayList<IntervalSelectedEvent> intervals;
    /** Creates new form DefaultAlgorithmConfiguration */
    public DefaultAlgorithmConfiguration(Algorithm al, JSWBManager jswbManager, Window owner) {
        this.jswbManager = jswbManager;
        this.owner = owner;
        intervals = new ArrayList<IntervalSelectedEvent>();
        List<String>
                signalNames = jswbManager.getSignalManager().getSignalsNames();
        initComponents();
        selectSignals.setSelected(true);
        jSpinner1.setEnabled(false);
        dlmNo = new DefaultListModel();
        dlmYes = new DefaultListModel();
        jList1.setModel(dlmNo);
        jList2.setModel(dlmYes);
        for (String s : signalNames) {
            dlmNo.addElement(s);
        }
        alg = al;
        //jProgressBar1.setVisible(false);
        //jProgressBar1.setValue(0);
        jButton2.setEnabled(false);
        jButton5.setEnabled(false);
        numberSignals = alg.numberOfSignalsNeeded();
        jLabel3.setText("Algorithm: " + alg.getName());
        if (numberSignals > 0) {
            jLabel4.setText("Maximum number of signals(or intervals): " + numberSignals);
        } else if (numberSignals == 0) {
            jLabel4.setText("Maximum number of signals(or intervals): No limit");
        } else {
            jLabel4.setText("Minimum number of signals(or intervals): " + numberSignals * ( -1));
        }
        if (numberSignals < 0) {
            jButton3.setEnabled(false);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            //jProgressBar1.setIndeterminate(false);
            //jProgressBar1.setValue((Integer) evt.getNewValue());
        }
    }

    public void intervalSelectedActionPerformed(IntervalSelectedEvent evt) {

        intervalsNeeded--;
        if (intervalsNeeded > 0) {
            System.out.println("quedan " + intervalsNeeded + " intervalos por seleccionar");
            intervals.add(evt);
            jswbManager.selectInterval(this);
        } else {
            intervals.add(evt);
            jswbManager.runAlgorithm(alg, intervals);
            intervals.clear();
        }
    }


    private void selectIntervals(int number) {
        intervalsNeeded = number;
        if (intervalsNeeded > 0) {
            jswbManager.selectInterval(this);
        } else {
            jswbManager.runAlgorithm(alg, intervals);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        selectSignals = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        selectInterval = new javax.swing.JCheckBox();
        jButton6 = new javax.swing.JButton();

        jButton3.setForeground(java.awt.Color.blue);
        jButton3.setText("Run Algorithm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Signals"));
        selectSignals.setForeground(java.awt.Color.blue);
        selectSignals.setText("Select complete signals");
        selectSignals.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        selectSignals.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        selectSignals.setMargin(new java.awt.Insets(0, 0, 0, 0));
        selectSignals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionModeActionPerformed(evt);
            }
        });

        jScrollPane1.setMinimumSize(new java.awt.Dimension(0, 0));
        jList1.setForeground(java.awt.Color.blue);
        jScrollPane1.setViewportView(jList1);

        jButton1.setText(">>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText(">");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setText("<<");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setMinimumSize(new java.awt.Dimension(0, 0));
        jList2.setForeground(java.awt.Color.blue);
        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(selectSignals, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                              jPanel2Layout.createSequentialGroup()
                                              .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.LEADING, false)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              Short.MAX_VALUE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
                javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1,
                               jScrollPane2});

        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(selectSignals)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING, false)
                                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                 javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
                                              .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
                javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                          .addContainerGap(18, Short.MAX_VALUE))
                );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Intervals"));
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Intervals");

        jSpinner1.setModel(new SpinnerNumberModel(0, 0, 100, 1));

        selectInterval.setForeground(java.awt.Color.blue);
        selectInterval.setText("Select signals intervals ");
        selectInterval.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        selectInterval.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        selectInterval.setMargin(new java.awt.Insets(0, 0, 0, 0));
        selectInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionModeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(selectInterval)
                          .addGap(25, 25, 25)
                          .addComponent(jLabel5)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 49,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(125, Short.MAX_VALUE))
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectInterval)
                                    .addComponent(jLabel5)
                                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        jButton6.setForeground(java.awt.Color.blue);
        jButton6.setText("Cancel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                          .addGap(80, 80, 80)
                          .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                          .addGap(80, 80, 80))
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                          .addGap(175, 175, 175)
                          .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGap(177, 177, 177))
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addGap(14, 14, 14)
                          .addComponent(jButton3)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jButton6)
                          .addContainerGap(48, Short.MAX_VALUE))
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton6ActionPerformed
        owner.setVisible(false);
        owner.dispose();
    } //GEN-LAST:event_jButton6ActionPerformed

    private void selectionModeActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_selectionModeActionPerformed
        if (evt.getSource().equals(selectInterval)) {
            this.jSpinner1.setEnabled(selectInterval.isSelected());
            //this.jLabel5.setVisible(selectInterval.isSelected());
        } else if (evt.getSource().equals(selectSignals)) {
            jList1.setEnabled(selectSignals.isSelected());
            jList2.setEnabled(selectSignals.isSelected());
            jButton1.setEnabled(selectSignals.isSelected());
            jButton2.setEnabled(selectSignals.isSelected());
            jButton4.setEnabled(selectSignals.isSelected());
            jButton5.setEnabled(selectSignals.isSelected());
        }
    } //GEN-LAST:event_selectionModeActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton5ActionPerformed
        if (!jList2.isSelectionEmpty()) {
            Object[] selected = jList2.getSelectedValues();
            for (int index = 0; index < selected.length; index++) {
                dlmNo.addElement(selected[index]);
                dlmYes.removeElement(selected[index]);
            }
        }
        if (dlmYes.size() < numberSignals || numberSignals <= 0) {
            jButton4.setEnabled(true);
            jButton1.setEnabled(true);
        }
        if (dlmYes.size() == 0) {
            jButton5.setEnabled(false);
            jButton2.setEnabled(false);
        }
        if (numberSignals < 0 && dlmYes.size() < (numberSignals * ( -1))) {
            jButton3.setEnabled(false);
        }
    } //GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton4ActionPerformed
        if (!jList1.isSelectionEmpty()) {
            Object[] selected = jList1.getSelectedValues();
            if (numberSignals > 0 && dlmYes.size() + selected.length > numberSignals) {
                JOptionPane.showMessageDialog(owner, "Maximum " + numberSignals + " signals selected");
            } else {
                for (int index = 0; index < selected.length; index++) {
                    dlmYes.addElement(selected[index]);
                    dlmNo.removeElement(selected[index]);
                }
                if (numberSignals > 0 && dlmYes.size() >= numberSignals) {
                    jButton4.setEnabled(false);
                    jButton1.setEnabled(false);
                } else if (numberSignals < 0 &&
                           dlmYes.size() >= (numberSignals * ( -1))) {
                    jButton3.setEnabled(true);
                }

                if (dlmYes.size() > 0) {
                    jButton5.setEnabled(true);
                    jButton2.setEnabled(true);
                }
                if (dlmNo.size() <= 0) {
                    jButton4.setEnabled(false);
                    jButton1.setEnabled(false);
                }
            }
        }
    } //GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton3ActionPerformed
        int intervalsNumber = 0;
        int signalsNumber = 0;
        if (selectInterval.isSelected()) {
            intervalsNumber = Integer.parseInt(jSpinner1.getValue().toString());
        }
        if (selectSignals.isSelected()) {
            signalsNumber = dlmYes.size();
        }
        int totalSelected = intervalsNumber + signalsNumber;
        if (numberSignals == 0 ||
            (numberSignals > 0 && totalSelected <= numberSignals) ||
            (numberSignals < 0 && totalSelected >= (numberSignals * ( -1)))) {
            Enumeration elemen = dlmYes.elements();
            owner.setVisible(false);
            try {
                if (selectInterval.isSelected()) {
                    while (elemen.hasMoreElements()) {
                        intervals.add(new IntervalSelectedEvent(elemen.nextElement().toString()));
                    }

                    selectIntervals(intervalsNumber);
                } else {
                    jswbManager.runAlgorithm(alg, elemen);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "The number of intervals selected is not a valid number");
            }
            owner.dispose();
        } else {
            if (numberSignals < 0) {
                JOptionPane.showMessageDialog(owner,
                                              "Selected " + totalSelected +
                                              " signals(or Intervals).Needed a minimum of " + numberSignals);
            } else {
                JOptionPane.showMessageDialog(owner,
                                              "Selected " + totalSelected +
                                              " signals(or Intervals).Needed a maximum of " + numberSignals);
            }
        }

    } //GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed

        Object[] elements = dlmYes.toArray();
        for (int index = 0; index < elements.length; index++) {
            dlmNo.addElement(elements[index]);
            dlmYes.removeElement(elements[index]);
        }

        if (dlmNo.size() > 0) {
            jButton1.setEnabled(true);
            jButton4.setEnabled(true);
        }
        if (dlmYes.size() <= 0) {
            jButton2.setEnabled(false);
            jButton5.setEnabled(false);
        }
    } //GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        if ((numberSignals > 0 && dlmYes.size() + dlmNo.size() <= numberSignals) || numberSignals <= 0) {
            Object[] elements = dlmNo.toArray();
            for (int index = 0; index < elements.length; index++) {
                dlmYes.addElement(elements[index]);
                dlmNo.removeElement(elements[index]);
            }
        } else {
            JOptionPane.showMessageDialog(owner, "Maximum " + numberSignals + " signals selected");
        }
        if (dlmNo.size() <= 0 || (numberSignals > 0 && dlmYes.size() >= numberSignals)) {
            jButton1.setEnabled(false);
            jButton4.setEnabled(false);
        }
        if (dlmYes.size() > 0) {
            jButton2.setEnabled(true);
            jButton5.setEnabled(true);
        }
    } //GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JCheckBox selectInterval;
    private javax.swing.JCheckBox selectSignals;
    // End of variables declaration//GEN-END:variables

}
