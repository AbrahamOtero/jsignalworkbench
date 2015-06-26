package es.usc.gsi.conversorDatosMIT.interfaz.filtros;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroDirectorio extends FileFilter {

    // Debe devolver true si existen ficheros .hea en un determinado directorio:
    // esto es necesario para que podamos analizar los contenidos del directorio
    // mediante el metodo isTraversable(File f) de la clase DialogoAbrir, que extiende a
    // JDialog
    public boolean accept(File f) {

        if (f.isDirectory() || (f.getName().indexOf(".hea") != -1)) {
            return true;
        } else {
            return false;
        }

    }

    public String getDescription() {
        return "Pacientes";
    }

} // Fin clase FiltroHead
