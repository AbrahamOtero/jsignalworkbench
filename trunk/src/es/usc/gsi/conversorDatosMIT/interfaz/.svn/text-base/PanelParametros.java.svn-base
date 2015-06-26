package es.usc.gsi.conversorDatosMIT.interfaz;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.usc.gsi.conversorDatosMIT.ficheros.FicheroHead;

public class PanelParametros extends JPanel {


    private PanelFichero[] panelesFichero;
    private BoxLayout layoutPanel;

    public PanelParametros(FicheroHead[] ficherosHead) {
        this.layoutPanel = new BoxLayout(this, BoxLayout.Y_AXIS); // Una sola columna y tantas filas como ficherosHead
        this.setLayout(layoutPanel);

        PanelInfo informacion = new PanelInfo(ficherosHead[0],
                                              PanelInfo.DIRECTORIO);
        this.add(informacion);
        this.creaPanelesFichero(ficherosHead);
    }

    private void creaPanelesFichero(FicheroHead[] ficherosHead) {
        panelesFichero = new PanelFichero[ficherosHead.length];

        for (int i = 0; i < ficherosHead.length; i++) {
            panelesFichero[i] = new PanelFichero(ficherosHead[i],
                                                 PanelFichero.LISTA);
            this.add(panelesFichero[i]);
        }
    }

    public void actualizaFrecuencias() {

        for (int i = 0; i < panelesFichero.length; i++) {
            panelesFichero[i].actualizaFrecuencia();
        }

    }

    public void actualizaFechas(String fechaInicio, String fechaFin) {

        for (int i = 0; i < panelesFichero.length; i++) {
            panelesFichero[i].actualizaFechas(fechaInicio, fechaFin);
        }

    }


} // Fin clase PanelParametros