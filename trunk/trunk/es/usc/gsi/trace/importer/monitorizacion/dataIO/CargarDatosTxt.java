//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\DataIO\\CargarDatosTxt.java

package es.usc.gsi.trace.importer.monitorizacion.dataIO;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Carga los todos de un archivo .txt, en el cual los datos se almacenan como
 * Strings organizados en columnas y seprados por tabulaciones.
 */
public class CargarDatosTxt extends CargarDatos {

    public CargarDatosTxt(String archivo) {
        super(archivo);
        cargaDatos();

    }

    /**
     * @todo: Javier Salas cambio el formato de almacenamiento de salida de la herramienta de Vila.
     * Cambi t por " ". Hice cambios con ese fin. Validarlo, no fijo que funcion siempre.
     */
    private void cargaDatos() {
        try {
            FileReader f = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(f);
            File fich = new File(archivo);
            //Marcasmos el Buffer para poder resetearlo una vez averiguado el numero
            //de filas y columnas que hay en el
            bf.mark((int) fich.length() + 1);
            String line = bf.readLine();
            StringTokenizer tk = new StringTokenizer(line);
            int columnas = 0;
            //Contamos el numero de columnas que hay en el archivo
            while (tk.hasMoreTokens()) {
                columnas++;
                tk.nextToken();
            }
            //Contamos el numero de filas. Aunque ya llevamos 1 leida el contador
            //empieza en 0 ya que el bucle incrementara su valor la primera vez que lea null
            int filas = 0;
            while (line != null) {
                line = bf.readLine();
                filas++;
            }
            //Inicilizamos todas la variables
            datos = new float[columnas][filas];
            pos = new byte[columnas][filas];
            marcas = new TreeSet[columnas];
            anotaciones = new TreeSet();
            for (int i = 0; i < columnas; i++) {
                marcas[i] = new TreeSet();
            }
            //reseteamos el buffer
            bf.reset();
            //Contadores de linea y columna
            int lin = 0, col = 0;
            do {
                line = bf.readLine();
                if (line != null) {
                    StringTokenizer tk2 = new StringTokenizer(line, "\t ", true);
                    lin = 0;
                    while (tk2.hasMoreTokens()) {
                        String dato_fichero = tk2.nextToken();
                        if (dato_fichero.equals("\t")) {
                            datos[lin][col] = 0;
                            lin++;
                            /*  //Si la primera columna es la que falta el dtao = \t 21 \t 23 \t...
                              //Por lo que en ese caso no hay que consumir ningun\t adicional
                              if (tk2.hasMoreElements() && lin != 1) {
                               //consuminos el \t adicional
                               if (!(tk2.nextElement().equals("\t"))) {
                             System.out.println("Se esta leyneod mal el fichero de entrada");
                               }
                              }*/

                        } else {
                            datos[lin][col] = Float.parseFloat(dato_fichero);
                            //Si no estamos en la ultima columna
                            if (tk2.hasMoreElements()) {
                                //consuminos el \t adicional
                                String ultimo_token = tk2.nextToken();
                                if (!(ultimo_token.equals("\t") ||
                                      ultimo_token.equals(" "))) {
                                }
                            }
                            lin++;
                        }
                    }
                    col++;
                }
            } while (line != null);
            GestorIO gestor_io = GestorIO.getGestorIO();
            gestor_io.setNumDatos(filas);
            gestor_io.setNumSenales(columnas);

        } catch (IOException ex) {
            ex.printStackTrace();
            datos = null;
            pos = null;
            marcas = null;
            anotaciones = null;
        }

    }
}
