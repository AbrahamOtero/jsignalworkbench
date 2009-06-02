//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\AlmacenDatosByte.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.TreeSet;

public class AlmacenDatosByte extends AlmacenDatos {
    private byte datos[][];
    public AlmacenDatos theAlmacenDatos;
    static final long serialVersionUID = 3212L;
    public AlmacenDatosByte() {

    }

    /**
     * @param datos
     * @param pos
     * @param marcas
     * @param anotaciones
     */
    public AlmacenDatosByte(byte[][] datos, byte[][] pos,
                            java.util.TreeSet anotaciones, TreeSet[] marcas) {
        this.datos = datos;
        this.anotaciones = anotaciones;
        this.marcas = marcas;
    }

    /**
     * @param senal
     * @return Object
     */
    public Object getArray(int senal) {
        return datos[senal];
    }

    /**
     * @param senal
     * @return Object
     */
    public Object getDatos(int senal) {
        return datos[senal];
    }

    /**
     *
     * @param numSenal
     * @param nuevoDatos
     */
    public void setDatos(int numSenal, Object nuevoDatos) {
        datos[numSenal] = (byte[]) nuevoDatos;
    }

    /**
     * @return Object
     */

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object nuevosDatos) {
        datos = (byte[][]) nuevosDatos;
    }

    /**
     * Emplear solo cuado sea un almacen de posibilidad.
     * @param senal
     * @param pos
     */
    public void setPos(int senal, byte[] pos) {
        this.datos[senal] = pos;
    }

    /**
     * Anhade la senhal indicada al almacend e datos byte.
     * @param nueva_senal
     */
    public void anhadeSenhal(byte[] nueva_senal) {
        byte[][] datos_tmp = new byte[datos.length + 1][];
        for (int i = 0; i < datos.length; i++) {
            datos_tmp[i] = datos[i];
        }
        datos_tmp[datos.length] = nueva_senal;
        datos = datos_tmp;
    }

    /**
     * LLamar cuando se cree una nueva senhal sin inicializarla a nada. Toma el mismo
     *  tamanho que la senhal situada en la posicion 0 del arayy datos[].
     */
    public void anhadeSenhal() {
        byte[][] datos_tmp = new byte[datos.length + 1][];
        for (int i = 0; i < datos.length; i++) {
            datos_tmp[i] = datos[i];
        }
        datos_tmp[datos.length] = new byte[datos[0].length];
        datos = datos_tmp;
    }

    /**
     * Devuelve la longitud maxima de la senhal mas grande.
     * @return
     */
    public int getMaximoNumeroDeDatos() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < datos.length; i++) {
            if (datos[i].length > max) {
                max = datos[i].length;
            }

        }
        return max;

    }
}
