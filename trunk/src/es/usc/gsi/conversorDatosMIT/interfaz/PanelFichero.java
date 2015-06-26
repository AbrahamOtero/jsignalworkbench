package es.usc.gsi.conversorDatosMIT.interfaz;

import javax.swing.*;

import es.usc.gsi.conversorDatosMIT.ficheros.FicheroHead;
import es.usc.gsi.conversorDatosMIT.ficheros.Parametro;

public class PanelFichero extends JPanel {

    public static final int LISTA = 0;
    public static final int ETIQUETAS = 1;

    private FicheroHead ficheroHead;
    private PanelInfo informacion;
    private PanelGrupoParametro[] panelG; //Array de paneles

//**********************************************************************************

     public PanelFichero(FicheroHead ficheroHead, int modoVista) {
         super();

         this.ficheroHead = ficheroHead;

         this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

         Parametro[] parametros = ficheroHead.getParametros();
         panelG = new PanelGrupoParametro[parametros.length];
         for (int i = 0; i < parametros.length; i++) {
             panelG[i] = new PanelGrupoParametro(parametros[i]);
         }

         switch (modoVista) {
         case PanelFichero.LISTA:
             this.vistaLista();
             break;

         case PanelFichero.ETIQUETAS:
             this.vistaEtiquetas();
             break;

         default:
             this.vistaLista();
         }
     }

//**********************************************************************************

     private void vistaLista() {
         this.setBorder(BorderFactory.createTitledBorder(ficheroHead.getName()));

         for (int i = 0; i < panelG.length; i++) {
             this.add(panelG[i]);
         }
     }

//**********************************************************************************

     private void vistaEtiquetas() {
         informacion = new PanelInfo(ficheroHead, PanelInfo.ARCHIVO);
         this.add(informacion);

         for (int i = 0; i < panelG.length; i++) {
             this.add(panelG[i]);
         }
     }

//**********************************************************************************

     public FicheroHead getFicheroHead() {
         return this.ficheroHead;
     }

//**********************************************************************************

     public void actualizaFrecuencia() {
         for (int i = 0; i < panelG.length; i++) {
             panelG[i].actualizaFrecuencia();
         }
     }

//**********************************************************************************

     public void actualizaFechas(String fechaInicio, String fechaFin) {
         for (int i = 0; i < panelG.length; i++) {
             panelG[i].setFechaInicio(fechaInicio);
             panelG[i].setFechaFin(fechaFin);
         }
     }


} // Fin PanelFichero