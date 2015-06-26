package es.usc.gsi.conversorDatosMIT.interfaz;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import es.usc.gsi.conversorDatosMIT.ficheros.Parametro;

public class PanelGrupoParametro extends JPanel implements ActionListener,
        ItemListener {

    private JCheckBox checkboxExportar = new JCheckBox();
    private JTextField textfieldFrecuencia = new JTextField(6);
    private JLabel labelNombre = new JLabel("Frec. (Hz)");
    private JButton botonReset = new JButton("Reset");
    private Parametro parametro;

//***************************************************************************

     public PanelGrupoParametro(Parametro parametro) {
         this.parametro = parametro;
         this.setLayout(new GridLayout(1, 2));
         this.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

         botonReset.setToolTipText("Devuelve el parmetro a su valor original");
//    botonReset.setBorder( javax.swing.BorderFactory.createRaisedBevelBorder() );
         botonReset.addActionListener(this);
         checkboxExportar.addItemListener(this);

         this.inicializa();

         this.add(checkboxExportar);

         JPanel frecPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
         frecPanel.add(labelNombre);
         frecPanel.add(textfieldFrecuencia);
         frecPanel.add(botonReset);

         this.add(frecPanel);

//    this.setSize(510,25);
     }

//***************************************************************************

     public void inicializa() {
         checkboxExportar.setText(parametro.getNombreParametro() + "-" +
                                  parametro.getUnidades());
         textfieldFrecuencia.setText(Float.toString(parametro.
                 getFrecuenciaMuestreo()));

//    labelNombre.setText("Frec. (Hz)");
//    botonReset.setText("Reset");
//    botonReset.setToolTipText("Devuelve el parmetro a su valor original");
//    botonReset.setBorder( javax.swing.BorderFactory.createRaisedBevelBorder() );
     }

//***************************************************************************

     //Resetea a los valores originales.
     public void reset() {
         parametro.resetFrecuencia();
         this.inicializa();
     }

//***************************************************************************

     public void actualizaFrecuencia() {
         float nuevoValor = Float.parseFloat(textfieldFrecuencia.getText());
         this.parametro.setFrecuenciaMuestreo(nuevoValor);
     }

//***************************************************************************

     public void setFechaInicio(String fechaInicio) {
         this.parametro.setFechaInicio(fechaInicio);
     }

//***************************************************************************

     public void setFechaFin(String fechaFin) {
         this.parametro.setFechaFin(fechaFin);
     }

//***************************************************************************

     // Metodos para gestionar eventos

     // Eventos de boton
     public void actionPerformed(ActionEvent e) {
         this.reset();
     }

    // Eventos de checkbox
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            this.parametro.setActivado(false);
        }
        if (e.getStateChange() == ItemEvent.SELECTED) {
            this.parametro.setActivado(true);
        }
    }

//***************************************************************************
     /*
       public PanelGrupoParametro() {
         this.setLayout(xYLayout1);
       }
      */

} // Fin clase PanelGrupoParametro
