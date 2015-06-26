package research.beats;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
public class DialogKubiosRHRV extends JDialog {
    private boolean exportRHRV = true;
    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JCheckBox jCheckBox1 = new JCheckBox();
    private JCheckBox jCheckBox2 = new JCheckBox();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JPanel jPanel2 = new JPanel();
    private JButton jButton1 = new JButton();
    public DialogKubiosRHRV(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            this.setSize(300,150);
            this.setLocationRelativeTo(JSWBManager.getParentWindow());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public DialogKubiosRHRV() {
        this(new Frame(), "DialogKubiosRHRV", false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jCheckBox1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        jCheckBox1.setForeground(Color.blue);
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Exportar datos para RHRV");
        jCheckBox1.addChangeListener(new DialogKubiosRHRV_jCheckBox1_changeAdapter(this));
        jCheckBox2.setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        jCheckBox2.setForeground(Color.blue);
        jCheckBox2.setText("Exportar datos para Kubios");
        jCheckBox2.addChangeListener(new DialogKubiosRHRV_jCheckBox2_changeAdapter(this));
        jPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(25);
        jButton1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        jButton1.setForeground(Color.blue);
        jButton1.setText("Siguiente >>");
        jButton1.addActionListener(new DialogKubiosRHRV_jButton1_actionAdapter(this));
        getContentPane().add(panel1);
        panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jCheckBox1);
        jPanel1.add(jCheckBox2);
        panel1.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton1);
    }

    public void jCheckBox1_stateChanged(ChangeEvent e) {
        jCheckBox2.setSelected(!jCheckBox1.isSelected());
    }

    public void jCheckBox2_stateChanged(ChangeEvent e) {

        jCheckBox1.setSelected(!jCheckBox2.isSelected());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        exportRHRV = jCheckBox1.isSelected();
        this.dispose();
    }

    public boolean isExportRHRV() {
        return exportRHRV;
    }
}


class DialogKubiosRHRV_jButton1_actionAdapter implements ActionListener {
    private DialogKubiosRHRV adaptee;
    DialogKubiosRHRV_jButton1_actionAdapter(DialogKubiosRHRV adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class DialogKubiosRHRV_jCheckBox2_changeAdapter implements ChangeListener {
    private DialogKubiosRHRV adaptee;
    DialogKubiosRHRV_jCheckBox2_changeAdapter(DialogKubiosRHRV adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jCheckBox2_stateChanged(e);
    }
}


class DialogKubiosRHRV_jCheckBox1_changeAdapter implements ChangeListener {
    private DialogKubiosRHRV adaptee;
    DialogKubiosRHRV_jCheckBox1_changeAdapter(DialogKubiosRHRV adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jCheckBox1_stateChanged(e);
    }
}
