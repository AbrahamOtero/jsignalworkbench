//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\DataIO\\CargarDatos.java

package es.usc.gsi.trace.importer.monitorizacion.dataIO;

import java.util.TreeSet;

import es.usc.gsi.trace.importer.monitorizacion.data.AlmacenDatos;

/**
 * Esta clase se encargara de cargar los datos de un archivo, que se le pasara en
 * su contructor. En funcion dle tipo de archivo (.txt o .mon, segun sean un
 * archivo de texto cuyos datos estan organizadas en columnas, o una
 * monitorizacion ya realizada) creara uno u otro objeto para leer el archivo y
 * los devolvera a Fuente de datos como un objeto CargarDatos.
 */
abstract class CargarDatos {
    int numero_senales;
    float[][] datos;
    byte pos[][];
    String archivo;
    TreeSet[] marcas;
    java.util.TreeSet anotaciones;
    AlmacenDatos almacen;

    CargarDatos() {

    }

    /**
     * @param fichero
     */
    CargarDatos(String fichero) {
        this.datos = null;
        this.marcas = null;
        this.anotaciones = null;
        this.pos = null;
        this.archivo = fichero;
    }

    /**
     * @param canal
     * @return java.util.LinkedList
     * @todo Que devuelva null en la superclase
     * Devuelve las marcas de una determinada senal.
     */
    java.util.TreeSet[] getMarcas() {
        return marcas;
    }

    /**
     * @param canal
     * @return java.util.LinkedList
     * @todo Que devuelva null en la superclase
     * Devuelve las anotaciones de una determinada senhal.
     */
    java.util.TreeSet getAnotaciones() {
        return anotaciones;
    }

    /**
     * @return float[][]
     */
    float[][] getDatos() {
        return datos;
    }

    /**
     * @return int[][]
     * @todo Que devuelva null en la superclase
     */
    byte[][] getPos() {
        return pos;
    }

    /**Este metodo debe ser sobre escrito por todos los descendientes
     * @param fichero
     */
    private void cargaDatos() {
        this.datos = null;
        this.marcas = null;
        this.anotaciones = null;
        this.pos = null;
    }

    int getNumeroSenales() {
        return numero_senales;
    }

    void setNumeroSenales(int _numero_senales) {
        numero_senales = _numero_senales;
    }

    AlmacenDatos getAlmacen() {
        return almacen;
    }

    public void setAlmacen(TreeSet tmp) {
        anotaciones = tmp;
    }
}
