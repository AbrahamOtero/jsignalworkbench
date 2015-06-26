package research.beats.hrv;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class DialogApneaEpisodeGenerator extends JDialog {

    private int tamanoVentana = 300;
    private float pesoApnea = 3;
    private float pesoHipoapnea = 1;
    private float limitePorcentaje = 30;

    public DialogApneaEpisodeGenerator(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            this.setSize(400, 400);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public DialogApneaEpisodeGenerator() {
        this(new Frame(), "DialogApneaEpisodeGenerator", false);
    }

    private void jbInit() throws Exception {
        jPanel2.setLayout(null);
        jLabel2.setText("Peso de apneas");
        jLabel2.setBounds(new Rectangle(53, 115, 133, 16));
        jTextFieldVentana.setColumns(10);
        jTextFieldVentana.setBounds(new Rectangle(258, 68, 69, 22));
        jTextFieldVentana.addActionListener(new DialogApneaEpisodeGenerator_jTextField1_actionAdapter(this));
        jLabel3.setText("Peso de hipoapneas");
        jLabel3.setBounds(new Rectangle(53, 157, 148, 16));
        jLabel4.setText("Porcentaje límite");
        jLabel4.setBounds(new Rectangle(53, 202, 124, 16));
        jLabel5.setText("Tamaño de la ventana");
        jLabel5.setBounds(new Rectangle(53, 74, 176, 16));
        jTextFieldApneas.setColumns(10);
        jTextFieldApneas.setBounds(new Rectangle(258, 109, 69, 22));
        jTextFieldHipoapneas.setColumns(10);
        jTextFieldHipoapneas.setBounds(new Rectangle(258, 151, 69, 22));
        jTextFieelPorcentaje.setColumns(10);
        jTextFieelPorcentaje.setBounds(new Rectangle(258, 196, 69, 22));
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 16));
        jLabel1.setForeground(Color.blue);
        jButton1.addActionListener(new DialogApneaEpisodeGenerator_jButton1_actionAdapter(this));
        jButton2.addActionListener(new DialogApneaEpisodeGenerator_jButton2_actionAdapter(this));
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jButton2.setText("Aceptar");
        jLabel1.setText("Configuración para generar los intervalos");
        jPanel1.add(jButton2);
        jPanel1.add(jButton1);
        jButton1.setText("Cancelar");
        jPanel3.add(jLabel1);
        this.getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        this.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jLabel5);
        jPanel2.add(jTextFieldVentana);
        jPanel2.add(jLabel2);
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel4);
        jPanel2.add(jTextFieldApneas);
        jPanel2.add(jTextFieldHipoapneas);
        jPanel2.add(jTextFieelPorcentaje);

    }

    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private JTextField jTextFieldVentana = new JTextField();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JTextField jTextFieldApneas = new JTextField();
    private JTextField jTextFieldHipoapneas = new JTextField();
    private JTextField jTextFieelPorcentaje = new JTextField();
    public void jTextField1_actionPerformed(ActionEvent e) {

    }

    public void jButton1_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public float getLimitePorcentaje() {
        return limitePorcentaje;
    }

    public float getPesoApnea() {
        return pesoApnea;
    }

    public float getPesoHipoapnea() {
        return pesoHipoapnea;
    }

    public int getTamanoVentana() {
        return tamanoVentana;
    }

    public void setTamanoVentana(int tamanoVentana) {
        this.tamanoVentana = tamanoVentana;
        jTextFieldVentana.setText("" + tamanoVentana);
    }

    public void setPesoHipoapnea(float pesoHipoapnea) {
        this.pesoHipoapnea = pesoHipoapnea;
        jTextFieldHipoapneas.setText("" + pesoHipoapnea);
    }

    public void setPesoApnea(float pesoApnea) {
        this.pesoApnea = pesoApnea;
        jTextFieldApneas.setText("" + pesoApnea);
    }

    public void setLimitePorcentaje(float limitePorcentaje) {
        this.limitePorcentaje = limitePorcentaje;
        jTextFieelPorcentaje.setText("" + limitePorcentaje);
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        try {
            String ventana = jTextFieldVentana.getText();
            tamanoVentana = Integer.parseInt(ventana);
            String pesoA = jTextFieldApneas.getText();
            pesoApnea = Float.parseFloat(pesoA);

            String pesoH = jTextFieldHipoapneas.getText();
            pesoHipoapnea = Float.parseFloat(pesoH);

            String porcentaje = jTextFieelPorcentaje.getText();
            limitePorcentaje = Float.parseFloat(porcentaje);
        this.dispose();
        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                          "Error en el formato de alguno de los datos",
                                          "Error", JOptionPane.OK_OPTION);
        }

    }
}


class DialogApneaEpisodeGenerator_jButton2_actionAdapter implements ActionListener {
    private DialogApneaEpisodeGenerator adaptee;
    DialogApneaEpisodeGenerator_jButton2_actionAdapter(DialogApneaEpisodeGenerator adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class DialogApneaEpisodeGenerator_jButton1_actionAdapter implements ActionListener {
    private DialogApneaEpisodeGenerator adaptee;
    DialogApneaEpisodeGenerator_jButton1_actionAdapter(DialogApneaEpisodeGenerator adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class DialogApneaEpisodeGenerator_jTextField1_actionAdapter implements ActionListener {
    private DialogApneaEpisodeGenerator adaptee;
    DialogApneaEpisodeGenerator_jTextField1_actionAdapter(DialogApneaEpisodeGenerator adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jTextField1_actionPerformed(e);
    }
}
