package es.usc.gsi.conversorDatosMIT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

//import conversorDatosMIT.ficheros.*;
import es.usc.gsi.conversorDatosMIT.interfaz.ControladorInterfaz;
import es.usc.gsi.conversorDatosMIT.interfaz.PanelPrincipal;
import net.javahispano.jsignalwb.JSWBManager;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class FrameConversorMIT extends JDialog {

    private PanelPrincipal conversor;


    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel panel_anadir_conversor = new JPanel();
    private JButton abrirPaciente = new JButton();
    private JButton abrirArchivo = new JButton();
    private JButton cerrarVista = new JButton();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel jPanel4 = new JPanel();
    private JButton aceptar = new JButton();
    private JButton cancelar = new JButton();
    private JButton guardarAlHdd = new JButton();
    private FlowLayout flowLayout1 = new FlowLayout();
    private FlowLayout flowLayout2 = new FlowLayout();
    private JRadioButton jRadioButton1 = new JRadioButton();
    private JRadioButton jRadioButton2 = new JRadioButton();
    private JSWBManager jswbManager;
    private File archivoAbierto = null;

    public FrameConversorMIT(Window frame, JSWBManager jswbManager) {
        super(frame, "Convertidor de formato MIT a ASCII", Dialog.ModalityType.APPLICATION_MODAL);
        conversor = ControladorInterfaz.getControlador().
                    getPanelPrincipal();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(500, 600);
        panel_anadir_conversor.add(conversor, BorderLayout.CENTER);
        this.setLocationRelativeTo(frame);
        this.jswbManager = jswbManager;
        /*        Point d = frame.getLocation();
                Dimension t = frame.getSize();
                setLocation((int) (d.x + t.getWidth() / 2 - 300),
                            (int) ((d.y) + t.getHeight() / 2 - 250));*/
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        abrirPaciente.setToolTipText(
                "Abre todos los registros que esten en un directorio.");
        abrirPaciente.setText("Abrir paciente");
        abrirPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrir_paciente_actionPerformed(e);
            }
        });
        abrirArchivo.setToolTipText("Abre un solo registro.");
        abrirArchivo.setText("Abrir archivo");
        abrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrir_archivo_actionPerformed(e);
            }
        });
        cerrarVista.setToolTipText("Cierra los registros abiertos");
        cerrarVista.setText("Cerrar vista");
        cerrarVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar_vista_actionPerformed(e);
            }
        });
        panel_anadir_conversor.setLayout(borderLayout3);
        aceptar.setToolTipText(
                "Vuelve a la interfase y carga en ella los datos seleccionados.");
        aceptar.setText("Exportar al entorno");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aceptar_actionPerformed(e);
            }
        });
        cancelar.setToolTipText("Vuelve a la interfase sin guardar los datos.");
        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar_actionPerformed(e);
            }
        });

        guardarAlHdd.setToolTipText(
                "Guarda en el disco duro las senhales seleccionadas.");
        guardarAlHdd.setText("Guardar al HDD");
        guardarAlHdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardar_al_hdd_actionPerformed(e);
            }
        });
        jPanel2.setLayout(flowLayout1);
        flowLayout1.setHgap(15);
        jPanel4.setLayout(flowLayout2);
        flowLayout2.setHgap(15);
        jPanel4.setBorder(BorderFactory.createEtchedBorder());
        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        jPanel3.setBorder(BorderFactory.createEtchedBorder());
        jRadioButton1.setToolTipText("Ver como Lenguetas");
        jRadioButton1.setActionCommand("Ver como Lenguetas");
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Ver como Lenguetas");
        jRadioButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                jRadioButton1_stateChanged(e);
            }
        });
        jRadioButton2.setToolTipText("Ver como lista");
        jRadioButton2.setText("Ver como lista");
        //Buton Group
        ButtonGroup buton_group = new ButtonGroup();
        buton_group.add(jRadioButton1);
        buton_group.add(jRadioButton2);

        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel2.add(abrirPaciente, null);
        jPanel2.add(abrirArchivo, null);
        jPanel2.add(cerrarVista, null);
        jPanel1.add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(aceptar, null);
        jPanel4.add(guardarAlHdd, null);
        jPanel4.add(cancelar, null);
        jPanel1.add(jPanel3, BorderLayout.NORTH);
        jPanel3.add(jRadioButton1, null);
        jPanel3.add(jRadioButton2, null);
        jPanel1.add(panel_anadir_conversor, BorderLayout.CENTER);
        panel_anadir_conversor.add(jPanel2, BorderLayout.SOUTH);
    }

    void abrir_paciente_actionPerformed(ActionEvent e) {
        archivoAbierto = conversor.abrirPaciente(archivoAbierto);
        conversor.setVista(PanelPrincipal.ETIQUETAS);
    }

    void abrir_archivo_actionPerformed(ActionEvent e) {
        archivoAbierto = conversor.abrirFichero(archivoAbierto);
    }

    void cerrar_vista_actionPerformed(ActionEvent e) {
        int opcion = JOptionPane.showConfirmDialog(this,
                "<html><body  text=\"#000000\"><font size=\"5\" color=\"#0033FF\">&iquest;Seguro que desea cerrar los registros abiertos?</font></body></html>"
                , "Advertencia", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            conversor.cerrarPaciente();
        }
    }


    void aceptar_actionPerformed(ActionEvent e) {
        int opcion = Integer.MIN_VALUE;

        PideDatosAlConversor pide_datos = new PideDatosAlConversor(conversor, jswbManager);

        pide_datos.cargarDatos();
        dispose();

    }

    void guardar_al_hdd_actionPerformed(ActionEvent e) {
        conversor.vuelcaDatos();
    }


    void jRadioButton1_stateChanged(ChangeEvent e) {
        if (jRadioButton1.isSelected()) {
            conversor.setVista(PanelPrincipal.ETIQUETAS);
        } else {
            conversor.setVista(PanelPrincipal.LISTA);
        }
    }

    void cancelar_actionPerformed(ActionEvent e) {
        int opcion = JOptionPane.showConfirmDialog(this,
                "<html><body text=\"#000000\"><font size=\"5\" color=\"#0033FF\">&iquest;Seguro que desea cerrar la herramienta de conversion?</font></body></html>"
                , "Advertencia", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public File getArchivoAbierto() {
        return archivoAbierto;
    }

    public void setArchivoAbierto(File archivoAbierto) {
        this.archivoAbierto = archivoAbierto;
    }


}
