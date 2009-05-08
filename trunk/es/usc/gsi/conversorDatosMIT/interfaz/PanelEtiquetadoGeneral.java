package es.usc.gsi.conversorDatosMIT.interfaz;

import javax.swing.*;

public abstract class PanelEtiquetadoGeneral extends JTabbedPane {

    // Actualizar� las frecuencias a las presentes en el interfaz gr�fico.
    public abstract void actualizaFrecuencias();

    // Actualizar� las fechas a las presentes en el interfaz gr�fico.
    public abstract void actualizaFechas(String fechaInicio, String fechaFin);

    // Cerrar todos los ficheros
    public abstract void cerrarTodosFicheros();

} // Fin clase PanelEtiquetadoGeneral