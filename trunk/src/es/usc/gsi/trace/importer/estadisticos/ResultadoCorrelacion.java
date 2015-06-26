package es.usc.gsi.trace.importer.estadisticos;

import java.io.Serializable;

public class ResultadoCorrelacion implements Serializable {
    private String senal1, senal2, fechaInicio1, fechaInicio2, fechaFin1,
    fechaFin2, comentario, nombre;
    private float nivelDeSignificacion;
    private int nivelDeSignificacionDiscreto;
    public String getSenal1() {
        return senal1;
    }

    public void setSenal1(String _senal1) {
        senal1 = _senal1;
    }

    public String getSenal2() {
        return senal2;
    }

    public void setSenal2(String _senal2) {
        senal2 = _senal2;
    }

    public String getFechaInicio1() {
        return fechaInicio1;
    }

    public void setFechaInicio1(String _fechaInicio1) {
        fechaInicio1 = _fechaInicio1;
    }

    public String getFechaInicio2() {
        return fechaInicio2;
    }

    public void setFechaInicio2(String _fechaInicio2) {
        fechaInicio2 = _fechaInicio2;
    }

    public String getFechaFin1() {
        return fechaFin1;
    }

    public void setFechaFin1(String _fechaFin1) {
        fechaFin1 = _fechaFin1;
    }

    public String getFechaFin2() {
        return fechaFin2;
    }

    public void setFechaFin2(String _fechaFin2) {
        fechaFin2 = _fechaFin2;
    }

    public float getNivelDeSignificacion() {
        return nivelDeSignificacion;
    }

    public void setNivelDeSignificacion(float _nivelDeSignificacion) {
        nivelDeSignificacion = _nivelDeSignificacion;
    }

    public int getNivelDeSignificacionDiscreto() {
        return nivelDeSignificacionDiscreto;
    }

    public void setNivelDeSignificacionDiscreto(int
                                                _nivelDeSignificacionDiscreto) {
        nivelDeSignificacionDiscreto = _nivelDeSignificacionDiscreto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String _comentario) {
        comentario = _comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String _comentario) {
        nombre = _comentario;
    }

    /**
     * crea el texto descriptivo de esta correlacion
     * @return
     */
    public String getTextoDescriptivo() {
        String texto = "La correlacion fue calculada sobre la senhal " +
                       this.getSenal1() +
                       " desde " + this.getFechaInicio1() + " hasta " +
                       this.getFechaFin1() + " y sobre la senhal " +
                       this.getSenal2() +
                       " desde " + this.getFechaInicio2() + " hasta " +
                       this.getFechaFin2() + ".";
        return texto;
    }

    /**
     * crea la key con la que se lmacena esta correlacion.
     * Ser indentica siempre que coincidan las senhales y los intervalos.
     * @return
     */
    public String getKey() {
        return getSenal1() + getSenal2() + getFechaInicio1() + getFechaFin1() +
                getFechaInicio2() + getFechaFin2();
    }
}
