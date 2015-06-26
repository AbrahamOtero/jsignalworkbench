package tmp;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import javax.swing.*;
import java.awt.Dimension;

public class DialogArea extends JDialog {
    private JPanel panel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
     JTextField jTextFieldPeso = new JTextField();
    private JLabel jLabel3 = new JLabel();
     JComboBox jComboBoxDroga;
     JCheckBox jCheckBoxValle = new JCheckBox();
    private JButton jButton1 = new JButton();
     JCheckBox jCheckBoxPico = new JCheckBox();
    private JLabel jLabel4 = new JLabel();
     JComboBox jComboBoxParametro;
     boolean cancelado = false;
    private String[] drogas = {"Acetilcolina inicio", "Nitroprusiato inicio",
                              "Noradrenalina inicio", "Acetilcolina fin", "Nitroprusiato fin",
                              "Noradrenali fin"
    };

    private String[] parametro = {"Presión arterial sistólica",
                                 "Presión arterial media",
                                 "Presión arterial diastólica",
                                 "Presión pulmonar sistólica",
                                 "Presión pulmonar media",
                                 "Presión pulmonar diastólica",
                                 "Flujo carótida", "Flujo riñón",
                                 "Flujo corteza", "Flujo médula"
    };
    private JButton jButton2 = new JButton();


    public DialogArea(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jComboBoxDroga = new JComboBox(drogas);
            jComboBoxParametro = new JComboBox(parametro);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public DialogArea() {
        this(new Frame(), "DialogArea", false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(null);
        this.getContentPane().setLayout(null);
        jCheckBoxPico.setForeground(Color.blue);
        jCheckBoxPico.addActionListener(new DialogArea_jCheckBoxPico_actionAdapter(this));
        jCheckBoxValle.addActionListener(new DialogArea_jCheckBoxValle_actionAdapter(this));
        jCheckBoxValle.setSelected(true);
        jTextFieldPeso.addFocusListener(new DialogArea_jTextFieldPeso_focusAdapter(this));
        jButton1.addActionListener(new DialogArea_jButton1_actionAdapter(this));
        panel1.setMinimumSize(new Dimension(600, 400));
        panel1.setPreferredSize(new Dimension(600, 400));
        jButton2.setBounds(new Rectangle(343, 312, 118, 25));
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new DialogArea_jButton2_actionAdapter(this));
        jTextFieldPeso.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jComboBoxDroga.setVisible(false);
        jLabel4.setVisible(false);
        jComboBoxParametro.setVisible(false);
        this.getContentPane().add(panel1, null);
        jLabel2.setForeground(Color.blue);
        jLabel2.setText("Peso del cerdo:");
        jLabel2.setBounds(new Rectangle(58, 104, 105, 16));
        jTextFieldPeso.setColumns(9);
        jTextFieldPeso.setBounds(new Rectangle(215, 98, 69, 22));
        jLabel3.setForeground(Color.blue);
        jLabel3.setText("Droga:");
        jLabel3.setBounds(new Rectangle(59, 163, 41, 16));
        jComboBoxDroga.setBounds(new Rectangle(215, 155, 312, 24));
        jCheckBoxValle.setForeground(Color.blue);
        jCheckBoxValle.setText("Valle");
        jCheckBoxValle.setBounds(new Rectangle(58, 247, 91, 25));
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 15));
        jLabel1.setForeground(Color.blue);
        jButton1.setBounds(new Rectangle(201, 312, 81, 25));
        jButton1.setText("Aceptar");
        jCheckBoxPico.setText("Pico");
        jCheckBoxPico.setBounds(new Rectangle(236, 247, 91, 25));
        jLabel4.setForeground(Color.blue);
        jLabel4.setText("Parámetro");
        jLabel4.setBounds(new Rectangle(60, 203, 118, 16));
        jComboBoxParametro.setBounds(new Rectangle(215, 195, 312, 24));
        panel1.add(jLabel1, null);
        panel1.add(jLabel2);
        panel1.add(jTextFieldPeso);
        panel1.add(jLabel3);
        panel1.add(jComboBoxDroga);
        panel1.add(jCheckBoxValle);
        panel1.add(jLabel4);
        panel1.add(jComboBoxParametro);
        panel1.add(jCheckBoxPico);
        this.getContentPane().add(jButton1);
        this.getContentPane().add(jButton2);
        jLabel1.setBounds(new Rectangle(58, 24, 400, 16));
        jLabel1.setText("Medida que se va a realizar:");
        panel1.setBounds(new Rectangle(10, 10, 540, 300));
    }

    public void jCheckBoxValle_actionPerformed(ActionEvent e) {
        this.jCheckBoxPico.setSelected(!this.jCheckBoxValle.isSelected());
    }

    public void jCheckBoxPico_actionPerformed(ActionEvent e) {
        this.jCheckBoxValle.setSelected(!this.jCheckBoxPico.isSelected());
    }

    public void jTextFieldPeso_focusLost(FocusEvent e) {
try {
    Float.parseFloat(this.jTextFieldPeso.getText());
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this,"Error, formato inadecuado para el peso", "Error",
            JOptionPane.ERROR_MESSAGE);
}
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        cancelado= true;
        this.dispose();
    }
}


class DialogArea_jButton2_actionAdapter implements ActionListener {
    private DialogArea adaptee;
    DialogArea_jButton2_actionAdapter(DialogArea adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class DialogArea_jButton1_actionAdapter implements ActionListener {
    private DialogArea adaptee;
    DialogArea_jButton1_actionAdapter(DialogArea adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class DialogArea_jTextFieldPeso_focusAdapter extends FocusAdapter {
    private DialogArea adaptee;
    DialogArea_jTextFieldPeso_focusAdapter(DialogArea adaptee) {
        this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
        adaptee.jTextFieldPeso_focusLost(e);
    }
}


class DialogArea_jCheckBoxPico_actionAdapter implements ActionListener {
    private DialogArea adaptee;
    DialogArea_jCheckBoxPico_actionAdapter(DialogArea adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBoxPico_actionPerformed(e);
    }
}


class DialogArea_jCheckBoxValle_actionAdapter implements ActionListener {
    private DialogArea adaptee;
    DialogArea_jCheckBoxValle_actionAdapter(DialogArea adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBoxValle_actionPerformed(e);
    }
}
