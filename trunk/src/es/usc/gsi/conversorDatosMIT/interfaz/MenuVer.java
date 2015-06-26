package es.usc.gsi.conversorDatosMIT.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuVer extends JMenu implements ActionListener {

    private ControladorInterfaz controlInterfaz = ControladorInterfaz.
                                                  getControlador();

    private JMenuItem verLista = new JMenuItem("Ver como lista");
    private JMenuItem verEtiquetas = new JMenuItem("Ver como etiquetas");

    private final String CMD_LISTA = "Lista";
    private final String CMD_ETIQUETAS = "Etiquetas";

    public MenuVer(String nombre) {

        super(nombre);

        verLista.setActionCommand(CMD_LISTA);
        verLista.addActionListener(this);
        this.add(verLista);

        verEtiquetas.setActionCommand(CMD_ETIQUETAS);
        verEtiquetas.addActionListener(this);
        this.add(verEtiquetas);
    }

    // Control de eventos
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(CMD_LISTA)) {
            controlInterfaz.cambiaVista(controlInterfaz.LISTA);
        }
        if (e.getActionCommand().equals(CMD_ETIQUETAS)) {
            controlInterfaz.cambiaVista(controlInterfaz.ETIQUETAS);
        }

    }

} // Fin MenuVer
