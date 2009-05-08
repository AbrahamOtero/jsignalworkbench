package es.usc.gsi.conversorDatosMIT.interfaz;

import java.io.*;
import java.util.*;

import javax.swing.*;

public class DialogoAbrir extends JFileChooser {

    public DialogoAbrir() {
        super(); // Se crea tal cual.
    }

    // Sobreescritura del m�todo que indica si un directorio o archivo se puede visitar.
    // Debido a las exigencias de la aplicaci�n, los directorios de pacientes
    // no deben ser visitables: tan s�lo debe ser posible marcarlos para abrirlos.
    // Para implementar este comportamiento, usamos que el m�todo isTraversable es llamado
    // en repetidas ocasiones cada vez que se accede a una carpeta; en concreto, se llama a este m�todo
    // tantas veces como ficheros FILTRADOS USANDO FiltroDirectorio haya en un directorio. Debido a esto,
    // lo �nico que tenemos que hacer es asegurarnos de que existe alg�n fichero .hea en ese directorio
    // y si este es el caso, retrocedemos al directorio inmediatamente superior.


    public boolean isTraversable(File f) {
        boolean res = super.isTraversable(f);
        if (f.getName().indexOf(".hea") != -1) {
            this.changeToParentDirectory(); // Si es un directorio, el valor de res podr�a cambiar.
        }

        return res;
    }


} // Fin clase DialogoAbrir
