//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\Referencia.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.LinkedList;


abstract class Referencia

{
    static final long serialVersionUID = 382145L;
    /**
     * Array de punteros que apunta a un vector de datos contenido en el almacen,
     * estos datos se estan monitorizando.
     */
    protected LinkedList referencia_datos;
    protected AlmacenDatos almacen;

    /**
     * @param almacen
     */
    Referencia(AlmacenDatos almacen) {
        referencia_datos = new LinkedList();
        //Generamos 20 posiciones en la lista.
        //@todo: esot es un pocoo trapalleiro.
        for (int i = 0; i < 20; i++) {
            referencia_datos.add(null);
        }

        this.almacen = almacen;
    }

    Referencia() {

    }

    /**
     * @return LinkedList
     */
    LinkedList getReferencias() {
        return referencia_datos;
    }

    /**
     * @param posicion -
     * Posicion en la que se ha de anhadir la referencia. Las que estuviesen en
     * posiciones superiores o iguales a esta se desplazan hacia arriba.@param canal
     */
    void anadeReferencia(int canal, int posicion) {
        referencia_datos.add(posicion, almacen.getDatos(canal));
        // System.out.println("Anade a " + (posicion-1));
    }

    /**
     * @param posicion
     */
    void eliminaReferencia(int posicion) {
        referencia_datos.remove(posicion);
    }

    /**
     * @param referencia
     * @return Object
     */
    Object getReferencias(int referencia) {
        return referencia_datos.get(referencia);
    }

    /**
     * @return monitorizacion.data.AlmacenDatos
     */
    protected AlmacenDatos getAlmacen() {
        return almacen;
    }

    /**
     * @param _almacen
     */
    protected void setAlmacen(AlmacenDatos _almacen) {
        almacen = _almacen;
    }
}
