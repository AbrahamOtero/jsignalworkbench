/*
 * ConfigureSignalPanel2.java
 *
 * Created on 4 de junio de 2007, 16:57
 */

package net.javahispano.jsignalwb.ui;


import java.awt.Color;
import java.awt.Window;
import java.beans.*;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import org.joda.time.DateTime;

public class ConfigureSignalPanel extends javax.swing.JPanel implements PropertyChangeListener, DocumentListener {
    String signalName;
    SignalManager sm;
    JSWBManager jswbManager;
    /** Creates new form ConfigureSignalPanel2 */
    public ConfigureSignalPanel(String signalName, JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
        this.signalName = signalName;
        this.sm = jswbManager.getSignalManager();
        initComponents();
//        abscissaValueTextField.setEnabled(false);
        minVisibleTextField.setEnabled(false);
        maxVisibleTextField.setEnabled(false);
        zoomSlider.setEnabled(false);
        zoomTextField.setEnabled(false);
        abscissaZoomRadioButton.setActionCommand("zoom");
        abscissaIntervalRadioButton.setActionCommand("interval");

        jTextFieldDate1.setText(TimeRepresentation.timeToString(
                sm.getSignal(signalName).getStart()));
        datePicker1.showButtonOnly(true);
        try {
            datePicker1.setDate(new Date(sm.getSignal(signalName).getStart()));
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        datePicker1.addPropertyChangeListener(datePicker1.PROPERTY_NAME_DATE, this);
        applyButton.setEnabled(false);
        initPropertiesListeners();
        abscissaIntervalRadioButton.grabFocus();
    }

    public void changedUpdate(DocumentEvent evt) {
        documentEvent(evt);
    }

    public void removeUpdate(DocumentEvent evt) {
        documentEvent(evt);
    }

    public void insertUpdate(DocumentEvent evt) {
        documentEvent(evt);
    }

    private void documentEvent(DocumentEvent evt) {
        String property = evt.getDocument().getProperty("textField").toString();

        if (property.equals("name")) {
            name = true;
        } else if (property.equals("abscissa")) {
            abscissa = true;
        } else if (property.equals("start")) {
            start = true;
        } else if (property.equals("frecuency")) {
            frec = true;
        } else if (property.equals("magnitude")) {
            magnitude = true;
        } else if (property.equals("zoom")) {
            zoom = true;
        }
        /*else if(property.equals("abscissaValue"))
            abscissaValue=true;*/
        else if (property.equals("interval")) {
            interval = true;
        }
        evt.getDocument().removeDocumentListener(this);
        checkApplyButton();
    }

    private void initPropertiesListeners() {
        zoom = abscissa = frec = start = magnitude = visible = name = /*abscissaValue=*/ false;
        nameTextField.getDocument().addDocumentListener(this);
//        abscissaTextField.getDocument().addDocumentListener(this);
        jTextFieldDate1.getDocument().addDocumentListener(this);
        frecuencyTextField.getDocument().addDocumentListener(this);
        magnitudeTextField.getDocument().addDocumentListener(this);
        zoomTextField.getDocument().addDocumentListener(this);
//        abscissaValueTextField.getDocument().addDocumentListener(this);
        minVisibleTextField.getDocument().addDocumentListener(this);
        maxVisibleTextField.getDocument().addDocumentListener(this);
        nameTextField.getDocument().putProperty("textField", "name");
//        abscissaTextField.getDocument().putProperty("textField","abscissa");
        jTextFieldDate1.getDocument().putProperty("textField", "start");
        frecuencyTextField.getDocument().putProperty("textField", "frecuency");
        magnitudeTextField.getDocument().putProperty("textField", "magnitude");
        zoomTextField.getDocument().putProperty("textField", "zoom");
//        abscissaValueTextField.getDocument().putProperty("textField","interval");
        minVisibleTextField.getDocument().putProperty("textField", "interval");
        maxVisibleTextField.getDocument().putProperty("textField", "interval");
    }

    private void checkApplyButton() {
        if (name || abscissa || start || frec || magnitude || zoom || /*abscissaValue||*/ interval) {
            applyButton.setEnabled(true);
        } else {
            applyButton.setEnabled(false);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            Date date = datePicker1.getDate();
            if (date != null) {
                jTextFieldDate1.setText(TimeRepresentation.timeToString(
                        swapDateNoChangeTime(
                                sm.getSignal(signalName).getStart(), date.getTime())));
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Codigo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        nameTextField.setText(signalName);
        startTimeLabel = new javax.swing.JLabel();
        frecuencyLabel = new javax.swing.JLabel();
        frecuencyTextField = new javax.swing.JTextField();
        frecuencyTextField.setText(Float.toString(sm.getSignal(signalName).getSRate()));
        magnitudeLabel = new javax.swing.JLabel();
        magnitudeTextField = new javax.swing.JTextField();
        magnitudeTextField.setText(sm.getSignal(signalName).getMagnitude());
        visibleCheckBox = new javax.swing.JCheckBox();
        okButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jTextFieldDate1 = new net.javahispano.jsignalwb.ui.JTextFieldDate();
        datePicker1 = new com.michaelbaranov.microba.calendar.DatePicker();
        jPanel1 = new javax.swing.JPanel();
        abscissaZoomRadioButton = new javax.swing.JRadioButton();
        abscissaIntervalRadioButton = new javax.swing.JRadioButton();
        minMaxPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        minVisibleTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        maxVisibleTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        zoomLabel = new javax.swing.JLabel();
        zoomSlider = new javax.swing.JSlider();
        zoomTextField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0), 4));
        nameLabel.setText("Name:");

        startTimeLabel.setText("Start time:");

        frecuencyLabel.setText("Frecuency:");

        magnitudeLabel.setText("Magnitude:");

        visibleCheckBox.setSelected(sm.getSignal(signalName).getProperties().isVisible()
                );
        visibleCheckBox.setText("Visible:");
        visibleCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        visibleCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        visibleCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        visibleCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                visibleCheckBoxItemStateChanged(evt);
            }
        });

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonGroup1.add(abscissaZoomRadioButton);
        abscissaZoomRadioButton.setText("Channel zoom");
        abscissaZoomRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        abscissaZoomRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        abscissaZoomRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtons(evt);
            }
        });

        buttonGroup1.add(abscissaIntervalRadioButton);
        abscissaIntervalRadioButton.setText("Channel limits");
        abscissaIntervalRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        abscissaIntervalRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        abscissaIntervalRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtons(evt);
            }
        });

        minMaxPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setText("Min:");

        minVisibleTextField.setText(Float.toString(sm.getSignal(signalName).getProperties().getAbscissaValue()));

        jLabel2.setText("Max:");

        maxVisibleTextField.setText(Float.toString(sm.getSignal(signalName).getProperties().getMaxValue()));

        javax.swing.GroupLayout minMaxPanelLayout = new javax.swing.GroupLayout(minMaxPanel);
        minMaxPanel.setLayout(minMaxPanelLayout);
        minMaxPanelLayout.setHorizontalGroup(
                minMaxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(minMaxPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jLabel1)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(minVisibleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 93,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jLabel2)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(maxVisibleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 87,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(174, Short.MAX_VALUE))
                );
        minMaxPanelLayout.setVerticalGroup(
                minMaxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(minMaxPanelLayout.createSequentialGroup()
                          .addGap(13, 13, 13)
                          .addGroup(minMaxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(minMaxPanelLayout.createSequentialGroup()
                                              .addGap(6, 6, 6)
                                              .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(minMaxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                BASELINE)
                                              .addComponent(minVisibleTextField)
                                              .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addComponent(maxVisibleTextField)))
                          .addContainerGap())
                );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        zoomLabel.setText("Zoom:");

        zoomSlider.setMajorTickSpacing(100);
        zoomSlider.setMaximum(500);
        zoomSlider.setMinorTickSpacing(25);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setSnapToTicks(true);
        zoomSlider.setValue((int) (sm.getSignal(signalName).getProperties().getZoom() * 100));
        zoomSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zoomSliderStateChanged(evt);
            }
        });

        zoomTextField.setText(Integer.toString((int) (sm.getSignal(signalName).getProperties().getZoom() * 100)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(zoomLabel)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(zoomSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(zoomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap())
                );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(zoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zoomLabel)
                                    .addComponent(zoomTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addContainerGap()
                                              .addComponent(abscissaZoomRadioButton))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(27, 27, 27)
                                              .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                              jPanel1Layout.createSequentialGroup()
                                              .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.LEADING)
                .addComponent(abscissaIntervalRadioButton)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addGap(17, 17, 17)
                          .addComponent(minMaxPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                          .addContainerGap())
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(abscissaZoomRadioButton)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(abscissaIntervalRadioButton)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(minMaxPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING)
                .addComponent(nameLabel)
                .addComponent(startTimeLabel)
                .addComponent(frecuencyLabel))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDate1, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE, 152,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                          .addComponent(frecuencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(magnitudeLabel)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(magnitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 188,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(visibleCheckBox)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                              .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, 149,
                Short.MAX_VALUE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(applyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 142,
                Short.MAX_VALUE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 162,
                javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nameLabel)
                                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                              .addComponent(jTextFieldDate1, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addComponent(startTimeLabel))
                                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(frecuencyLabel)
                                    .addComponent(frecuencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(magnitudeLabel)
                                    .addComponent(magnitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(visibleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                    .addComponent(okButton)
                                    .addComponent(applyButton)
                                    .addComponent(cancelButton))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
    } // </editor-fold>//GEN-END:initComponents

    private void radioButtons(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_radioButtons
        if ("zoom".equals(evt.getActionCommand())) {
//            abscissaValueTextField.setEnabled(false);
            zoomSlider.setEnabled(true);
            zoomTextField.setEnabled(true);
            minVisibleTextField.setEnabled(false);
            maxVisibleTextField.setEnabled(false);
            zoomTextField.grabFocus();
        }
        if ("interval".equals(evt.getActionCommand())) {
            minVisibleTextField.setEnabled(true);
            maxVisibleTextField.setEnabled(true);
//            abscissaValueTextField.setEnabled(true);
            zoomSlider.setEnabled(false);
            zoomTextField.setEnabled(false);
            minVisibleTextField.grabFocus();
        }
    } //GEN-LAST:event_radioButtons

    private void visibleCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) { //GEN-FIRST:event_visibleCheckBoxItemStateChanged
        visible = true;
        applyButton.setEnabled(true);
    } //GEN-LAST:event_visibleCheckBoxItemStateChanged

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_okButtonActionPerformed
        if (apply()) {
            hideJWindow();
        }
    } //GEN-LAST:event_okButtonActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_applyButtonActionPerformed
        apply();
    } //GEN-LAST:event_applyButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cancelButtonActionPerformed
        hideJWindow();
    } //GEN-LAST:event_cancelButtonActionPerformed

    private void zoomSliderStateChanged(javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_zoomSliderStateChanged
        if (zoomSlider.getValue() > 0) {
            zoomTextField.setText(Integer.toString(zoomSlider.getValue()));
        } else {
            zoomTextField.setText("1");
        }
        zoom = true;
        applyButton.setEnabled(true);
    } //GEN-LAST:event_zoomSliderStateChanged

    public void showJWindow(Window owner) {
        jw = new JWindow(owner);
        //jw.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
        jw.add(this);
        jw.setSize(this.getPreferredSize());
        jw.setLocationRelativeTo(owner);
        jw.setVisible(true);
    }

    private void hideJWindow() {
        jw.dispose();
    }

    private boolean apply() {
        boolean flag = true;
        if (zoom) {
            if (abscissaZoomRadioButton.isSelected()) {
                float zoomValue = 0;
                try {
                    zoomValue = Float.valueOf(zoomTextField.getText());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                if (zoomValue > 0) {
                    sm.setSignalZoom(signalName, zoomValue / 100f);
                    zoomTextField.setBackground(Color.WHITE);
                } else {
                    zoomTextField.setBackground(Color.RED);
                    flag = false;
                }
            }
        }
//        if(abscissa){
//            float abscissaOffsetValue=0;
//            try {
//                abscissaOffsetValue = Float.valueOf(abscissaTextField.getText());
//            } catch (NumberFormatException ex) {
//                ex.printStackTrace();
//            }
//            if(abscissaOffsetValue>=0){
//                sm.setSignalAbscissaOffset(signalName,abscissaOffsetValue/100f);
//                abscissaTextField.setBackground(Color.WHITE);
//            }else{
//                abscissaTextField.setBackground(Color.RED);
//                flag=false;
//            }
//        }
        /*if(abscissaValue){
            if(abscissaIntervalRadioButton.isSelected()){
                float newAbscissaValue;
                try {
                    newAbscissaValue = Float.valueOf(abscissaValueTextField.getText());
                    sm.setSignalAbscissaValue(signalName,newAbscissaValue);
                    abscissaValueTextField.setBackground(Color.WHITE);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    abscissaValueTextField.setBackground(Color.RED);
                    flag=false;
                }
            }

                 }*/
        if (frec) {
            float frecValue = 0;
            try {
                frecValue = Float.valueOf(frecuencyTextField.getText());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            if (frecValue > 0) {
                sm.setSignalFrecuency(signalName, frecValue);
                frecuencyTextField.setBackground(Color.WHITE);
            } else {
                frecuencyTextField.setBackground(Color.RED);
                flag = false;
            }

        }
        if (start) {
            String dateValue = jTextFieldDate1.getFormattedText();
            if (!dateValue.equals("")) {
                try {
                    sm.setSignalStartTime(signalName,
                                          TimeRepresentation.stringToMillis(dateValue, true, true, true));
                    jTextFieldDate1.setBackground(Color.WHITE);
                } catch (Exception ex) {
                    jTextFieldDate1.setBackground(Color.RED);
                }
            } else {
                jTextFieldDate1.setBackground(Color.RED);
                flag = false;
            }
        }
        if (magnitude) {
            String magnitudeValue = magnitudeTextField.getText();
            if (!magnitudeValue.equals("")) {
                sm.setSignalMagnitude(signalName, magnitudeValue);
                magnitudeTextField.setBackground(Color.WHITE);
            } else {
                magnitudeTextField.setBackground(Color.RED);
                flag = false;
            }
        }
        if (visible) {
            sm.setSignalVisible(signalName, visibleCheckBox.isSelected());
        }

        if (abscissaIntervalRadioButton.isSelected() && interval) {
            boolean flag2 = false;
            boolean flag3 = false;
//            boolean flag4=false;
            float newMin = 0;
            try {
                newMin = Float.parseFloat(minVisibleTextField.getText());
                minVisibleTextField.setBackground(Color.WHITE);
                flag2 = true;
            } catch (NumberFormatException ex) {
                minVisibleTextField.setBackground(Color.RED);
            }
            float newMax = 0;
            try {
                newMax = Float.parseFloat(maxVisibleTextField.getText());
                maxVisibleTextField.setBackground(Color.WHITE);
                flag3 = true;
            } catch (NumberFormatException ex) {
                maxVisibleTextField.setBackground(Color.RED);
            }
//            float newAbscissaValue=0;
//                try {
//                    newAbscissaValue = Float.valueOf(abscissaValueTextField.getText());
//                    sm.setSignalAbscissaValue(signalName,newAbscissaValue);
//                    flag4=true;
//                } catch (NumberFormatException ex) {
//                    abscissaValueTextField.setBackground(Color.RED);
//                }
            if (flag2 && flag3) {
                try {
                    jswbManager.setSignalVisibleRange(signalName, newMin, newMax);
                    maxVisibleTextField.setBackground(Color.WHITE);
                    minVisibleTextField.setBackground(Color.WHITE);
//                    abscissaValueTextField.setBackground(Color.WHITE);
                } catch (SignalNotFoundException ex) {
                    maxVisibleTextField.setBackground(Color.RED);
                    minVisibleTextField.setBackground(Color.RED);
//                    abscissaValueTextField.setBackground(Color.RED);
                    flag = false;
                }
            }
        }

        if (name) {
            String newName = nameTextField.getText();
            if (!newName.equals("")) {
                sm.renameSignal(signalName, newName);
                signalName = newName;
                nameTextField.setBackground(Color.WHITE);
            } else {
                nameTextField.setBackground(Color.RED);
                flag = false;
            }
        }
        initPropertiesListeners();
        jswbManager.refreshJSM(false);
        applyButton.setEnabled(false);
        return flag;
    }

    /**
     * @param old long
     * @param newTime long
     * @return long
     */
    private static long swapDateNoChangeTime(long old, long newTime) {
        DateTime dt = new DateTime(old);
        DateTime newDateTime = new DateTime(newTime).withTime(dt.getHourOfDay(),
                dt.getMinuteOfHour(), dt.getSecondOfMinute(),
                dt.getMillisOfSecond());

        return newDateTime.getMillis();
    }

    // Declaracion de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JRadioButton abscissaIntervalRadioButton;
    private javax.swing.JRadioButton abscissaZoomRadioButton;
    private javax.swing.JButton applyButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private com.michaelbaranov.microba.calendar.DatePicker datePicker1;
    private javax.swing.JLabel frecuencyLabel;
    private javax.swing.JTextField frecuencyTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private net.javahispano.jsignalwb.ui.JTextFieldDate jTextFieldDate1;
    private javax.swing.JLabel magnitudeLabel;
    private javax.swing.JTextField magnitudeTextField;
    private javax.swing.JTextField maxVisibleTextField;
    private javax.swing.JPanel minMaxPanel;
    private javax.swing.JTextField minVisibleTextField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JCheckBox visibleCheckBox;
    private javax.swing.JLabel zoomLabel;
    private javax.swing.JSlider zoomSlider;
    private javax.swing.JTextField zoomTextField;
    // Fin de declaracion de variables//GEN-END:variables
    private JWindow jw;
    private boolean name;
    private boolean visible;
    private boolean magnitude;
    private boolean start;
    private boolean frec;
    private boolean abscissa;
    //private boolean abscissaValue;
    private boolean zoom;
    private boolean interval;

}
