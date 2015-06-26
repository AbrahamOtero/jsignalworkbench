package research.beats.anotaciones;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;

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
public class LimitacionesDialog extends JDialog {
    LimitacionAnotacion limitacionAnotacion;
    boolean apnea = true, desat = false, latido = false;
    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JComboBox jComboBox1 = new JComboBox();
    private JLabel jLabel2 = new JLabel();
    private JLabel abelAutomatico = new JLabel();
    public LimitacionesDialog(Frame owner, String title, boolean modal,
                              LimitacionAnotacion limitacionesAnotaciones) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            if (limitacionesAnotaciones.getTipo() == LimitacionAnotacion.APNEA ||
                limitacionesAnotaciones.getTipo() == LimitacionAnotacion.HIPOAPNEA) {

                jLabel1.setText("Tipo de limitacion: ");
                jComboBox1.addItem("Apnea");
                jComboBox1.addItem("Hipoapnea");

            } else {
                //jPanel2.remove(this.jComboBox1);
                if (limitacionesAnotaciones.getTipo() <= 0) {
                    /* JLabel l = new JLabel( "Latido Normal");
                     l.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
                     l.setForeground(Color.blue);
                     this.jPanel2.add(l);*/

                    jLabel1.setText("Tipo de Latido: ");
                    jComboBox1.addItem("N");
                    jComboBox1.addItem("A");
                    jComboBox1.addItem("V");
                    jComboBox1.addItem("P");
                    jComboBox1.addItem("TV");
                    jComboBox1.addItem("Vrt");
                    jComboBox1.addItem("Prt");
                    apnea = false;
                    latido = true;
                } else {
                    jPanel2.remove(this.jComboBox1);
                    JLabel l = new JLabel("Desaturacion                       ");
                    l.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
                    l.setForeground(Color.blue);
                    this.jPanel2.add(l);

                    apnea = false;
                    desat = true;
                }

            }

            jPanel2.add(jLabel2);
            jPanel2.add(abelAutomatico);
            this.abelAutomatico.setText("" + limitacionesAnotaciones.isAutomatica());
            this.setSize(300, 160);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.limitacionAnotacion = limitacionesAnotaciones;
        this.setLocationRelativeTo(owner);
        if (limitacionAnotacion.getTipo() == LimitacionAnotacion.HIPOAPNEA) {
            jComboBox1.setSelectedItem("Hipoapnea");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.N) {
            jComboBox1.setSelectedItem("N");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.A) {
            jComboBox1.setSelectedItem("A");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.V) {
            jComboBox1.setSelectedItem("V");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.P) {
            jComboBox1.setSelectedItem("P");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.TV) {
            jComboBox1.setSelectedItem("TV");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.Vrt) {
            jComboBox1.setSelectedItem("Vrt");
        } else if (limitacionAnotacion.getTipo() == LimitacionAnotacion.Prt) {
            jComboBox1.setSelectedItem("Prt");
        }

        TimerTask timerTask = new TimerTask() {
            public void run() {
                dispose();
                if (limitacionAnotacion instanceof DesaturacionAnotacion) {
                    DesaturacionAnotacion d = (DesaturacionAnotacion) limitacionAnotacion;
                    d.resetColors();
                }

                JSWBManager.getJSignalMonitor().repaintAll();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 3000);

        //this.setVisible(true);
    }

    public LimitacionesDialog(LimitacionAnotacion limitacionesAnotaciones) {
        this(new Frame(), "LimitacionesDialog", false, limitacionesAnotaciones);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jButton1.setText("OK");
        jButton1.addActionListener(new LimitacionesDialog_jButton1_actionAdapter(this));
        jButton2.setText("Borrar");
        jButton2.addActionListener(new LimitacionesDialog_jButton2_actionAdapter(this));
        jButton3.setText("Borrar sin confirmar");
        jButton3.addActionListener(new LimitacionesDialog_jButton3_actionAdapter(this));
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        jLabel1.setForeground(Color.blue);
        jLabel2.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        jLabel2.setForeground(Color.blue);
        jLabel2.setText("Creado automaticamente:");
        abelAutomatico.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        abelAutomatico.setForeground(Color.blue);
        abelAutomatico.setText("jLabel3");
        jComboBox1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        jComboBox1.setForeground(Color.blue);
        getContentPane().add(panel1);
        panel1.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(jButton3);
        panel1.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jLabel1);
        jPanel2.add(jComboBox1);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        hideJWindow();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        Signal s = limitacionAnotacion.getSignal();
        if (s != null) {
            if (JOptionPane.showConfirmDialog(JSWBManager.getParentWindow(),
                                              "Are you sure?", "Delete mark", JOptionPane.YES_NO_OPTION,
                                              JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                s.removeMark(limitacionAnotacion);
                JSWBManager.getJSWBManagerInstance().refreshJSM(false);
            }
        }
        this.dispose();
    }

    /**
     * hideJWindow
     */
    private void hideJWindow() {
        if (apnea && ((String) jComboBox1.getSelectedItem()).equals("Apnea")) {
            limitacionAnotacion.setTipo(LimitacionAnotacion.APNEA);
        } else if (apnea) {
            limitacionAnotacion.setTipo(LimitacionAnotacion.HIPOAPNEA);
        }
        if (latido) {
            if (((String) jComboBox1.getSelectedItem()).equals("N")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.N);
            } else if (((String) jComboBox1.getSelectedItem()).equals("A")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.A);
            } else if (((String) jComboBox1.getSelectedItem()).equals("V")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.V);
            } else if (((String) jComboBox1.getSelectedItem()).equals("P")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.P);

            } else if (((String) jComboBox1.getSelectedItem()).equals("TV")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.TV);
            } else if (((String) jComboBox1.getSelectedItem()).equals("Vrt")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.Vrt);
            } else if (((String) jComboBox1.getSelectedItem()).equals("Prt")) {
                limitacionAnotacion.setTipo(LimitacionAnotacion.Prt);
            }
            if (!((String) jComboBox1.getSelectedItem()).equals("N")) {
                this.limitacionAnotacion.setAutomatica(false);
            }
        }
        this.dispose();
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        Signal s = limitacionAnotacion.getSignal();
        if (s != null) {
            s.removeMark(limitacionAnotacion);
            JSWBManager.getJSWBManagerInstance().refreshJSM(false);
        }
        this.dispose();
    }
}


class LimitacionesDialog_jButton3_actionAdapter implements ActionListener {
    private LimitacionesDialog adaptee;
    LimitacionesDialog_jButton3_actionAdapter(LimitacionesDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}


class LimitacionesDialog_jButton2_actionAdapter implements ActionListener {
    private LimitacionesDialog adaptee;
    LimitacionesDialog_jButton2_actionAdapter(LimitacionesDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class LimitacionesDialog_jButton1_actionAdapter implements ActionListener {
    private LimitacionesDialog adaptee;
    LimitacionesDialog_jButton1_actionAdapter(LimitacionesDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
