package es.usc.gsi.conversorDatosMIT.interfaz;

import java.io.*;
import java.util.*;

import javax.swing.*;

public class DialogoAbrir extends JFileChooser {

    public DialogoAbrir() {
        super(); // Se crea tal cual.
    }

    // Sobreescritura del método que indica si un directorio o archivo se puede visitar.
    // Debido a las exigencias de la aplicación, los directorios de pacientes
    // no deben ser visitables: tan sólo debe ser posible marcarlos para abrirlos.
    // Para implementar este comportamiento, usamos que el método isTraversable es llamado
    // en repetidas ocasiones cada vez que se accede a una carpeta; en concreto, se llama a este método
    // tantas veces como ficheros FILTRADOS USANDO FiltroDirectorio haya en un directorio. Debido a esto,
    // lo único que tenemos que hacer es asegurarnos de que existe algún fichero .hea en ese directorio
    // y si este es el caso, retrocedemos al directorio inmediatamente superior.


    public boolean isTraversable(File f) {
        boolean res = super.isTraversable(f);
        if (f.getName().indexOf(".hea") != -1) {
            this.changeToParentDirectory(); // Si es un directorio, el valor de res podría cambiar.
        }

        return res;
    }


} // Fin clase DialogoAbrir
