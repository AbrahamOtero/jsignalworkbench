package es.usc.gsi.conversorDatosMIT.interfaz.filtros;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroHead extends FileFilter {

    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }
        if (f.getName().indexOf(".hea") == -1 &&
            f.getName().indexOf(".HEA") == -1
                ) {
            return false;
        }

        else {
            return true;
        }

    }

    public String getDescription() {
        return "Ficheros .hea";
    }

} // Fin clase FiltroHead