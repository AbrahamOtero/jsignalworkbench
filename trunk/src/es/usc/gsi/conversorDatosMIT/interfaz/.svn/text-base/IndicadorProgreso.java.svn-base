// *************
// Todavia por implementar correctamente: centrar componentes, decicir tamanhos,
// establecer modelo de communicaciones, usar multithreading para actualizarlo, etc.
// Es necesario hacer multithreading?
// *************
package es.usc.gsi.conversorDatosMIT.interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.usc.gsi.conversorDatosMIT.ficheros.Cancelar;

public class IndicadorProgreso extends JDialog implements ActionListener {


    private JLabel labelTexto = new JLabel();
    private JProgressBar barraProgreso;

    private Cancelar hilo;

    private boolean _stop = false;


//***********************************************************************************

     public IndicadorProgreso(Cancelar h, String titulo, String textoPrincipal,
                              int max, int min, boolean _stop) {

         super((Frame)null, titulo, true); // Indicador modal

         this._stop = _stop;
         this.hilo = h;
         this.barraProgreso = new JProgressBar(min, max);
         this.barraProgreso.setStringPainted(true);

         this.labelTexto.setText(textoPrincipal);
         this.labelTexto.setHorizontalAlignment(JLabel.CENTER);

         this.setSize(300, 150);

         Toolkit tk = Toolkit.getDefaultToolkit();
         Dimension d = tk.getScreenSize();
         this.setLocation((d.width - 300) / 2, (d.height - 150) / 2);

         JButton cancelButton = new JButton("Cancelar");
         cancelButton.addActionListener(this);

         JPanel cancelPanel = new JPanel();
         cancelPanel.add(cancelButton);

         JPanel barraPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15,
                 15));
         barraPanel.add(barraProgreso);

         this.getContentPane().add(labelTexto, BorderLayout.NORTH);
         this.getContentPane().add(barraPanel, BorderLayout.CENTER);
         this.getContentPane().add(cancelPanel, BorderLayout.SOUTH);

         this.setResizable(false);
//    this.setLocationRelativeTo(propietario);
     }


//***********************************************************************************

     public void setProgreso(int valorProgreso) {
         barraProgreso.setValue(valorProgreso);
         this.actualiza();
     }

//***********************************************************************************

     private void actualiza() {
//    labelTexto.setText(textoPrincipal + "..." + textoSecundario);
//    labelTexto.repaint();
         barraProgreso.repaint();
     }

//***********************************************************************************

     public void actionPerformed(ActionEvent ev) {
         if (!_stop) {
             hilo.cancelar();
         } else {
             ((Thread) hilo).stop();
             this.dispose();
         }
//    ((Thread)hilo).stop();
//    hilo.interrupt();
//    this.dispose();
     }

} // Fin IndicadorProgreso.
