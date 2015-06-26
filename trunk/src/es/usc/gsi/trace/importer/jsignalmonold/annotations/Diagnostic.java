package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Diagnostic extends Annotation {

    private Attribute atributo;
    public Attribute theAtributo;

    public Diagnostic(String nombre, Attribute atributo) {
        this.texto = nombre;
        this.atributo = atributo;
    }

    /**
     * @return pintar.MarcasAnotaciones.Atributo
     */
    public Attribute getAtributo() {
        return this.atributo;
    }

    /**
     * @param atributo
     */
    public void setAtributo(Attribute atributo) {
        this.atributo = atributo;
    }
}
