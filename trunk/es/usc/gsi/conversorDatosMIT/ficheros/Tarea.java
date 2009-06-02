package es.usc.gsi.conversorDatosMIT.ficheros;

import java.util.Vector;

import es.usc.gsi.conversorDatosMIT.ficheros.lectura.LeeFicheroDat;
import es.usc.gsi.conversorDatosMIT.interfaz.ControladorInterfaz;

public class Tarea extends Thread implements Cancelar {
    private ControladorInterfaz controlInterfaz = ControladorInterfaz.
                                                  getControlador();
    private Parametro[] parametros = null;
    private Vector ficherosHead;
    private boolean cancel = false;
    private boolean memoryError = false;

//**********************************************************************************************

     public Tarea(Vector fh) {
         this.ficherosHead = fh;
         this.setPriority(Thread.MIN_PRIORITY);
     }

//**********************************************************************************************

     public void cancelar() {
         cancel = true;
     }

//**********************************************************************************************

     public Parametro[] getParametros() {
         return parametros;
     }

//**********************************************************************************************

     public boolean hasMemoryError() {
         return memoryError;
     }

//**********************************************************************************************

     public void run() {
         Vector copiaParamTemp = new Vector();

         try {
             int total = 1;
             for (int i = 0; i < ficherosHead.size(); i++) {
                 FicheroHead fh = (FicheroHead) ficherosHead.elementAt(i);
                 Parametro[] p = fh.getParametros();
                 for (int j = 0; j < p.length; j++) {
                     if (p[j].getActivado()) {
                         if (cancel) {
                             controlInterfaz.cierraIndicadorProgreso();
                             return;
                         }
                         int[] pValorTemp = (new LeeFicheroDat(fh, p[j])).
                                            getTodosValores(); // Lectura de todos los valores
                         p[j].setValores(pValorTemp); // Asigna los valores al parametro.
                         copiaParamTemp.add(p[j]);
                         controlInterfaz.notificaProgreso(total++);
                     } // Fin if p[j]
                 } // Fin for j
             } // Fin for i

             // Conversion a array.
             parametros = new Parametro[copiaParamTemp.size()];

             for (int i = 0; i < parametros.length; i++) {
                 Parametro pTemp = (Parametro) copiaParamTemp.elementAt(i);
                 parametros[i] = new Parametro(pTemp); // Creamos un array con copias de parametros
                 parametros[i].setValores(pTemp.getValores()); // Referenciamos los valores de un parametro hacia otro.
                 pTemp.setValores(null);
             }
         } catch (OutOfMemoryError err) {
             parametros = null;
             memoryError = true;
             controlInterfaz.cierraIndicadorProgreso();
         }
         controlInterfaz.cierraIndicadorProgreso();
     } // End run()
}
