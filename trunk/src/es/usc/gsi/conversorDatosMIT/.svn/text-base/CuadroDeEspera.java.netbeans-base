package es.usc.gsi.conversorDatosMIT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class CuadroDeEspera extends JDialog {

    private Border normal = BorderFactory.createLoweredBevelBorder();
    private Border selecionado = BorderFactory.createRaisedBevelBorder();
    private Window frame;
    private boolean cancelado = true;
    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JButton aceptar = new JButton();
    private JButton cancelar = new JButton();
    private JLabel jLabel1 = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JProgressBar jProgressBar1 = new JProgressBar();
    private FlowLayout flowLayout2 = new FlowLayout();


    public CuadroDeEspera(Frame frame, String title, boolean modal) {
        super(frame, title, true);
        this.frame = frame;
        construyeUI();

    }

    public CuadroDeEspera(JDialog frame, String title, boolean modal) {
        super(frame, title, true);
        this.frame = frame;
        construyeUI();
    }

    private void construyeUI() {
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 200);
        Point d = frame.getLocation();
        Dimension t = frame.getSize();
        setLocation((int) (d.x + t.getWidth() / 2 - 200),
                    (int) ((d.y) + t.getHeight() / 2 - 150));
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        this.getContentPane().setLayout(borderLayout2);
        jPanel1.setLayout(borderLayout3);
        aceptar.setEnabled(false);
        aceptar.setBorder(normal);
        aceptar.setToolTipText(
                "Volver a la Herramienta de ayuda al descubrimiento de patrones.");
        aceptar.setText("Aceptar");
        aceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                aceptar_mouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                aceptar_mouseExited(e);
            }
        });
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aceptar_actionPerformed(e);
            }
        });
        cancelar.setBorder(normal);
        cancelar.setToolTipText("Canclar el proceso de adquisicion de datos");
        cancelar.setText("Cancelar");
        cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                cancelar_mouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                cancelar_mouseExited(e);
            }
        });
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar_actionPerformed(e);
            }
        });
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel1.setForeground(Color.red);
        jLabel1.setText("Adquirendo datos del registro ...");
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setVgap(35);
        jProgressBar1.setPreferredSize(new Dimension(250, 25));
        jProgressBar1.setStringPainted(true);
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setHgap(25);
        getContentPane().add(panel1, BorderLayout.CENTER);
        panel1.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(aceptar, null);
        jPanel2.add(cancelar, null);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jLabel1, null);
        jPanel3.add(jProgressBar1, null);
    }

    /**
     * Invocar cuando el proceso halla terminado. Se active el boton de
     * aceptar y se desactiva el de cancelar.
     */
    void cambiaEstado() {
        aceptar.setEnabled(true);
        cancelar.setEnabled(false);
        jLabel1.setText("Proceso finalizado");
    }

    /**
     * Emplear para actualizar el progress bar
     * @param i
     */
    void actulizaProgressBar(int i) {
        jProgressBar1.setValue(i);
    }

    void aceptar_actionPerformed(ActionEvent e) {
        dispose();
        cancelado = false;
    }

    void cancelar_actionPerformed(ActionEvent e) {
        cancelado = true;
        this.dispose();
    }

    public boolean getCancelado() {
        return cancelado;
    }

    void aceptar_mouseEntered(MouseEvent e) {
        aceptar.setBorder(selecionado);
    }

    void aceptar_mouseExited(MouseEvent e) {
        aceptar.setBorder(normal);
    }

    void cancelar_mouseEntered(MouseEvent e) {
        aceptar.setBorder(selecionado);
    }

    void cancelar_mouseExited(MouseEvent e) {
        aceptar.setBorder(normal);
    }
}
