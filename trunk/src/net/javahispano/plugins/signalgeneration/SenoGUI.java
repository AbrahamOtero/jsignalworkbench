package net.javahispano.plugins.signalgeneration;

import java.awt.*;

import javax.swing.*;

public class SenoGUI extends JPanel {
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel amplitudEtiqueta = new JLabel();
    JLabel jLabel1 = new JLabel();
    JLabel frecuenciaEtiqueta = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel desfaseEtiqueta = new JLabel();
    JCheckBox seleccionado = new JCheckBox();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();


    public SenoGUI(double amplitud, double frecuencia, double desfase) {
        this.setAmplitud(amplitud);
        this.setFrecuencia(frecuencia);
        this.setDesfase(desfase);
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(flowLayout1);
        Font fuente = new java.awt.Font("Tahoma", Font.BOLD, 12);
        amplitudEtiqueta.setFont(fuente);
        amplitudEtiqueta.setForeground(Color.blue);
        jLabel1.setText("* Sin ( ");
        jLabel1.setFont(fuente);
        jLabel1.setForeground(Color.blue);
        frecuenciaEtiqueta.setFont(fuente);
        frecuenciaEtiqueta.setForeground(Color.blue);
        jLabel2.setText("+");
        jLabel2.setFont(fuente);
        jLabel1.setForeground(Color.blue);
        jLabel2.setForeground(Color.blue);
        jLabel3.setForeground(Color.blue);
        desfaseEtiqueta.setFont(fuente);
        desfaseEtiqueta.setForeground(Color.blue);
        jLabel3.setText(")");
        jLabel3.setFont(fuente);
        jLabel4.setText("*t");
        jLabel4.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel4.setForeground(Color.blue);
        this.add(amplitudEtiqueta);
        this.add(jLabel1);
        this.add(frecuenciaEtiqueta);
        this.add(jLabel4);
        this.add(jLabel2);
        this.add(desfaseEtiqueta);
        this.add(jLabel3);
        this.add(seleccionado);
    }

    public boolean isSeleccionado() {
        return seleccionado.isSelected();
    }

    public double getFrecuencia() {
        return Double.parseDouble(this.frecuenciaEtiqueta.getText());
    }

    public double getDesfase() {
        return Double.parseDouble(this.desfaseEtiqueta.getText());
    }

    public double getAmplitud() {
        return Double.parseDouble(this.amplitudEtiqueta.getText());
    }

    public void setFrecuencia(double frecuencia) {
        this.frecuenciaEtiqueta.setText("" + frecuencia);
    }

    public void setDesfase(double desfase) {
        this.desfaseEtiqueta.setText("" + desfase);
    }

    public void setAmplitud(double amplitud) {
        this.amplitudEtiqueta.setText("" + amplitud);
    }
}
