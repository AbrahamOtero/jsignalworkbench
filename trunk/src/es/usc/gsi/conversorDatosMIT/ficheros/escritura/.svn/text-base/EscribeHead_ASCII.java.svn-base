/* Esta clase esta bastante mal planteada: REFORMAR.
 Tiene una estructura "rara": todo se hace desde el constructor
 */
package es.usc.gsi.conversorDatosMIT.ficheros.escritura;

import java.io.*;
import java.util.Vector;

import javax.swing.JOptionPane;

import es.usc.gsi.conversorDatosMIT.ficheros.*;
import es.usc.gsi.conversorDatosMIT.ficheros.lectura.LeeFicheroDat;
import es.usc.gsi.conversorDatosMIT.interfaz.ControladorInterfaz;

public class EscribeHead_ASCII extends Thread implements Cancelar {

    private Vector vectorFicherosHead;
    private File ficheroDestino;
    private LeeFicheroDat[] arrayLectores;
    private ControladorFicheros controlFicheros = ControladorFicheros.
                                                  getControlador();
    private ControladorInterfaz controlInterfaz = ControladorInterfaz.
                                                  getControlador();
    private boolean cancel = false;

//*******************************************************************************

     // FALTA: CREAR UN CONSTRUCTOR QUE ADMITA UNA FRECUENCIA DE REMUESTREO GLOBAL.
     public EscribeHead_ASCII(Vector vectorFicherosHead, File ficheroDestino) {
         this.vectorFicherosHead = vectorFicherosHead;

         // Correccion del nombre de fichero
         if (ficheroDestino.getName().indexOf(".txt") != -1) {
             this.ficheroDestino = ficheroDestino;
         } else {
             this.ficheroDestino = new File(ficheroDestino.getAbsolutePath() +
                                            ".txt");
         }
     }

//*******************************************************************************

     public void cancelar() {
         cancel = true;
     }

//*******************************************************************************

     public void run() {
         new EscribeCabeceraHead_ASCII(vectorFicherosHead, ficheroDestino);
         this.creaLectores();
         if (this.vuelcaDatos()) {
             return;
         }
         this.cierraFicheros();
         controlFicheros.cierraIndicadorProgreso();
     }

//*******************************************************************************

     private void creaLectores() {
         FicheroHead fhTemp;
         Vector vectorLectores = new Vector();

         for (int i = 0; i < vectorFicherosHead.size(); i++) {
             fhTemp = (FicheroHead) vectorFicherosHead.elementAt(i);
             Parametro[] parG = fhTemp.getParametros();

             for (int j = 0; j < parG.length; j++) {
                 if (parG[j].getActivado()) { // Solo se crean lectores para ficheros con algun parametro activado para volcar.
                     LeeFicheroDat lfd = new LeeFicheroDat(fhTemp, parG[j]);
                     //      LeeFicheroDat lfd = new LeeFicheroDat( fhTemp, parG[j], 250.0F );
                     vectorLectores.add(lfd);
                 }
             }
         }

         // Creacion del array, una vez seleccionados todos los ficheros y parametros para volcar.
         arrayLectores = new LeeFicheroDat[vectorLectores.size()];

         for (int i = 0; i < arrayLectores.length; i++) {
             arrayLectores[i] = (LeeFicheroDat) vectorLectores.elementAt(i);
         }

     }

//*******************************************************************************

     private boolean vuelcaDatos() {
         LeeFicheroDat lfdTemp = null;
         String lineaVolcado = "";
         BufferedWriter salida = null;
         FileWriter fw = null;
         String datoLeido = "";
         int numeroLectoresFinalizados = 0;
         String separador;

         try {
             fw = new FileWriter(ficheroDestino);
             salida = new BufferedWriter(fw);
         } catch (Exception e) {
             controlFicheros.cierraIndicadorProgreso();
             JOptionPane.showMessageDialog(controlInterfaz.getPanelPrincipal(),
                                           "Error al crear fichero de exportacion");
             System.out.println("Error al crear fichero de exportacion");
//      throw new ErrorExportandoException();
         }
         // Abre todos los ficheros.
         for (int i = 0; i < arrayLectores.length; i++) {
             arrayLectores[i].abreFichero();
         }

         // Comienza la lectura de datos y su volcado.

         for (int j = 0; numeroLectoresFinalizados < arrayLectores.length; j++) {
             if (cancel) {
                 controlFicheros.cierraIndicadorProgreso();
                 try {
                     salida.close();
                     fw.close();
                 } catch (Exception e) {
                     System.out.println("Error al cerrar fichero de salida");
                 }
                 boolean res = ficheroDestino.delete();
                 String fichName = ficheroDestino.getName();
                 File fichCXP = new File(ficheroDestino.getParent() +
                                         File.separator +
                                         fichName.substring(0,
                         fichName.indexOf(".txt")) + ".cxp");
                 res = fichCXP.delete();
                 return true;
             }
//    controlFicheros.notificaProgreso(j);
             numeroLectoresFinalizados = 0; // Si no han finalizado todos, empezamos desde 0.
             for (int i = 0; i < arrayLectores.length; i++) {
                 if (i < (arrayLectores.length - 1)) {
                     separador = "\t";
                 } else {
                     separador = "\n";
                 }

                 try {

                     datoLeido = arrayLectores[i].getSiguiente();
                     if (datoLeido == null) {
                         lineaVolcado = lineaVolcado + separador;
                         numeroLectoresFinalizados++;
                     } else {
                         lineaVolcado = lineaVolcado + datoLeido + separador;
                     }

                     //  System.out.println(lineaVolcado);
                 } catch (Exception e) {
                     System.out.println("Error al volcar linea");
                     e.printStackTrace();
                 }

             } // Fin for i

             try {
                 salida.write(lineaVolcado);
                 salida.flush();
                 lineaVolcado = "";
             } catch (Exception e) {
                 System.out.println("Error al escribir linea");
             }

             controlFicheros.notificaProgreso(j);
//System.out.println("Progreso: " + j);
         } // Fin for j

         // Cerrado de archivos de lectura y escritura.
//  for(int i=0; i< arrayLectores.length; i++) arrayLectores[i].cierraFichero();

         try {
             salida.close();
             fw.close();
         } catch (Exception e) {
             System.out.println("Error al cerrar fichero de salida");
         }

         return false;
     } // Fin vuelcaDatos


//*******************************************************************************

// INCLUIR METODO PARA CERRAR TODOS LOS FICHEROS ABIERTOS
     public void cierraFicheros() {

         for (int i = 0; i < arrayLectores.length; i++) {
             arrayLectores[i].cierraFichero();
         }

     }

} // Fin clase EscribeHead_ASCII
