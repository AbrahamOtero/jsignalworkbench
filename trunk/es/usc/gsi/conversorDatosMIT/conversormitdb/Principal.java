package es.usc.gsi.conversorDatosMIT.conversormitdb;


import es.usc.gsi.conversorDatosMIT.interfaz.*;

public class Principal {

    // Constructor
    public Principal() {

        // Carga de controladores: al cargar la clase también se crea un objeto
        // de cada uno de los controladores: siguen el patrón Singleton


        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

        ventanaPrincipal.setVisible(true);

    }

    public static void main(String[] args) {
        Principal p = new Principal();
    } // Fin main

} // Fin clase Principal