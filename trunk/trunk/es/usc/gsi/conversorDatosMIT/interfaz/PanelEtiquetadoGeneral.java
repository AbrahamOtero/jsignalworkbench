package es.usc.gsi.conversorDatosMIT.interfaz;

import javax.swing.*;

public abstract class PanelEtiquetadoGeneral extends JTabbedPane {

    // Actualizará las frecuencias a las presentes en el interfaz gráfico.
    public abstract void actualizaFrecuencias();

    // Actualizará las fechas a las presentes en el interfaz gráfico.
    public abstract void actualizaFechas(String fechaInicio, String fechaFin);

    // Cerrar todos los ficheros
    public abstract void cerrarTodosFicheros();

} // Fin clase PanelEtiquetadoGeneral