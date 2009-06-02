package es.usc.gsi.conversorDatosMIT.ficheros.escritura;

import java.io.*;
import java.util.Vector;

import es.usc.gsi.conversorDatosMIT.ficheros.FicheroHead;
import es.usc.gsi.conversorDatosMIT.ficheros.Parametro;
import es.usc.gsi.conversorDatosMIT.utilidades.ParseadorCadena;

public class EscribeCabeceraHead_ASCII {

    private Vector vectorFicherosHead;
    private File ficheroDestinoCabecera;

    public EscribeCabeceraHead_ASCII(Vector vectorFicherosHead,
                                     File ficheroDestino) {

        // Preprocesado del nombre de fichero para generar el nombre del .cxp
        if (ficheroDestino.getName().indexOf(".txt") != -1) { // Si ya tiene extension .txt
            // SOLUCION PROVISIONAL: FALLARA SI EL FICHERO TIENE .ALGO Y .TXT AL FINAL.
            String[] nombreSinExtension = ParseadorCadena.split(ficheroDestino.
                    getName(), ".");
            String pathSinExtension = ficheroDestino.getParent() +
                                      File.separator + nombreSinExtension[0];
            this.ficheroDestinoCabecera = new File(pathSinExtension + ".cxp");
        } else { // Si no tiene extension .txt
            this.ficheroDestinoCabecera = new File(ficheroDestino.
                    getAbsolutePath() + ".cxp");
        }

        // Vector de ficheros de cabecera
        this.vectorFicherosHead = vectorFicherosHead;

        this.vuelcaDatos();

    }

    public void vuelcaDatos() {

        FileWriter fw = null;
        BufferedWriter salida = null;
        int numColumna = 0;

        try {
            fw = new FileWriter(ficheroDestinoCabecera);
            salida = new BufferedWriter(fw);
        } catch (Exception e) {
            System.out.println(
                    "Error al crear fichero de cabecera en exportacion");
        }

        try {
            // Lectura de los ficheros.
            for (int i = 0; i < vectorFicherosHead.size(); i++) {
                FicheroHead fh = (FicheroHead) vectorFicherosHead.elementAt(i);
                Parametro[] parG = fh.getParametros();

                for (int j = 0; j < parG.length; j++) {
                    if (parG[j].getActivado()) {
                        numColumna++;
                        salida.write("[Columna" + numColumna + "]\n");
                        salida.write("nombreMarco=" + fh.getNombreFrame() +
                                     "\n");
                        //    salida.write("frecuenciaMuestreoMarco=" + fh.getFrecuenciaMuestreoFrame() + "\n");
                        salida.write("fechaInicio=" + fh.getFechaInicio() +
                                     "\n");
                        // salida.write("horaInicio=" + fh.getHoraInicio() + "\n");
                        salida.write("ficheroDatos=" + fh.getAbsolutePath() +
                                     "\n");
                        salida.write("nombreVariable=" +
                                     parG[j].getNombreParametro() + "\n");
                        salida.write(
                                "frecuenciaMuestreoOriginal=" +
                                Float.toString(parG[j].
                                               getBackupFrecuenciaMuestreo()) +
                                "\n"
                                );
                        salida.write(
                                "frecuenciaMuestreoVolcado=" +
                                Float.toString(parG[j].getFrecuenciaMuestreo()) +
                                "\n"
                                );
                        salida.write("\n");
                        salida.flush();

                    } // Fin if

                } // Fin bucle j

            } // Fin bucle i

        } catch (Exception e) {
            System.out.println(
                    "Error al volcar datos de fichero de cabecera exportacion");
        }

        try {
            salida.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar fichero de cabecera");
        }

    }

} // Fin EscribeCabeceraHead
