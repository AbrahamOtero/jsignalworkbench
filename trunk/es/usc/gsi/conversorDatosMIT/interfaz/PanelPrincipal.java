// Clase PanelPrincipal: sirve para poder anhadir toda la funcionalidad de
// la aplicacion a otra aplicacion como modulo independiente.
// Lo unico que hay que hacer es crear un nuevo objeto PanelPrincipal
// y anhadirlo a otro contenedor.

package es.usc.gsi.conversorDatosMIT.interfaz;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;

import es.usc.gsi.conversorDatosMIT.ficheros.*;

public class PanelPrincipal extends JPanel /* implements ComponentListener */ {

    private ControladorInterfaz controlInterfaz = ControladorInterfaz.
                                                  getControlador();
    private ControladorFicheros controlFicheros = ControladorFicheros.
                                                  getControlador();

    public static final int LISTA = 0;
    public static final int ETIQUETAS = 1;

    private Paciente paciente = null;
//  private Frame ventanaPrincipal=null; // Ventana principal de la aplicacion.

    PanelPrincipal() {
        // Enlazamos ambos controladores.
        ControladorInterfaz.enlazaControladorFicheros();
        ControladorFicheros.enlazaControladorInterfaz();

        this.setLayout(new BorderLayout());
    }

    public void abrirPaciente(FicheroHead[] ficherosHead) {
        this.cerrarPaciente(); // Cerramos el paciente actual para poder abrir otro.

        paciente = new Paciente(ficherosHead);

        this.add(paciente, BorderLayout.CENTER);

        paciente.anhadeSubPanel();
        this.validate(); // IMPRESCINDIBLE: SI NO, NO PINTA EL PANEL.
        //ESTO ES DEBIDO A QUE EN SWING EN --CASI-- TODOS LOS CASOS SE LLAMA A validate()
        // DE MANERA AUTOMATICA, PERO EN ESTE CASO NO. HAY QUE LLAMARLO SIEMPRE QUE
        // SE ANHADA UN COMPONENTE A OTRO QUE YA HA SIDO PINTADO
        // Y QUE NO LLAME AUTOMATICAMENTE A validate().
        this.repaint();

    }

    //////////

    public void cerrarPaciente() {

        if (paciente != null) {
            this.remove(paciente); // Sacamos el panel paciente
            this.validate(); // validamos para que el contenedor se actualice a la nueva situacion
            this.repaint(); // repintamos
            paciente.cerrarPaciente(); // destruimos los objetos de paciente
            paciente = null; // destruimos el paciente.
        }

    }

    ////////
    /* ERA PARA OBTENER EL FICHEROHEAD SELECCIONADO EN UNA LENGUETA
      public FicheroHead getFicheroHeadSeleccionado() {

        return paciente.getFicheroHeadSeleccionado();

        } */
    ///////

    public void actualizaFrecuencias() {

        if (paciente != null) {
            paciente.actualizaFrecuencias();
        }

    }

    public void actualizaFechas() throws Exception {

        if (paciente != null) {
            paciente.actualizaFechas();
        }

    }

    public void cambiaVista(FicheroHead[] ficherosHead, int modoVista) {

        if (paciente == null) {
            return;
        }

        paciente.cambiaVista(ficherosHead, modoVista);
        this.validate();

    }

    ////////
    /*
      public Frame getVentanaPrincipal() {
        if (ventanaPrincipal==null) {
          Component componente=this;
     while( !(componente instanceof Frame) ) componente=componente.getParent();
          ventanaPrincipal=(Frame) componente;
        }
        return ventanaPrincipal;
      }
     */

    /// METODOS PARA ABRAHAM: VOLCADO DE DATOS Y OBTENCION DE ARRAY DE PARAMETROS.

    // 1.- VOLCADO DE DATOS

    public void vuelcaDatos() {
        controlInterfaz.exportaFicheros();
    }

    // 2.- OBTENCION DE ARRAY DE PARAMETROS

    public Parametro[] getParametros() throws OutOfMemoryError {
        return controlInterfaz.getParametrosSeleccionados();
    }

    // 3.- ABRIR ARCHIVOS

    public File abrirPaciente(File archivoAbierto) {
        return controlInterfaz.abrirPaciente(archivoAbierto);
    }

    public File abrirFichero(File archivoAbierto) {

        return controlInterfaz.abrirFichero(archivoAbierto);
    }

    public void setVista(int modo) {

        switch (modo) {
        case PanelPrincipal.LISTA:
            controlInterfaz.cambiaVista(controlInterfaz.LISTA);
            break;
        case PanelPrincipal.ETIQUETAS:
            controlInterfaz.cambiaVista(controlInterfaz.ETIQUETAS);
            break;
        default:
            controlInterfaz.cambiaVista(controlInterfaz.ETIQUETAS);
        }

    }

    ///////////

    /*
       // Eventos de cambio de status: interfaz ComponentListener

      public void componentHidden(ComponentEvent e){}
      public void componentMoved(ComponentEvent e){}
      public void componentResized(ComponentEvent e){}

      public void componentShown(ComponentEvent e) {

        }
       // Fin eventos cambio de status */

} // Fin clase PanelPrincipal
