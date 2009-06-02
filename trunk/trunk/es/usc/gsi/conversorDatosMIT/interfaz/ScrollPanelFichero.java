package es.usc.gsi.conversorDatosMIT.interfaz;

import javax.swing.JScrollPane;

public class ScrollPanelFichero extends JScrollPane {

    private PanelFichero panelFichero;

//*********************************************************************************

     public ScrollPanelFichero(PanelFichero panelFichero) {
         super();
         this.panelFichero = panelFichero;
         this.setViewportView(panelFichero);

     }

//*********************************************************************************

     public PanelFichero getPanelFichero() {
         return panelFichero;
     }


//*********************************************************************************

     public void actualizaFrecuencia() {
         panelFichero.actualizaFrecuencia();
     }

//*********************************************************************************

     public void actualizaFechas(String fechaInicio, String fechaFin) {
         panelFichero.actualizaFechas(fechaInicio, fechaFin);
     }

} // Fin clase ScrollPanelFichero