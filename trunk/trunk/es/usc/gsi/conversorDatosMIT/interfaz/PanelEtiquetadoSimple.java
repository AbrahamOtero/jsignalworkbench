package es.usc.gsi.conversorDatosMIT.interfaz;

import es.usc.gsi.conversorDatosMIT.ficheros.FicheroHead;


public class PanelEtiquetadoSimple extends PanelEtiquetadoGeneral {

    private ScrollPanelParametros scrollPanel;


    public PanelEtiquetadoSimple(FicheroHead[] ficherosHead) {
        this.creaScrollPanel(ficherosHead);
    }

    private void creaScrollPanel(FicheroHead[] ficherosHead) {
        scrollPanel = new ScrollPanelParametros(ficherosHead);
        this.add(ficherosHead[0].getParentFile().getName(), scrollPanel);
    }

    public void actualizaFrecuencias() {
        scrollPanel.actualizaFrecuencias();
    }

/////////////////

    public void actualizaFechas(String fechaInicio, String fechaFin) {
        scrollPanel.actualizaFechas(fechaInicio, fechaFin);
    }

////////////////

    public void cerrarTodosFicheros() {
        this.removeAll();
        this.validate();
    }


} // Fin clase PanelEtiquetadoSimple