package net.javahispano.jsignalwb.plugins.signalgeneration;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import java.util.LinkedList;
import javax.swing.Box;
import java.awt.Component;
import net.javahispano.jsignalwb.*;
import java.util.*;
import javax.swing.JTabbedPane;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.*;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.temporalseries.*;

public class SignalGeneration extends JDialog {

    private java.util.List<Seno> listaSenos = new LinkedList<Seno>();

    private Image image = Toolkit.getDefaultToolkit().createImage(
            SignalGeneration.class.getResource("pulso.jpg"));

    private Icon pulso = new ImageIcon(image);
    private Image image2 = Toolkit.getDefaultToolkit().createImage(
            SignalGeneration.class.getResource("seno.jpg"));


    private Image logo = Toolkit.getDefaultToolkit().createImage(
            SignalGeneration.class.getResource("logo.gif"));


    private Icon seno = new ImageIcon(image2);


    public SignalGeneration(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.setIconImage(logo);
        this.tAmplitud.requestFocus();
    }

    public SignalGeneration() {
        this((JFrame) JSWBManager.getJSWBManagerInstance().getParentWindow(),
             "SignalGeneration", false);
    }

    private void jbInit() throws Exception {
        Font fuente = new java.awt.Font("Tahoma", Font.BOLD, 12);
        panel1.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(10, 160));
        jPanel1.setLayout(gridLayout1);
        tAmplitud.setColumns(10);
        jLabel1.setFont(fuente);
        jLabel1.setForeground(Color.blue);
        jLabel1.setText("* Sin ( ");
        jLabel2.setFont(fuente);
        jLabel2.setForeground(Color.blue);
        jLabel2.setText("* t + ");
        tFrecuencia.setToolTipText("You can append \"*PI\" to mutuply by PI");
        tFrecuencia.setColumns(10);
        tDesfase.setToolTipText("You can append \"*PI\" to mutuply by PI");
        tDesfase.setText("0");
        tDesfase.setColumns(10);
        jLabel3.setFont(fuente);
        jLabel3.setForeground(Color.blue);
        jLabel3.setText(")");
        jButton1.setText("Add");
        jButton1.addActionListener(new SignalGeneration_jButton1_actionAdapter(this));
        gridLayout1.setColumns(1);
        gridLayout1.setRows(4);
        jLabel4.setFont(fuente);
        jLabel4.setForeground(Color.blue);
        jLabel4.setText("Length of the signal (seg):");
        ruido.setFont(fuente);
        ruido.setForeground(Color.blue);
        ruido.setText("Noise   Maximum noise magnitude:");
        ruido.addActionListener(new SignalGeneration_ruido_actionAdapter(this));
        tDuracion.setText("100");
        tDuracion.setColumns(10);
        ruidoMagnitud.setEnabled(false);
        ruidoMagnitud.setText("0");
        ruidoMagnitud.setColumns(10);
        jButton2.setText("Generate");
        jButton2.addActionListener(new SignalGeneration_jButton2_actionAdapter(this));
        jButton3.setText("Cancel");
        jButton3.addActionListener(new SignalGeneration_jButton3_actionAdapter(this));
        panelAnadir.setLayout(layout);
        layout.setColumns(1);
        jButton4.setText("Delete");
        jButton4.addActionListener(new SignalGeneration_jButton4_actionAdapter(this));
        jLabel5.setFont(fuente);
        jLabel5.setForeground(Color.blue);
        jLabel5.setText("Sample rate: ");
        tRate.setText("100");
        tRate.setColumns(5);
        jLabel6.setFont(fuente);
        jLabel6.setForeground(Color.blue);
        jLabel6.setText(
                "Start of the signal:");
        tPrincipio.setText("0");
        tPrincipio.setColumns(10);
        jLabel7.setFont(fuente);
        jLabel7.setForeground(Color.blue);
        jLabel7.setText("Signal\'s name:");
        tNnombre.setColumns(15);
        jCheckBorrar.setFont(fuente);
        jCheckBorrar.setForeground(Color.blue);
        jCheckBorrar.setText("Erase signals before adding this one");
        jLabel8.setFont(fuente);
        jLabel8.setForeground(Color.blue);
        jLabel8.setText("Offset");
        tOffset.setText("0");
        tOffset.setColumns(10);
        hstrut1.setPreferredSize(new Dimension(18, 0));
        hstrut1.setMinimumSize(new Dimension(18, 0));
        hstrut1.setMaximumSize(new Dimension(18, 32767));
        panelSquare.setLayout(borderLayout2);
        jLabelIconoSquare.setIcon(pulso);
        jPanel8.setLayout(gridLayout2);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(5);
        tSquareArriba.setColumns(10);
        jLabel10.setFont(fuente);
        jLabel10.setForeground(Color.blue);
        jLabel10.setText("Time on high value  (seg):");
        tSquarBajada.setText("0");
        tSquarBajada.setColumns(10);
        jLabel11.setFont(fuente);
        jLabel11.setForeground(Color.blue);
        jLabel11.setText("Time to change fom high value to low value  (seg):");
        tSquareSubida.setText("0");
        tSquareSubida.setColumns(10);
        jLabel12.setFont(fuente);
        jLabel12.setForeground(Color.blue);
        jLabel12.setText("Time to change fom low value to high value  (seg):");
        tSquareAbajo.setColumns(10);
        jLabel13.setFont(fuente);
        jLabel13.setForeground(Color.blue);
        jLabel13.setText("Time on low value  (seg):");
        jPanel9.setLayout(flowLayout1);
        jLabel14.setFont(fuente);
        jLabel14.setForeground(Color.blue);
        jLabel14.setText("Maximum");
        tSquareMaximum.setColumns(10);
        jLabel15.setFont(fuente);
        jLabel15.setForeground(Color.blue);
        jLabel15.setText("Minimum:");
        tSquareMinimum.setColumns(10);
        jPanel14.setLayout(borderLayout3);
        jCheckPeriodic.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jCheckPeriodic.setForeground(Color.blue);
        jCheckPeriodic.setToolTipText(
                "If not selected only one period of the pulse will be generated");
        jCheckPeriodic.setSelected(true);
        jCheckPeriodic.setText("Periodic");
        jPanel13.setLayout(flowLayout2);
        flowLayout2.setHgap(10);
        jPanel2.add(tAmplitud);
        jPanel2.add(jLabel1);
        jPanel2.add(tFrecuencia);
        jPanel2.add(jLabel2);
        jPanel2.add(tDesfase);
        jPanel2.add(jLabel3);
        jPanel2.add(jButton1);
        jPanel2.add(vstrut1);
        jPanel2.add(jButton4);
        jPanel4.add(jLabel8);
        jPanel4.add(tOffset);
        jPanel4.add(hstrut1);
        jPanel4.add(hstrut2);
        jPanel4.add(ruido);
        jPanel4.add(ruidoMagnitud);
        jPanel5.add(jCheckBorrar);
        jPanel5.add(jButton2);
        jPanel5.add(jButton3);
        panel1.add(panel, java.awt.BorderLayout.CENTER);
        panel.getViewport().add(jPanel14);
        jPanel14.add(jLabelIconoSeno, java.awt.BorderLayout.EAST);
        panel1.add(jPanel2, java.awt.BorderLayout.NORTH);
        jPanel3.add(jLabel4);
        jPanel3.add(tDuracion);
        jPanel3.add(jLabel5);
        jPanel3.add(tRate);
        jPanel1.add(jPanel3);
        jPanel1.add(jPanel4);
        jPanel1.add(jPanel6);
        jPanel1.add(jPanel5);
        jPanel6.add(jLabel7);
        jPanel6.add(tNnombre);
        jPanel6.add(jLabel6);
        jPanel6.add(tPrincipio);

        jTabbedPane1.add(panel1, "Sun of sins");
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        this.getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        panelSquare.add(jPanel7, java.awt.BorderLayout.EAST);
        jPanel7.add(jLabelIconoSquare);
        panelSquare.add(jPanel8, java.awt.BorderLayout.CENTER);
        jPanel8.add(jPanel9);
        jPanel9.add(jLabel10);
        jPanel9.add(hstrut3);
        jPanel9.add(tSquareArriba);
        jPanel8.add(jPanel10);
        jPanel10.add(jLabel11);
        jPanel10.add(tSquarBajada);
        jPanel8.add(jPanel11);
        jPanel11.add(jLabel13);
        jPanel11.add(hstrut4);
        jPanel11.add(tSquareAbajo);
        jPanel8.add(jPanel12);
        jPanel8.add(jPanel13);
        jPanel13.add(jCheckPeriodic);
        jPanel13.add(jLabel15);
        jPanel13.add(tSquareMinimum);
        jPanel13.add(jLabel14);
        jPanel13.add(tSquareMaximum);
        jPanel12.add(jLabel12);
        jPanel12.add(tSquareSubida);
        jTabbedPane1.add(panelSquare, "Square/Triangle");
        this.jPanel14.add(this.panelAnadir, BorderLayout.CENTER);
        this.jLabelIconoSeno.setIcon(seno);
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void ruido_actionPerformed(ActionEvent e) {
        this.ruidoMagnitud.setEnabled(this.ruido.isSelected());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        double amplitud, frecuencia, desfase;

        try {
            amplitud = leerYValidarJTextFiel(tAmplitud,
                                             "Valor incorrecto para la amplitud del seno");
            frecuencia = leerYValidarJTextFiel(tFrecuencia,
                                               "Valor incorrecto para la frecuencia", true);
            desfase = leerYValidarJTextFiel(tDesfase,
                                            "Valor incorrecto para el desfase", true);
        } catch (Exception ex) {
            return;
        }

        SenoGUI s = new SenoGUI(amplitud, frecuencia, desfase);
        this.layout.setRows(this.layout.getRows() + 1);
        panelAnadir.add(s);
        this.validate();
        listaSenos.add(new Seno(amplitud, frecuencia, desfase, s));

    }

    public void jButton4_actionPerformed(ActionEvent e) {
        for (Seno sen : listaSenos) {
            if (sen.isSeleccionado()) {
                panelAnadir.remove(sen.getPanel());
                this.layout.setRows(this.layout.getRows() - 1);

            }
        }
        this.validate();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        double muestreo, longitud;
        double amplitudRuido, offset, inicio, ceros;
        long origen;
        String nombre;

        float datos[];

        try {
            longitud = leerYValidarJTextFiel(this.tDuracion,
                                             "Revise la duraci�n de la se�al");
            muestreo = leerYValidarJTextFiel(tRate,
                                             "Revise la frecuencia de muestreo");
            inicio = leerYValidarJTextFiel(this.tPrincipio,
                                           "Revise el instante de inicio");
            offset = leerYValidarJTextFiel(this.tOffset, "Revise el offset");
        } catch (Exception ex1) {
            return;
        }

        nombre = tNnombre.getText();
        SignalManager signalManager = JSWBManager.getJSWBManagerInstance().
                                      getSignalManager();
        if (nombre.equals("")) {
            mostrarError("Es necesario introducir un nombre para la se�al");

            return;
        } else if (signalManager.exists(nombre) &&
                   !this.jCheckBorrar.isSelected()) {
            mostrarError(
                    "Ya existe una se�al con ese nombre. Introduzca otro nombre diferente");
            return;
        }

        if (this.jTabbedPane1.getSelectedIndex() == 0) {
            if (listaSenos.size() == 0) {
                mostrarError(
                        "");
                return;
            }
            datos = generarSenalSeno(muestreo, longitud, offset, inicio);
        } else {
            double arriba, abajo, ascenso, descenso, minimo = 1, maximo = 1;

            try {
                ascenso = leerYValidarJTextFiel(tSquareSubida,
                                                " Valor incorrecto para la duraci�n del tramo desubida");
                descenso = leerYValidarJTextFiel(tSquarBajada,
                                                 " Valor incorrecto para la duraci�n del tramo debajada");
                arriba = leerYValidarJTextFiel(tSquareArriba,
                                               " Valor incorrecto para la duraci�n del tramo alto");
                abajo = leerYValidarJTextFiel(tSquareAbajo,
                                              " Valor incorrecto para la duraci�n del tramo bajo");
                minimo = leerYValidarJTextFiel(tSquareMinimum,
                                               " Valor incorrecto para el valor m�nimo");
                maximo = leerYValidarJTextFiel(tSquareMaximum,
                                               " Valor incorrecto para el valor m�ximo");
            } catch (Exception ex2) {
                return;
            }

            datos = generaCuadrado(muestreo, longitud, arriba, abajo, ascenso,
                                   descenso, minimo, maximo, offset,
                                   this.jCheckPeriodic.isSelected());

        }

        if (ruido.isSelected()) {
            try {
                amplitudRuido = leerYValidarJTextFiel(ruidoMagnitud,
                        "Valor incorrecto para el ruido");
            } catch (Exception ex) {
                return;
            }
            for (int i = 0; i < datos.length; i++) {
                datos[i] += amplitudRuido * Math.random();
            }

        }

        float max = Float.MIN_VALUE, min = Float.MAX_VALUE;
        //no tenemos en cuenta los ceros del principio
        for (int i = 0; i < datos.length; i++) {
            max = (max > datos[i]) ? max : datos[i];
            min = (min > datos[i]) ? min : datos[i];
        }

        TemporalSeries signal = new TemporalSeries(nombre, datos, (float) muestreo, 0,
                "Unidades");
        signal.setMinIndex((int)inicio);
        signalManager.addSignal(signal);
        JSWBManager.getJSWBManagerInstance().setJSMFrecuency((float) muestreo);
        JSWBManager.getJSignalMonitor().setShowTimeLeyend(false);
        JSWBManager.getJSignalMonitor().setZoomForFullView();
        // System.out.println("signal.setVisibleRange(min,max,signal.getProperties().getAbscissaValue()); "+signal.setVisibleRange(min,max,signal.getProperties().getAbscissaValue()));
        signal.adjustVisibleRange();
        this.dispose();
    }

    private long calcularPrincipio(SignalManager signalManager) {
        long principio;
//si vamos a borrar todas las se�ales tomamos el instante actual
        if (this.jCheckBorrar.isSelected()) {
            signalManager.removeAllSignals();
            principio = (new Date()).getTime();

        }
//en caso contrario el m�nimo de los principios de las se�ales a�adidas
        else {
            Collection<Signal> s = signalManager.getSignals();
            principio = Long.MAX_VALUE;
            for (Signal elem : s) {
                if (elem instanceof TemporalSeries) {
                   // principio = Math.min(principio,
                     //                    ((TemporalSeries) elem).getTimeOrigin());
                    //todas tienen el mismo principio, no hay que buscar m\u2663s
                    return principio;
                }
            }
            principio = (new Date()).getTime();

        }
        return principio;
    }

    private static double leerYValidarJTextFiel(JTextField textField,
                                                String textoError) {
        return leerYValidarJTextFiel(textField,
                                     textoError, false);
    }

    private static double leerYValidarJTextFiel(JTextField textField,
                                                String textoError,
                                                boolean frecuencia) {
        double dato = 0.0;
        String texto = textField.getText();
        try {
            if (!frecuencia) {
                dato = Double.parseDouble(texto);
            } else {
                dato = procesarFrecuencia(texto);
            }
        } catch (NumberFormatException ex) {
            mostrarError(textoError);
            textField.requestFocus();
            textField.selectAll();
            throw ex;
        }
        return dato;
    }

    private static float[] generaCuadrado(double muestreo, double longitud,
                                          double arriba, double abajo,
                                          double ascenso, double descenso,
                                          double minimo, double maximo,
                                          double offset, boolean periodico) {
        int tamano;
        tamano = (int) (longitud * muestreo); //pasamos de segundos a muestras
        ascenso *= muestreo;
        descenso *= muestreo;
        arriba *= muestreo;
        abajo *= muestreo;
        //calculamos el paso de subida y bajada
        double diferencia = Math.abs(maximo - minimo);
        double sube = diferencia / ascenso;
        double baja = diferencia / descenso;

        //si es no periodico solo necesitamos espacio para un periodo
        if (!periodico) {
            tamano = (int) (ascenso + descenso + arriba + abajo);
        }
        float[] datos = new float[tamano];
        for (int i = 0; i < datos.length; i++) {
            //tramo de valor constante arriba
            int j;
            for (j = i; j < i + arriba && j < datos.length; j++) {
                datos[j] = (float) (maximo + offset);
            }
            // ponemos el indice en el sitio correcto
            i += arriba;

            //tramo de bajada
            for (j = i; j < i + descenso && j < datos.length; j++) {
                datos[j] = (float) (maximo + offset - (j - i) * baja);
            }
            i += descenso;

            //tramo de valor constante abajo
            for (j = i; j < i + abajo && j < datos.length; j++) {
                datos[j] = (float) (minimo + offset);
            }
            // ponemos el indice en el sitio correcto
            i += abajo;

            //tramo de subida
            for (j = i; j < i + ascenso && j < datos.length; j++) {
                datos[j] = (float) (minimo + offset + (j - i) * sube);
            }
            //nos aseguramos que el utimo valor es el adecuado
            i += ascenso;
            i--;
        }
        return datos;
    }

    private static void mostrarError(String mensaje) throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getJSWBManagerInstance().
                                      getParentWindow(),
                                      mensaje,
                                      "Error", JOptionPane.ERROR_MESSAGE);
    }

    private float[] generarSenalSeno(double muestreo, double longitud,
                                     double offset, double inicio
            ) {
        double amplitud = 0.0;
        double desfase = 0.0;
        double frecuencia;
        float[] datos = new float[(int) (longitud * muestreo)];

        for (Seno elem : listaSenos) {
            amplitud = elem.getAmplitud();
            frecuencia = elem.getFrecuencia();
            desfase = elem.getDesfase();
            inicio *= muestreo;
            for (int i = (int)inicio; i < datos.length+(int)inicio; i++) {
                datos[i-(int)inicio] += (float) (amplitud *
                                     Math.sin(frecuencia *
                                              ((i) / muestreo) +
                                              desfase));
            }
        }
        if (offset != 0) {
            for (int i = 0; i < datos.length; i++) {
                datos[i] += offset;
            }
        }

        return datos;
    }

    private static double procesarFrecuencia(String texto) throws
            NumberFormatException {
        double muestreo;
        texto = texto.replaceAll(" ", "");
        texto = texto.toLowerCase();
        int posicionPI = texto.indexOf("*pi");
        boolean multiplicar;
        if (posicionPI == -1) {
            multiplicar = false;
        } else {
            multiplicar = true;
            texto = texto.substring(0, posicionPI);
        }
        ;

        muestreo = Double.parseDouble(texto);
        if (multiplicar) {
            muestreo *= Math.PI;
        }

        return muestreo;
    }

    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JTextField tAmplitud = new JTextField();
    JLabel jLabel1 = new JLabel();
    JTextField tFrecuencia = new JTextField();
    JLabel jLabel2 = new JLabel();
    JTextField tDesfase = new JTextField();
    JLabel jLabel3 = new JLabel();
    JButton jButton1 = new JButton();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel4 = new JLabel();
    JTextField tDuracion = new JTextField();
    JCheckBox ruido = new JCheckBox();
    JTextField ruidoMagnitud = new JTextField();
    JPanel jPanel4 = new JPanel();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JScrollPane panel = new JScrollPane();
    JPanel panelAnadir = new JPanel();
    GridLayout layout = new GridLayout();
    Component vstrut1 = Box.createVerticalStrut(8);
    JButton jButton4 = new JButton();
    JLabel jLabel5 = new JLabel();
    JTextField tRate = new JTextField();
    JPanel jPanel5 = new JPanel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel panelSquare = new JPanel();
    JPanel jPanel6 = new JPanel();
    JLabel jLabel6 = new JLabel();
    JTextField tPrincipio = new JTextField();
    JLabel jLabel7 = new JLabel();
    JTextField tNnombre = new JTextField();
    JCheckBox jCheckBorrar = new JCheckBox();
    JLabel jLabel8 = new JLabel();
    JTextField tOffset = new JTextField();
    Component hstrut1 = Box.createHorizontalStrut(8);
    Component hstrut2 = Box.createHorizontalStrut(8);
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel7 = new JPanel();
    JLabel jLabelIconoSquare = new JLabel();
    JPanel jPanel8 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel10 = new JPanel();
    JPanel jPanel11 = new JPanel();
    JPanel jPanel12 = new JPanel();
    JTextField tSquareArriba = new JTextField();
    JLabel jLabel10 = new JLabel();
    JTextField tSquarBajada = new JTextField();
    JLabel jLabel11 = new JLabel();
    JTextField tSquareSubida = new JTextField();
    JLabel jLabel12 = new JLabel();
    JTextField tSquareAbajo = new JTextField();
    JLabel jLabel13 = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    Component hstrut3 = Box.createHorizontalStrut(150);
    Component hstrut4 = Box.createHorizontalStrut(154);
    JPanel jPanel13 = new JPanel();
    JLabel jLabel14 = new JLabel();
    JTextField tSquareMaximum = new JTextField();
    JLabel jLabel15 = new JLabel();
    JTextField tSquareMinimum = new JTextField();
    JPanel jPanel14 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel jLabelIconoSeno = new JLabel();
    JCheckBox jCheckPeriodic = new JCheckBox();
    FlowLayout flowLayout2 = new FlowLayout();

}


class SignalGeneration_jButton2_actionAdapter implements ActionListener {
    private SignalGeneration adaptee;
    SignalGeneration_jButton2_actionAdapter(SignalGeneration adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class SignalGeneration_jButton4_actionAdapter implements ActionListener {
    private SignalGeneration adaptee;
    SignalGeneration_jButton4_actionAdapter(SignalGeneration adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton4_actionPerformed(e);
    }
}


class SignalGeneration_jButton1_actionAdapter implements ActionListener {
    private SignalGeneration adaptee;
    SignalGeneration_jButton1_actionAdapter(SignalGeneration adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class SignalGeneration_ruido_actionAdapter implements ActionListener {
    private SignalGeneration adaptee;
    SignalGeneration_ruido_actionAdapter(SignalGeneration adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.ruido_actionPerformed(e);
    }
}


class SignalGeneration_jButton3_actionAdapter implements ActionListener {
    private SignalGeneration adaptee;
    SignalGeneration_jButton3_actionAdapter(SignalGeneration adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}
