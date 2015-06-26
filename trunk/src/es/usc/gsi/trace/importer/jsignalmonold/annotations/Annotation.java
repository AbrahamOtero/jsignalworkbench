package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Annotation extends ClinicalEvent {

    private int tiempoFin;

    /**
     * @return int
     */
    public int getTiempoFin() {
        return tiempoFin;
    }

    /**
     * @param tiempo
     */
    public void setTiempoFin(int tiempo) {
        this.tiempoFin = tiempo;
    }
}
