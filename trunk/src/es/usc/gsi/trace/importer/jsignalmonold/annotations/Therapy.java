package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 ** @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Therapy extends Annotation {

    private String nombreFarmaco;
    private String fase_terapeutica;
    private String dosificacion;
    private String tipo;

    public Therapy(String nombre_farmaco, String fase_terapeutico,
                   String dosificacion, String tipo) {
        this.nombreFarmaco = nombre_farmaco;
        this.texto = nombre_farmaco;
        this.fase_terapeutica = fase_terapeutico;
        this.dosificacion = dosificacion;
        this.tipo = tipo;

    }

    /**
     * @return String
     */
    public String getFaseTerapeutica() {
        return this.fase_terapeutica;
    }

    /**
     * @return String
     */
    public String getDosificacion() {
        return this.dosificacion;
    }

    /**
     * @return String
     */
    public String getTipoTerapia() {
        return this.tipo;
    }

    /**
     * @param fase
     */
    public void setFaseTerapeutica(String fase) {
        this.fase_terapeutica = fase;
    }

    /**
     * @param dosis
     */
    public void setDosificacion(String dosis) {
        this.dosificacion = dosis;
    }

    /**
     * @param tipo
     */
    public void setTipoTerapia(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreFarmaco() {
        return nombreFarmaco;
    }

    public void setNombreFarmaco(String nombreFarmaco) {
        this.nombreFarmaco = nombreFarmaco;
    }
}
