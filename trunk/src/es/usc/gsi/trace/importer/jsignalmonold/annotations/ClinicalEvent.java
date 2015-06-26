package es.usc.gsi.trace.importer.jsignalmonold.annotations;

import java.io.Serializable;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class ClinicalEvent implements Comparable, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 3215L;

    protected int tiempo_inicio;
    protected boolean modofecha;
    protected String texto;
    protected String comentario;
    public static final int MARCA = 0;
    public static final int ANOTACION = 1;
    public static final int DIAGNOSTICO = 2;
    public static final int MANIFESTACION = 3;
    public static final int TERAPIA = 4;
    public static final int EVENTO = 5;
    protected int tipo = 5;

//   protected GregorianCalendar fecha;
    protected int offset;
    protected float fs_sobre_la_cual_se_define_el_offset = 1.0F;

    /**
     * Este constructor construye un evento "Vacio", con solo tiempo de inicio.
     * Es empleado por PanelAnotaciones para buscar en el TreeSet de anotaciones
     * aquellas que le interesan
     */
    public ClinicalEvent(int i) {
        this.tiempo_inicio = i;
    }

    public ClinicalEvent() {

    }

    /**
     * @return int
     */
    public int getStartTime() {
        return tiempo_inicio;
    }

    /**
     * @return String
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @return String
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @return boolean
     */
    public boolean getModoFecha() {
        return false;
    }

    /**
     * @param texto
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @param comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @param tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo_inicio = tiempo;
    }

    /**
     * @param boolean@param b
     */
    public void setModoFecha(boolean b) {

    }

    /**
     * Debuelve el tipo de enento (Marca, Diagnostico, Terapia o manifestacion)
     * al cual pertene este evento
     */
    public int getTipo() {
        return this.tipo;
    }

    /**
     * Metodo que se invoca para evitar que halla dos eventos en el mismo instante
     */
    public void evitaIgualdad() {
        this.tiempo_inicio++;
    }

    public int compareTo(Object o) {
        ClinicalEvent evento = (ClinicalEvent) o;

        if (this.tiempo_inicio > evento.getStartTime()) {
            return 1;
        } else if (this.tiempo_inicio == evento.getStartTime()) {
            return 0;
        } else {
            return -1;
        }

    }

    public void setTipo(int _tipo) {
        tipo = _tipo;
    }

    /**
     * Devuelve el ofset con el cual sera dibujado este evento en pantalla. Es un
     * metodo cuya unica finalidad es gestionar el dibujo en pantalla.
     */

    public int getOffset() {
        return offset;
    }

    public void setOffset(int _offset) {
        offset = _offset;
    }

    public float getFsSobreLaCualSeDefineElOffset() {
        return fs_sobre_la_cual_se_define_el_offset;
    }

    public void setFsSobreLaCualSeDefineElOffset(float
                                                 _fs_sobre_la_cual_se_define_el_offset) {
        fs_sobre_la_cual_se_define_el_offset =
                _fs_sobre_la_cual_se_define_el_offset;
    }

}
