package es.usc.gsi.conversorDatosMIT.interfaz;

import java.util.*;

import javax.swing.*;

public class BarraMenuPrincipal extends JMenuBar {

    private MenuArchivo archivo = new MenuArchivo("Archivo");
    private MenuVer ver = new MenuVer("Ver");

    public BarraMenuPrincipal() {
        this.add(archivo);
        this.add(ver);
    }
} // Fin clase BarraMenuPrincipal
