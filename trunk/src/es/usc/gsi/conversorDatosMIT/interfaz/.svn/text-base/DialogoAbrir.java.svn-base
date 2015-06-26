package es.usc.gsi.conversorDatosMIT.interfaz;

import java.io.File;

import javax.swing.JFileChooser;

public class DialogoAbrir extends JFileChooser {

    public DialogoAbrir() {
        super(); // Se crea tal cual.
    }

    // Sobreescritura del metodo que indica si un directorio o archivo se puede visitar.
    // Debido a las exigencias de la aplicacion, los directorios de pacientes
    // no deben ser visitables: tan solo debe ser posible marcarlos para abrirlos.
    // Para implementar este comportamiento, usamos que el metodo isTraversable es llamado
    // en repetidas ocasiones cada vez que se accede a una carpeta; en concreto, se llama a este metodo
    // tantas veces como ficheros FILTRADOS USANDO FiltroDirectorio haya en un directorio. Debido a esto,
    // lo unico que tenemos que hacer es asegurarnos de que existe algun fichero .hea en ese directorio
    // y si este es el caso, retrocedemos al directorio inmediatamente superior.


    public boolean isTraversable(File f) {
        boolean res = super.isTraversable(f);
        if (f.getName().indexOf(".hea") != -1) {

            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    changeToParentDirectory(); // Si es un directorio, el valor de res podria cambiar.
                }
            };
            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

        }

        return res;
    }


} // Fin clase DialogoAbrir
