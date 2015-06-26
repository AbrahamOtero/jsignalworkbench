package es.usc.gsi.trace.importer.estadisticos;

/**
 * @todo documentar
 */
import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class ResultadosEstadisticos implements Serializable {
    private float media_aritmetica, mediana, varianza, desviacion_tipica,
    error_estandar, cociente_de_variacion;
    private float[] intervalo_de_confianza = new float[2];
    private HashMap percentiles = new HashMap();
    private String comentario, fecha_inicio, fecha_fin, nombre_senhal;
    private boolean tiene_percentiles = true;

    /**
     * Construye un Resultado estadistico, se le paso tod menos el comentario.
     * @param media_aritmetica
     * @param mediana
     * @param varianza
     * @param desviacion_tipica
     * @param error_estandar
     * @param cociente_de_variacion
     * @param intervalo_de_confianza
     * @param percentiles
     * @param valores_percentiles
     * @param fecha_inicio
     * @param fecha_fin
     * @param nombre_senhal
     */
    public ResultadosEstadisticos(float media_aritmetica, float mediana,
                                  float varianza,
                                  float desviacion_tipica, float error_estandar,
                                  float cociente_de_variacion,
                                  float[] intervalo_de_confianza,
                                  int[] percentiles,
                                  float[] valores_percentiles,
                                  String fecha_inicio, String fecha_fin,
                                  String nombre_senhal) throws
            NotPercentilException {
        this.media_aritmetica = media_aritmetica;
        this.mediana = mediana;
        this.varianza = varianza;
        this.desviacion_tipica = desviacion_tipica;
        this.error_estandar = error_estandar;
        this.cociente_de_variacion = cociente_de_variacion;
        this.intervalo_de_confianza = intervalo_de_confianza;
        this.fecha_fin = fecha_fin;
        this.fecha_inicio = fecha_inicio;
        this.nombre_senhal = nombre_senhal;
        if (percentiles != null) {
            this.tiene_percentiles = true;
            if (percentiles.length != valores_percentiles.length) {
                throw new NotPercentilException(
                        "Se paso un numero distinto de percentiles y de valores de percentil: " +
                        percentiles.length + " != " +
                        valores_percentiles.length, 0);
            }
            this.percentiles = new HashMap();
            for (int i = 0; i < percentiles.length; i++) {
                this.percentiles.put(Integer.toString(percentiles[i]),
                                     Float.toString(valores_percentiles[i]));
            }
        } else {
            this.tiene_percentiles = false;
        }
    }


    public float getMediaAritmetica() {
        return media_aritmetica;
    }

    public float getMediana() {
        return mediana;
    }

    public float getVarianza() {
        return varianza;
    }

    public float getDesviacionTipica() {
        return desviacion_tipica;
    }

    public float getErrorEstandar() {
        return error_estandar;
    }

    public float getCocienteDeVariacion() {
        return cociente_de_variacion;
    }

    public float[] getIntervaloDeConfianza() {
        return intervalo_de_confianza;
    }

    public HashMap getPercentiles() {
        return percentiles;
    }

    protected void setMediaAritmetica(float _media_aritmetica) {
        media_aritmetica = _media_aritmetica;
    }

    protected void setMediana(float _mediana) {
        mediana = _mediana;
    }

    protected void setVarianza(float _varianza) {
        varianza = _varianza;
    }

    protected void setDesviacion_tipica(float _desviacion_tipica) {
        desviacion_tipica = _desviacion_tipica;
    }

    protected void setErrorEstandar(float _error_estandar) {
        error_estandar = _error_estandar;
    }

    protected void setCocienteDeVariacion(float _cociente_de_variacion) {
        cociente_de_variacion = _cociente_de_variacion;
    }

    protected void setIntervaloDeConfianza(float[] _intervalo_de_confianza) {
        intervalo_de_confianza = _intervalo_de_confianza;
    }

    protected void setPercentiles(HashMap _percentiles) {
        percentiles = _percentiles;
    }

    protected void setPercentil(int percentil, float valor) throws
            NotPercentilException {
        if (percentil < 0 || percentil > 100) {
            throw new NotPercentilException("No es un percentil admisible ",
                                            percentil);
        }

        String percentil_key = null;
        percentil_key = Integer.toString(percentil);
        percentiles.put(percentil_key, Float.toString(valor));
    }

    public String getComentario() {
        if (comentario == null) {
            return "";
        }

        return comentario;
    }

    public String getFechaInicio() {
        return fecha_inicio;
    }

    public String getFechaFin() {
        return fecha_fin;
    }

    public String getNombreSenhal() {
        return nombre_senhal;
    }

    protected void setFechaInicio(String _fecha_inicio) {
        fecha_inicio = _fecha_inicio;
    }

    protected void setFechaFin(String _fecha_fin) {
        fecha_fin = _fecha_fin;
    }

    /**
     * Permite modificar el nombnre de este estdistico.
     * @param _nombre_senhal
     */
    public void setNombreSenhal(String _nombre_senhal) {
        nombre_senhal = _nombre_senhal;
    }

    /**
     * Permite modificar el comentario de este Estadistico.
     * @param _comentario
     */
    public void setComentario(String _comentario) {
        comentario = _comentario;
    }

    /**
     * Devuelve true si se han calculado los percentiles.
     * @return
     */
    public boolean getTienePercentiles() {
        return tiene_percentiles;
    }

    /**
     * Elimina los percentiles de este resultado estadistico.
     */
    public void invalidaPercentiles() {
        this.percentiles = null;
        this.tiene_percentiles = false;
    }


}
