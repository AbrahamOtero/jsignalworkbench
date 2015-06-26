package net.javahispano.plugins.basicstats;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
public class DialogMobileMeanPlugin extends JDialog {
    private int window;
    private int oldWindow;
    private boolean mediana = false;
    private boolean rellenar = false;
    private float valorRelleno = 50;

    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JSlider jSlider1 = new JSlider();
    private JTextField textDuration = new JTextField();
    private JCheckBox jCheckBox1 = new JCheckBox();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JCheckBox jCheckBox2 = new JCheckBox();
    JTextField jTextField1 = new JTextField();
    public DialogMobileMeanPlugin(Frame owner, String title, boolean modal,
                                  int window) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.ajustarSlider(window);
        jSlider1.setValue(window);
        this.oldWindow = window;
        this.setSize(500, 250);
        this.setLocationRelativeTo(owner);
    }

    public DialogMobileMeanPlugin() {
        this(null, "DialogMobileMeanPlugin", false, 0);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new
                                   DialogMobileMeanPlugin_jButton1_actionAdapter(this));
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new
                                   DialogMobileMeanPlugin_jButton2_actionAdapter(this));
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        jLabel1.setForeground(Color.blue);
        jLabel1.setText("Longitud de la ventana temporal:");
        jSlider1.setMajorTickSpacing(10);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setPreferredSize(new Dimension(300, 50));
        jSlider1.addChangeListener(new
                                   DialogMobileMeanPlugin_jSlider1_changeAdapter(this));
        textDuration.setColumns(10);
        textDuration.addFocusListener(new
                                      DialogMobileMeanPlugin_textDuration_focusAdapter(this));
        textDuration.addActionListener(new
                                       DialogMobileMeanPlugin_textDuration_actionAdapter(this));
        jCheckBox1.setText("Usar mediana en vez de la media. ");
        jPanel2.setLayout(flowLayout1);
        flowLayout1.setVgap(15);
        jPanel1.setLayout(flowLayout2);
        flowLayout2.setHgap(25);
        jCheckBox2.setText(
                "Rellenar huecos con el ultimo valor si su valor es menor que:");
        jCheckBox2.addItemListener(new
                                   DialogMobileMeanPlugin_jCheckBox2_itemAdapter(this));
        jTextField1.setEnabled(false);
        jTextField1.setText("50");
        jTextField1.setColumns(8);
        getContentPane().add(panel1);
        panel1.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        panel1.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jLabel1);
        jPanel2.add(jSlider1);
        jPanel2.add(textDuration);
        jPanel2.add(jCheckBox1);
        jPanel2.add(jCheckBox2);
        jPanel2.add(jTextField1);
    }

    public void textDuration_actionPerformed(ActionEvent e) {
        try {
            int valor = Integer.parseInt(textDuration.getText());
            ajustarSlider(valor);
            jSlider1.setValue(valor);
        } catch (NumberFormatException ex) {
            textDuration.requestFocus();
            textDuration.selectAll();
        }
    }

    private void ajustarSlider(int valorMax) {
            if (valorMax > jSlider1.getMaximum()) {
                jSlider1.setMaximum(valorMax*2);

        jSlider1.setMajorTickSpacing(valorMax/10);
            }
    }

    public void textDuration_focusLost(FocusEvent focusEvent) {
        this.textDuration_actionPerformed(null);
    }

    public void jSlider1_stateChanged(ChangeEvent changeEvent) {
        int valor = jSlider1.getValue();
        textDuration.setText(Integer.toString(valor));
    }

    public static void main(String[] args) {
        DialogMobileMeanPlugin configura = new DialogMobileMeanPlugin();
        configura.show();
        configura.repaint();
    }

    public int getWindow() {
        return window;
    }

    public boolean isMediana() {
        return mediana;
    }

    public float getValorRelleno() {
        return valorRelleno;
    }

    public boolean isRellenar() {
        return rellenar;
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        this.dispose();
        this.window = oldWindow;
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        this.window = jSlider1.getValue();
        this.mediana = jCheckBox1.isSelected();
        if (this.jCheckBox2.isSelected()) {
            this.rellenar = true;
            try {
                this.valorRelleno = Float.parseFloat(this.jTextField1.getText());
            } catch (NumberFormatException ex) {
                this.jTextField1.requestFocus();
                this.jTextField1.selectAll();
                return;
            }
        } else {
            this.rellenar = false;
        }
        this.dispose();

    }

    public void jCheckBox2_itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            this.jTextField1.setEnabled(true);
        } else {
            this.jTextField1.setEnabled(false);
        }
    }

    public void setRellenar(boolean rellenar) {
        this.rellenar = rellenar;
        this.jCheckBox2.setSelected(rellenar);
    }

    public void setMediana(boolean mediana) {
        this.mediana = mediana;
        this.jCheckBox1.setSelected(mediana);
    }

    public void setValorRelleno(float valorRelleno) {
        this.valorRelleno = valorRelleno;
        this.jTextField1.setText("" + valorRelleno);
    }
}


class DialogMobileMeanPlugin_jCheckBox2_itemAdapter implements ItemListener {
    private DialogMobileMeanPlugin adaptee;
    DialogMobileMeanPlugin_jCheckBox2_itemAdapter(DialogMobileMeanPlugin
                                                  adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.jCheckBox2_itemStateChanged(e);
    }
}


class DialogMobileMeanPlugin_jButton1_actionAdapter implements ActionListener {
    private DialogMobileMeanPlugin adaptee;
    DialogMobileMeanPlugin_jButton1_actionAdapter(DialogMobileMeanPlugin
                                                  adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton1_actionPerformed(actionEvent);
    }
}


class DialogMobileMeanPlugin_textDuration_actionAdapter implements
        ActionListener {
    private DialogMobileMeanPlugin adaptee;
    DialogMobileMeanPlugin_textDuration_actionAdapter(DialogMobileMeanPlugin
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textDuration_actionPerformed(e);
    }
}


class DialogMobileMeanPlugin_jSlider1_changeAdapter implements ChangeListener {
    private DialogMobileMeanPlugin adaptee;
    DialogMobileMeanPlugin_jSlider1_changeAdapter(DialogMobileMeanPlugin
                                                  adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.jSlider1_stateChanged(changeEvent);
    }
}


class DialogMobileMeanPlugin_jButton2_actionAdapter implements ActionListener {
    private DialogMobileMeanPlugin adaptee;
    DialogMobileMeanPlugin_jButton2_actionAdapter(DialogMobileMeanPlugin
                                                  adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton2_actionPerformed(actionEvent);
    }
}


class DialogMobileMeanPlugin_textDuration_focusAdapter extends FocusAdapter {
    private DialogMobileMeanPlugin adaptee;
    DialogMobileMeanPlugin_textDuration_focusAdapter(DialogMobileMeanPlugin
            adaptee) {
        this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent focusEvent) {
        adaptee.textDuration_focusLost(focusEvent);
    }
}
