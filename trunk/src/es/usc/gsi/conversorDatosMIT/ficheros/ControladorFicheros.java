// SIGUE EL PATRON SINGLETON: UN SOLO OBJETO
package es.usc.gsi.conversorDatosMIT.ficheros;

import java.io.File;
import java.util.Vector;

import es.usc.gsi.conversorDatosMIT.excepciones.*;
import es.usc.gsi.conversorDatosMIT.ficheros.escritura.EscribeHead_ASCII;
import es.usc.gsi.conversorDatosMIT.interfaz.ControladorInterfaz;

public class ControladorFicheros {

    private static ControladorFicheros controlador = new ControladorFicheros();
    public static ControladorFicheros getControlador() {
        return controlador;
    }

    private static ControladorInterfaz controlInterfaz;

    public static void enlazaControladorInterfaz() {

        controlInterfaz = ControladorInterfaz.getControlador();
    }

    private Vector ficherosHead = new Vector();


    // Constructor privado
    private ControladorFicheros() {}

    public FicheroHead getNuevoHead(String path) throws
            FicheroNoValidoException {

        FicheroHead res = new FicheroHead(path);
        this.setNuevoHead(res);
        return res;
    }

    public void setNuevoHead(FicheroHead fh) {

        ficherosHead.add(fh);

    }

    public void eliminaHead(FicheroHead fh) {

        ficherosHead.remove(fh);

    }

    public Vector getFicherosHead() {

        return ficherosHead;

    }

    public FicheroHead[] getFicherosHeadArray() {

        FicheroHead[] res = null;

        if (ficherosHead.size() == 0) {
            return null;
        }

        res = new FicheroHead[ficherosHead.size()];
        for (int i = 0; i < ficherosHead.size(); i++) {
            res[i] = (FicheroHead) ficherosHead.elementAt(i);
        }

        return res;

    }

    // DEVUELVE UN ARRAY CON LOS PARAMETROS SELECCIONADOS
    // Y DENTRO DE CADA UNO DE LOS PARAMETROS EXISTE UN ARRAY
    // QUE CONTIENE TODOS LOS VALORES.

    public Parametro[] getParametrosSeleccionados() throws OutOfMemoryError {
        int maxBarraProgreso = Parametro.numSeleccionados;
        Tarea hilo = new Tarea(ficherosHead);
        hilo.start();
        this.creaIndicadorProgreso((Cancelar) hilo, "Importing data",
                                   "Importing...", maxBarraProgreso, 0, true);
        if (hilo.hasMemoryError()) {
            throw new OutOfMemoryError(
                    "No hay memoria suficiente para completar la tarea");
        }
        return hilo.getParametros();
    }

    public void vaciaVectorFicheros() {
        this.ficherosHead.removeAllElements();
    }

    public boolean existenArchivosAbiertos() {
        return ficherosHead.isEmpty();
    }

    public void volcarFicherosHead_ASCII(File ficheroDestino) throws
            ErrorExportandoException {

        // INCLUIR CODIGO DE PREPROCESAMIENTO DEL PATH PARA
        // QUE EL FICHERO TENGA UN NOMBRE ADECUADO.
        int maxBarraProgreso = calcularMaxBarraProgreso();
        EscribeHead_ASCII hilo = new EscribeHead_ASCII(ficherosHead,
                ficheroDestino);
        hilo.start();
        this.creaIndicadorProgreso(hilo, "Exportando datos", "Exportando...",
                                   maxBarraProgreso, 0, false);
    }

    private int calcularMaxBarraProgreso() {
        int max = 0;
        FicheroHead fh;
        for (int i = 0; i < ficherosHead.size(); i++) {
            fh = (FicheroHead) ficherosHead.elementAt(i);
            if (fh.getNumParametrosActivos() != 0) {
                int num = (int) fh.getNumMuestras();
                if (num > max) {
                    max = num;
                }
                Parametro[] params = fh.getParametros();
                int factorMax = 1;
                for (int j = 0; j < params.length; j++) {
                    if (params[j].getActivado()) {
                        int factorFrec = params[j].getFactorFrecuencia();
                        if (factorFrec > factorMax) {
                            factorMax = factorFrec;
                        }
                    }
                }
                max = max * factorMax;
            }
        } // End for
        return max;
    }

    // Devuelve todos los ficheros head en un directorio menos los
    // denotados por senal.hea y los no validos.

    public FicheroHead[] getFicherosHeadDirectorio(File f) throws
            DirectorioVacioException {

        // Debe estar aqui: si no, cuando se crea ControladorInterfaz

        FicheroHead[] res;
        File[] listaFicheros;
        Vector temp1 = new Vector(); // Almacena todos los ficheros .hea menos senal.hea --AHORA SI LOS ALMACENA
        Vector temp2 = new Vector(); // Almacena todos los ficheros .hea VALIDOS.

        listaFicheros = f.listFiles();

        if (listaFicheros == null) {
            throw new DirectorioVacioException();
        }

//    this.creaIndicadorProgreso(null, "Leyendo archivos", "Leyendo", listaFicheros.length, 0);

        for (int i = 0; i < listaFicheros.length; i++) {

//    this.notificaProgreso(i);

            // Mejorar este if: es demasiado largo.
            if (
                    (listaFicheros[i].getName().indexOf(".hea") != -1 ||
                     listaFicheros[i].getName().indexOf(".HEA") != -1) && // Con extension .hea
                    // !listaFicheros[i].getName().equalsIgnoreCase("senal.hea") && // Sin ser senal.hea
                    !listaFicheros[i].isDirectory() // Sin ser un directorio
                    ) {
                temp1.add(listaFicheros[i]);

            } // Fin if

        } // Fin for i

        for (int j = 0; j < temp1.size(); j++) {
            try {
                FicheroHead fhTemp = new FicheroHead((File) temp1.elementAt(j));
                temp2.add(fhTemp);
            } catch (FicheroNoValidoException e) {}
        } // Fin for j

        if (temp2.size() == 0) {
            //       this.cierraIndicadorProgreso();
            throw new DirectorioVacioException();
        }

        res = new FicheroHead[temp2.size()];

        for (int i = 0; i < res.length; i++) {
            res[i] = (FicheroHead) temp2.elementAt(i);
        }

//      this.cierraIndicadorProgreso();
        return res;

    }

    // Verifica condiciones de exportacion para decidir si se puede exportar el grupo de
    // ficheros o no.
    public void esExportable() throws NoPacienteAbiertoException,
            NoParametroSeleccionadoException {

        if (ficherosHead.size() == 0) {
            throw new NoPacienteAbiertoException(); // Si no se ha abierto ningun fichero head, no es exportable
        }

        for (int i = 0; i < ficherosHead.size(); i++) {

            FicheroHead fh = (FicheroHead) ficherosHead.elementAt(i);
            Parametro[] parG = fh.getParametros();

            for (int j = 0; j < parG.length; j++) {
                if (parG[j].getActivado()) {
                    return; //Si existe algun parametro activado, si es exportable.
                }
            }
        }

        throw new NoParametroSeleccionadoException(); // Si se ha acabado el bucle y no hay parametros seleccionados
    }

    // Creacion y manejo de indicadores de progreso graficos: son
    // aconsejables para operaciones intensivas en ficheros.

    public void creaIndicadorProgreso(Cancelar h, String titulo,
                                      String textoPrincipal, int max, int min,
                                      boolean _stop) {
        controlInterfaz.creaIndicadorProgreso(h, titulo, textoPrincipal, max,
                                              min, _stop);
    }

    public void notificaProgreso(int nuevoValor) {
        controlInterfaz.notificaProgreso(nuevoValor);
    }

    public void cierraIndicadorProgreso() {
        controlInterfaz.cierraIndicadorProgreso();
    }

} // Fin clase ControladorFicheros
