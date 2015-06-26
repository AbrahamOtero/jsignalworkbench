// Ventana principal de la aplicacion: es la que hace falta para hacer una
// aplicacion standalone.
package es.usc.gsi.conversorDatosMIT.interfaz;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame {

    private ControladorInterfaz controlInterfaz = ControladorInterfaz.
                                                  getControlador();
    private BarraMenuPrincipal barraMenu = new BarraMenuPrincipal();
    private PanelPrincipal panelPrincipal; // Contenedor de todos los demas paneles.
    private final int ANCHOVENTANA = 670;
    private final int ALTOVENTANA = 550;

    public VentanaPrincipal() {
        super("Conversor MITDB");
        try {
            jbInit();
        } catch (Exception e) {
            System.out.println(panelPrincipal);
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        this.addWindowListener(new EventoVentana());

        this.setSize(ANCHOVENTANA, ALTOVENTANA);
//    this.setResizable(false);

        // Hay que inicializarlo despues de haberle dado un tamanho a la ventana
        // o al contenedor, ya que el panelPrincipal ocupa todo
        // el espacio de su contenedor.
        panelPrincipal = controlInterfaz.getPanelPrincipal();

        this.getContentPane().add(panelPrincipal);
        // Prueba de la clase Paciente

        /* Paciente paci=new Paciente(null);
         panelPrincipal.add (paci, new XYConstraints(30, 30, 500, 500) ); */


        this.setJMenuBar(barraMenu);

    }

    // InnerClass: Eventos de Ventana
    // LLAMAR A METODOS DEL CONTROLADOR PARA COMPROBAR QUE TODO ESTA CORRECTO
    // ANTES DE CERRAR LA APLICACION
    class EventoVentana extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

} // Fin clase VentanaPrincipal
