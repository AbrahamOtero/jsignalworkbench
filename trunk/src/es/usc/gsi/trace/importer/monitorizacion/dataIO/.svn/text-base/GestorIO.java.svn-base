//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\dataIO\\GestorIO.java

package es.usc.gsi.trace.importer.monitorizacion.dataIO;

import java.util.TreeSet;

import javax.swing.JOptionPane;

import es.usc.gsi.trace.importer.monitorizacion.data.*;
import org.jdom.JDOMException;

public class GestorIO {

    /**
     * instance flag attribute
     */
    private static boolean instance_flag_for_GestorIO;
    private static GestorIO instancia;
    private static String fichero;
    private CargarDatos cargador;
    private static int num_datos;
    private static int num_senales;

    /**
     * private default constructor
     * @throws SingletonException
     */
    private GestorIO() throws Exception {
        if (instance_flag_for_GestorIO) {

            throw new Exception("Only one instance allowed");
        }

        else {

            instance_flag_for_GestorIO = true;
        }
    }

    /**
     *
     * @param archivo
     * @param macacar_datos_antiguos si es true machaca los datos antiguos, si no los anhade.
     * @return
     */
    public boolean cargarDatos(String archivo, boolean machacar_datos_antiguos) {
        AlmacenDatos almacen = null;
        if (archivo.endsWith(".txt") || archivo.endsWith(".picos")) {
            this.cargador = new CargarDatosTxt(archivo);
            float datos[][] = cargador.getDatos();
            byte[][] pos = cargador.getPos();
            TreeSet[] marcas = cargador.getMarcas();
            TreeSet anotaciones = cargador.getAnotaciones();

            num_datos = datos[0].length;
            num_senales = datos.length;
            //+1 para la posibilidad global
            float rango[][] = new float[num_senales + 1][2];
            for (int i = 0; i < num_senales + 1; i++) {
                rango[i][0] = 0;
                rango[i][1] = 100;
            }

            if (datos != null) {
                almacen = new AlmacenDatosFloat(datos, pos, anotaciones, marcas);
                almacen.setRango(rango);
            }
        } else {
            //Por defecto intentamos cargar XML
            try {
                this.cargador = new CargarDatosXML(archivo);
            }
            //Pero si no va nos volvemos al serialismo
            catch (JDOMException ex) {
                System.out.println("Exception " + ex.getMessage());
                this.cargador = new CargarDatosMon(archivo);
            }
            almacen = cargador.getAlmacen();
        }

        if (almacen != null) {
            //Si hay que machacar => machacamos
            if (machacar_datos_antiguos) {
                GestorDatos.getInstancia().setAlmacen(almacen);
                return true;
            }
            //Si no anhado los datos al archivo actual
            else {
                float[][] datos = (float[][]) almacen.getDatos();
                String[] nombres_Vila = {"VLF", "LF", "HF", "HF/LF", "FC"};
                GestorDatos gestor_fdatos = GestorDatos.getInstancia();
                //Si carhgo el archivo de Vila
                if (archivo.endsWith(".picos")) {
                    for (int i = 0; i < datos.length && i < nombres_Vila.length;
                                 i++) {
                        gestor_fdatos.anhadeSenhal(datos[i], nombres_Vila[i], "",
                                "",
                                almacen.getFs(i),
                                almacen.getRango(i));
                    }

                }
                //Si no se cargan sin nombre
                else {
                    for (int i = 0; i < datos.length; i++) {
                        gestor_fdatos.anhadeSenhal(datos[i],
                                almacen.getNombreSenal(i),
                                almacen.getLeyenda(i),
                                "",
                                almacen.getFs(i),
                                almacen.getRango(i));
                    }
                }

                return true;
            }

        }
        //Si datos = null hubo un error al leer
        //@todo: quizas debiera resposabilizarse de esto otro
        else {
            JOptionPane.showOptionDialog(null, "Error en el formato", "ERROR",
                                         JOptionPane.DEFAULT_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return false;
        }
    }

    /**
     * @param fichero
     * @return GestorIO
     */
    public synchronized static GestorIO getGestorIO() {
        if (instance_flag_for_GestorIO) {
            return instancia;
        } else {
            try {
                GestorIO.instancia = new GestorIO();
                return instancia;
            } catch (Exception ex) {
                System.out.println("Exception " + ex.getMessage());
                ex.printStackTrace();
                return null;
            }

        }
    }

    public int getNumDatos() {
        return num_datos;
    }

    public void setNumDatos(int _num_datos) {
        num_datos = _num_datos;
    }

    public int getNumSenales() {
        return num_senales;
    }

    public void setNumSenales(int _num_senales) {
        num_senales = _num_senales;
    }


}
