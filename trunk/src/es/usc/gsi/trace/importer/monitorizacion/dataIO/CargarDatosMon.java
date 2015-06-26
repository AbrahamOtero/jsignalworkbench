//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\DataIO\\CargarDatosMon.java

package es.usc.gsi.trace.importer.monitorizacion.dataIO;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.monitorizacion.data.AlmacenDatos;

/**
 * Carga los datos referentes a una monitorizacion ya realizada. Peuede haber en
 * ellos anotaciones y maracas.
 */
public class CargarDatosMon extends CargarDatos {

    public CargarDatosMon(String archivo) {
        super(archivo);
        cargaDatos();

    }

    private void cargaDatos() {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    archivo));
            almacen = (AlmacenDatos) in.readObject();
            almacen.inicializaPartesNoSerializadas();
            float[][] tmp = (float[][]) almacen.getDatos();
            GestorIO.getGestorIO().setNumSenales(tmp.length);
            GestorIO.getGestorIO().setNumDatos(tmp[0].length);
            String fecha_base = almacen.getFechaBase();
            if (fecha_base != null) {
                SamplesToDate.getInstancia().setFechaBase(fecha_base);
            }

        } catch (Exception e) {
            e.printStackTrace();
            almacen = null;
        }

    }

}
