package es.usc.gsi.conversorDatosMIT.interfaz.filtros;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroExportar extends FileFilter {

    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }
        if (f.getName().indexOf(".txt") == -1) {
            return false;
        } else {
            return true;
        }

    }

    public String getDescription() {
        return "Ficheros .txt";
    }

} // Fin clase FiltroExportar
