package net.javahispano.jsignalwb.plugins.calculator;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JButton;
import net.javahispano.jsignalwb.Signal;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class CalculatorGUI extends JDialog {
    public enum Operation {
        ADD, SUBSTRACT, MULTIPLY, DIVIDE
    }


    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel jPanel5 = new JPanel();
    private BorderLayout borderLayout4 = new BorderLayout();
    private FlowLayout flowLayout1 = new FlowLayout();
    private ButtonGroup buttonGroup1 = new ButtonGroup();
    private JRadioButton jRadioButtonAdd = new JRadioButton();
    private JRadioButton jRadioButtonSubstract = new JRadioButton();
    private JRadioButton jRadioButtonMultiply = new JRadioButton();
    private JRadioButton jRadioButtonDivide = new JRadioButton();
    private JList signalListFirst = new JList();
    private JList signalListSecond = new JList();
    private JPanel jPanel6 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private FlowLayout flowLayout2 = new FlowLayout();
    String firstSignalName, secondSignalName, newSignalName;
    Operation operation = Operation.ADD;
    boolean cancelar = false;
    private JPanel jPanel7 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private JTextField jTextFieldNweSignal = new JTextField();
    private FlowLayout flowLayout3 = new FlowLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    public CalculatorGUI(Frame owner, String title, boolean modal, String[] signalNames) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        signalListFirst.setListData(signalNames);
        signalListSecond.setListData(signalNames);
        signalListFirst.setSelectedIndex(0);
        signalListSecond.setSelectedIndex(0);
        this.generarNombreNuevaSenal();
        this.setSize(500, 400);
        this.setLocationRelativeTo(owner);
    }


    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 15));
        jLabel1.setForeground(Color.blue);
        jLabel1.setText("Calculadora de señales");
        jPanel4.setPreferredSize(new Dimension(200, 200));
        jPanel4.setLayout(borderLayout2);
        jPanel3.setPreferredSize(new Dimension(200, 10));
        jPanel3.setLayout(borderLayout3);
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanel2.setLayout(borderLayout4);
        jPanel5.setLayout(flowLayout1);
        jRadioButtonAdd.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18));
        jRadioButtonAdd.setSelected(true);
        jRadioButtonAdd.setText("+");
        jRadioButtonAdd.addActionListener(new CalculatorGUI_jRadioButtonAdd_actionAdapter(this));
        jRadioButtonSubstract.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18));
        jRadioButtonSubstract.setText("-");
        jRadioButtonSubstract.addActionListener(new CalculatorGUI_jRadioButtonSubstract_actionAdapter(this));
        jRadioButtonMultiply.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18));
        jRadioButtonMultiply.setText("*");
        jRadioButtonMultiply.addActionListener(new CalculatorGUI_jRadioButtonMultiply_actionAdapter(this));
        jRadioButtonDivide.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18));
        jRadioButtonDivide.setText("/");
        jRadioButtonDivide.addActionListener(new CalculatorGUI_jRadioButtonDivide_actionAdapter(this));
        flowLayout1.setHgap(50);
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new CalculatorGUI_jButton1_actionAdapter(this));
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new CalculatorGUI_jButton2_actionAdapter(this));
        jPanel6.setLayout(flowLayout2);
        flowLayout2.setHgap(15);
        jLabel2.setText("Nombre de la señal:");
        jTextFieldNweSignal.setColumns(25);
        jPanel7.setLayout(flowLayout3);
        flowLayout3.setHgap(15);
        signalListSecond.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        signalListSecond.addMouseListener(new CalculatorGUI_signalListSecond_mouseAdapter(this));
        signalListFirst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        signalListFirst.addMouseListener(new CalculatorGUI_signalListFirst_mouseAdapter(this));
        getContentPane().add(panel1);
        jPanel1.add(jLabel1);
        panel1.add(jPanel2, java.awt.BorderLayout.CENTER);
        panel1.add(jPanel3, java.awt.BorderLayout.EAST);
        jScrollPane1.getViewport().add(signalListSecond);
        panel1.add(jPanel4, java.awt.BorderLayout.WEST);
        jScrollPane2.getViewport().add(signalListFirst);
        jPanel5.add(jRadioButtonAdd);
        jPanel5.add(jRadioButtonSubstract);
        jPanel5.add(jRadioButtonMultiply);
        jPanel5.add(jRadioButtonDivide);
        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);
        buttonGroup1.add(jRadioButtonDivide);
        buttonGroup1.add(jRadioButtonMultiply);
        buttonGroup1.add(jRadioButtonSubstract);
        buttonGroup1.add(jRadioButtonAdd);
        panel1.add(jPanel1, java.awt.BorderLayout.NORTH);
        panel1.add(jPanel6, java.awt.BorderLayout.SOUTH);
        jPanel6.add(jLabel2);
        jPanel6.add(jTextFieldNweSignal);
        this.getContentPane().add(jPanel7, java.awt.BorderLayout.SOUTH);
        jPanel7.add(jButton1);
        jPanel7.add(jButton2);
        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }

    public void signalListSecond_mouseClicked(MouseEvent e) {
        generarNombreNuevaSenal();
    }

    private void generarNombreNuevaSenal() {
        String firstSignalName = (String) signalListFirst.getSelectedValue();
        String secondSignalName = (String) signalListSecond.getSelectedValue();
        if (firstSignalName == null || secondSignalName == null) {
            return;
        }
        String separador = generarSeparador();
        this.newSignalName = firstSignalName + separador + secondSignalName;
        jTextFieldNweSignal.setText(firstSignalName + separador + secondSignalName);
    }

    private String generarSeparador() {
        String separador = "";
        if (jRadioButtonAdd.isSelected()) {
            separador = " + ";
        } else if (jRadioButtonSubstract.isSelected()) {

            separador = " - ";
        } else if (jRadioButtonMultiply.isSelected()) {

            separador = " * ";
        } else {

            separador = " / ";
        }
        return separador;
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        cancelar = true;
        this.dispose();
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        firstSignalName = (String) signalListFirst.getSelectedValue();
        secondSignalName = (String) signalListSecond.getSelectedValue();
        this.newSignalName = jTextFieldNweSignal.getText();
        if (jRadioButtonAdd.isSelected()) {
            this.operation = Operation.ADD;
        } else if (jRadioButtonSubstract.isSelected()) {

            this.operation = Operation.SUBSTRACT;
        } else if (jRadioButtonMultiply.isSelected()) {

            this.operation = Operation.MULTIPLY;
        } else {

            this.operation = Operation.DIVIDE;
        }
        this.dispose();
    }

    public void signalListFirst_mouseClicked(MouseEvent e) {
        generarNombreNuevaSenal();
    }

    public void jRadioButtonAdd_actionPerformed(ActionEvent e) {

        generarNombreNuevaSenal();
    }

    public void jRadioButtonSubstract_actionPerformed(ActionEvent e) {

        generarNombreNuevaSenal();
    }

    public void jRadioButtonMultiply_actionPerformed(ActionEvent e) {

        generarNombreNuevaSenal();
    }

    public void jRadioButtonDivide_actionPerformed(ActionEvent e) {

        generarNombreNuevaSenal();
    }
}


class CalculatorGUI_jRadioButtonDivide_actionAdapter implements ActionListener {
    private CalculatorGUI adaptee;
    CalculatorGUI_jRadioButtonDivide_actionAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButtonDivide_actionPerformed(e);
    }
}


class CalculatorGUI_jRadioButtonMultiply_actionAdapter implements ActionListener {
    private CalculatorGUI adaptee;
    CalculatorGUI_jRadioButtonMultiply_actionAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButtonMultiply_actionPerformed(e);
    }
}


class CalculatorGUI_jRadioButtonSubstract_actionAdapter implements ActionListener {
    private CalculatorGUI adaptee;
    CalculatorGUI_jRadioButtonSubstract_actionAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButtonSubstract_actionPerformed(e);
    }
}


class CalculatorGUI_jRadioButtonAdd_actionAdapter implements ActionListener {
    private CalculatorGUI adaptee;
    CalculatorGUI_jRadioButtonAdd_actionAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButtonAdd_actionPerformed(e);
    }
}


class CalculatorGUI_jButton1_actionAdapter implements ActionListener {
    private CalculatorGUI adaptee;
    CalculatorGUI_jButton1_actionAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class CalculatorGUI_signalListFirst_mouseAdapter extends MouseAdapter {
    private CalculatorGUI adaptee;
    CalculatorGUI_signalListFirst_mouseAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.signalListFirst_mouseClicked(e);
    }
}


class CalculatorGUI_signalListSecond_mouseAdapter extends MouseAdapter {
    private CalculatorGUI adaptee;
    CalculatorGUI_signalListSecond_mouseAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.signalListSecond_mouseClicked(e);
    }
}


class CalculatorGUI_jButton2_actionAdapter implements ActionListener {
    private CalculatorGUI adaptee;
    CalculatorGUI_jButton2_actionAdapter(CalculatorGUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
