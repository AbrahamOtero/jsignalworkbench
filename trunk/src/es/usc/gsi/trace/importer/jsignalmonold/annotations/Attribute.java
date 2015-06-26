package es.usc.gsi.trace.importer.jsignalmonold.annotations;

import java.io.Serializable;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Attribute implements Serializable {

    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 3213L;

    public String atributo;
    public String valor;

    /**
     * Pretende ser el conjunto de valores que podra tomar el atributo. No se empleara
     * en la primera version.
     */
    public static String[] opciones_atributo;
    public static String[][] opciones_valor;

    public Attribute(String atributo, String valor) {
        this.atributo = atributo;
        this.valor = valor;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String _atributo) {
        atributo = _atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String _valor) {
        valor = _valor;
    }
}
