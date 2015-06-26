package es.usc.gsi.conversorDatosMIT.interfaz;


import es.usc.gsi.conversorDatosMIT.ficheros.FicheroHead;

public class PanelEtiquetado extends PanelEtiquetadoGeneral {

    private ScrollPanelFichero[] scrollesPaneles;
    //  private JScrollPane[] scrollesPaneles;

    public PanelEtiquetado(FicheroHead[] ficherosHead) {
        this.creaScrollesPaneles(ficherosHead);
    }

    // Crea un PanelFichero por cada fichero, y un
    // ScrollPanelFichero por cada PanelFichero
    private void creaScrollesPaneles(FicheroHead[] ficherosHead) {
        PanelFichero[] panelesFichero = new PanelFichero[ficherosHead.length];
        scrollesPaneles = new ScrollPanelFichero[ficherosHead.length];

        for (int i = 0; i < ficherosHead.length; i++) {
            panelesFichero[i] = new PanelFichero(ficherosHead[i],
                                                 PanelFichero.ETIQUETAS);
            scrollesPaneles[i] = new ScrollPanelFichero(panelesFichero[i]);
            this.add(ficherosHead[i].getNombreFrame(), scrollesPaneles[i]);
        }
    }

    ///////////////////////////
    public FicheroHead getFicheroHeadSeleccionado() {
        ScrollPanelFichero spf =
                (ScrollPanelFichero)this.getSelectedComponent();
        PanelFichero pf = spf.getPanelFichero();

        return pf.getFicheroHead();
    }

/////////////////
    public void actualizaFrecuencias() {
        for (int i = 0; i < scrollesPaneles.length; i++) {
            scrollesPaneles[i].actualizaFrecuencia();
        }
    }

/////////////////

    public void actualizaFechas(String fechaInicio, String fechaFin) {
        for (int i = 0; i < scrollesPaneles.length; i++) {
            scrollesPaneles[i].actualizaFechas(fechaInicio, fechaFin);
        }
    }

////////////////

    public void cerrarTodosFicheros() {
        this.removeAll();
        this.validate();
    }

} // Fin clase PanelEtiquetado