// Clase Paciente: encapsula la informacion general de un paciente
// y su presentacion grafica.
package es.usc.gsi.conversorDatosMIT.interfaz;

import java.awt.*;

import javax.swing.*;

import es.usc.gsi.conversorDatosMIT.ficheros.*;

public class Paciente extends JPanel {

    public static final int LISTA = 0;
    public static final int ETIQUETAS = 1;

    private PanelFecha panelFecha;
    private PanelEtiquetadoGeneral panelEtiquetado;
    private int modoVista;

    public Paciente(FicheroHead[] ficherosHead, int modoVista) {
        this.setLayout(new BorderLayout());
        this.panelFecha = new PanelFecha(ficherosHead[0].getFechaInicio(),
                                         ficherosHead[0].getFechaFin());
        this.creaVista(ficherosHead, modoVista);
    }

    public Paciente(FicheroHead[] ficherosHead) {
        this(ficherosHead, Paciente.LISTA);
    }

    // Necesario llamar a este metodo despues de haber a�adido
    // este componente a un contenedor: es la unica manera de que el tama�o
    // sea respecto al contenedor. MEJORAR ESTO.
    public void anhadeSubPanel() {
        // LLAMAR A M�TODOS DE SUBPANELES??
        this.add(panelFecha, BorderLayout.NORTH);
        this.add(panelEtiquetado, BorderLayout.CENTER);
        this.validate();
    }

    private void creaVista(FicheroHead[] ficherosHead, int modoVista) {
        this.modoVista = modoVista;

        switch (modoVista) {
        case Paciente.LISTA:
            panelEtiquetado = new PanelEtiquetadoSimple(ficherosHead); // PRESENTACION EN FORMA DE LISTA
            break;
        case Paciente.ETIQUETAS:
            panelEtiquetado = new PanelEtiquetado(ficherosHead); // PRESENTACION EN VARIAS LENG�ETAS
            break;
        default:
            panelEtiquetado = new PanelEtiquetadoSimple(ficherosHead);
        }
    }

    public void cambiaVista(FicheroHead[] ficherosHead, int modoVista) {
        if (this.modoVista == modoVista) {
            return; // Si la  vista no cambia, no hacemos nada.
        }

        this.cerrarPaciente(); // Cerramos los paneles abiertos anteriormente
        this.creaVista(ficherosHead, modoVista); // Creamos una nueva vista.
        this.anhadeSubPanel(); // Se puede llamar desde aqui, ya que se supone que este componente ya ha sido a�adido.
    }

    public void cerrarPaciente() {
        panelEtiquetado.cerrarTodosFicheros();
        this.removeAll();
        panelEtiquetado = null;
        this.validate();
    }

    /* ERA PARA SELECCIONAR UN FICHEROHEAD CUANDO HAB�A VARIOS, EN VEZ DE UN PACIENTE
       public FicheroHead getFicheroHeadSeleccionado() {
      return panelEtiquetado.getFicheroHeadSeleccionado();
       } */

    public void actualizaFrecuencias() {
        panelEtiquetado.actualizaFrecuencias();
    }

    public void actualizaFechas() throws Exception {

        String fIni = panelFecha.getFechaInicio();
        String fFin = panelFecha.getFechaFin();
        panelEtiquetado.actualizaFechas(fIni, fFin);
    }


} // Fin clase Paciente
