//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\ReferenciaDatos.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.LinkedList;

/**
 * Contien las referencias a los arrays que conriene los datos que se estan
 * monitorizando.
 */
public class ReferenciaDatos extends Referencia {
    static final long serialVersionUID = 326145L;
    boolean pos_asociada[];
    private ReferenciaPosibilidades almacen_posibilidades;

    /**
     * @param almacen
     */
    ReferenciaDatos(AlmacenDatos almacen) {
        super(almacen);
        almacen_posibilidades = new ReferenciaPosibilidades();
    }

    /**
     * @return LinkedList
     */
    LinkedList getReferenciaPos() {
        return almacen_posibilidades.getReferencias();
    }

    /**
     * @param pos
     * @return Object
     */
    Object getReferenciaPos(int pos) {
        return almacen_posibilidades.getReferencias(pos);
    }
}
