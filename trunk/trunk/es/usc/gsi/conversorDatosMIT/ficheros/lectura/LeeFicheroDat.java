package es.usc.gsi.conversorDatosMIT.ficheros.lectura;


import java.io.*;
import java.util.Vector;

import es.usc.gsi.conversorDatosMIT.algoritmos.*;
import es.usc.gsi.conversorDatosMIT.ficheros.FicheroHead;
import es.usc.gsi.conversorDatosMIT.ficheros.Parametro;
import es.usc.gsi.conversorDatosMIT.utilidades.ParseadorFecha;

public class LeeFicheroDat {

    private FicheroHead ficheroHead; // Fichero de cabecera
    private Parametro parametro; // Parametro que muestrearemos
    private int indiceParametro; // Indice del parametro dentro de su array

    private int factorFrecuencia; // Factor que multiplica la frecuencia del frame.

    private int muestrasTotalesPorMarco; // Numero de muestras de todos los parametros por cada marco.
    private int muestrasParametroPorMarco; // Numero de muestras del parametro en un marco.
    private int muestrasParametrosAnteriores; // Numero de muestras de los parametros anteriores a este en el marco
    // en el cual leeremos. Hara las veces de un offset para desplazarse dentro del marco.

    private float frecuenciaOriginal; // Frecuencia original a la cual se muestreo la senhal
    private float frecuenciaRemuestreo; // Frecuencia a la que queremos remuestrear
    private float factorConversion; // Factor de conversion de num muestras remuestreadas a num muestras originales

    private BufferedRandomAccessFile entrada; // Entrada de datos desde fichero
    // private CacheMITDB entrada;
    // private RandomAccessFile entrada;

    private long numMuestra; // Numero ordinal de muestra que leeremos en la siguiente invocacion de getSiguiente()
    private long numMuestraFinal; // Ordinal hasta el cual leeremos: es la muestra en la qu acaba la lectura.
    private long numMuestrasTotal; // Numero que indica que cantidad de muestras existen.

    private int valorMuestra; // Valor numerico de la muestra en formato entero.

    private int formato;

    private float stepBytes; // Numero de bytes por muestra <=> Numero de bytes que hay que avanzar para leer la siguiente muestra.
    private int sizeArrayBytes; // Tamanho del array de bytes.

    private InterfazConversion algoritmoConversion; // Algoritmo de conversion propio para este formato.

    public LeeFicheroDat(FicheroHead ficheroHead, Parametro parametro) {

        this.ficheroHead = ficheroHead;
        this.parametro = parametro;

        this.frecuenciaRemuestreo = parametro.getFrecuenciaMuestreo();
        this.frecuenciaOriginal = parametro.getBackupFrecuenciaMuestreo();

        // Equivalente a numMuestrasOriginales/numMuestrasRemuestreo:
        // es un factor de conversion para pasar un numero de muestras
        // de remuestreo a uno de muestras originales, que son las muestras
        // sobre las que trabajamos.
        this.factorConversion = frecuenciaOriginal / frecuenciaRemuestreo;

        this.factorFrecuencia = parametro.getFactorFrecuencia();

        // Son equivalentes: el factor de frecuencia indica cuantas muestras
        // hay en cada marco.
        this.muestrasParametroPorMarco = this.factorFrecuencia;
        this.indiceParametro = this.localizaIndiceParametro();
        this.muestrasTotalesPorMarco = this.calculaMuestrasTotalesPorMarco();
        this.muestrasParametrosAnteriores = this.
                                            calculaMuestrasParametrosAnteriores();

        // Inicializamos los parametros de conteo.
        long intervaloNumMuestra = ParseadorFecha.calculaDiferencia(
                parametro.getBackupFechaInicio(), parametro.getFechaInicio()
                                   );

        long intervaloNumMuestraFinal = ParseadorFecha.calculaDiferencia(
                parametro.getBackupFechaInicio(), parametro.getFechaFin()
                                        );

        this.numMuestra = (long) (intervaloNumMuestra *
                                  parametro.getFrecuenciaMuestreo());

        this.numMuestraFinal = (long) (intervaloNumMuestraFinal *
                                       parametro.getFrecuenciaMuestreo());

        /*  System.out.println(parametro.getBackupFechaInicio());
          System.out.println(parametro.getFechaInicio());
          System.out.println(parametro.getFechaFin()); */
        // System.out.println(numMuestra);
        // System.out.println(numMuestraFinal);
        // System.out.println(intervaloNumMuestra);
        // System.out.println(intervaloNumMuestraFinal);

        // Estudiar los intervalos en los que puede haber error e implementar excepciones.
        if (numMuestra < 0) {
            numMuestra = 0;
        }
        if (numMuestraFinal < 0) {
            numMuestraFinal = 1;
        }

        this.numMuestrasTotal = (ficheroHead.getNumMuestras()) *
                                this.factorFrecuencia - 1; // Hay que corregir: la 10 muestra tendra el indice 9, p.ej.

        // "Step" de avance dentro del fichero y conversion de formato

        switch (this.parametro.getFormatoAlmacenamiento()) {

        case Parametro.F16:
            this.algoritmoConversion = new AlgoritmoF16();
            break;

        case Parametro.F61:
            this.algoritmoConversion = new AlgoritmoF61();
            break;

        case Parametro.F212:

            this.algoritmoConversion = new AlgoritmoF212();
            break;

        default: // POR DEFECTO SE COGE EL ALGORITMO F61 PARA QUE EL FICHERO SE INTERPRETE MAL, PERO POR LO MENOS NO HAYA ERRORES DE EJECUCION.
            this.algoritmoConversion = new AlgoritmoF61();

        } // Fin switch

        this.stepBytes = this.algoritmoConversion.getStep();

        this.sizeArrayBytes = this.algoritmoConversion.getArraySize();

    } // Fin constructor


    ////////////////////

    // Transforma una fecha en formato cadena en segundos desde 1-1-1970
    // Puesto que el formato de los .hea es hh:mm:ss.milis dd/mm/yyyy
    // tendremos primero que convertir la fecha a un formato adecuado
    // para trabajar con las clases Java: dd/mm/yyyy hh:mm:ss
    // CAMBIADO: AHORA LA FECHA SE ALMACENA EN FORMATO HISPANO EN LOS OBJETOS FICHEROHEAD
    // Y POR LO TANTO NO HACE FALTA CONVERTIR.
    private long convierteASegundos(String fecha) {

        long res = 0;
        // String fecha=ParseadorFecha.convierteAFormatoHispano(fechaMITDB); -> Si fuese en formato MITDB
        res = ParseadorFecha.convierteASegundos(fecha);
        return res;

    }

    //////////////////
    private int calculaMuestrasTotalesPorMarco() {
        int res = 0;
        Parametro[] parG = ficheroHead.getParametros();
        // System.out.println(parG.length);

        for (int i = 0; i < parG.length; i++) {
            // System.out.println(parG[i].getNombreParametro());
            res = res + parG[i].getFactorFrecuencia();
        }
        return res;

    }

////////////////////

    private int calculaMuestrasParametrosAnteriores() {
        int res = 0;

        Parametro[] parG = ficheroHead.getParametros();
        // System.out.println("parG: " + parG.length);
        for (int i = 0; i < indiceParametro; i++) {
            res = res + parG[i].getFactorFrecuencia();
        }

        return res;

    }

////////
    private int localizaIndiceParametro() {
        int res;
        Parametro[] parG = ficheroHead.getParametros();
        for (res = 0; parametro != parG[res]; res++) {
            ;
        }
        return res;

    }

////// Busqueda de muestra original correspondiente a la muestra de remuestreo
    private int buscaMuestraOriginal() {

        int res;

        // Buscamos el marco donde esta la muestra
        int numMuestraOriginal = (int) (numMuestra * factorConversion);
        int numMarco = numMuestraOriginal / muestrasParametroPorMarco;
        int desplazamiento = numMuestraOriginal % muestrasParametroPorMarco;

        res = numMarco * muestrasTotalesPorMarco + muestrasParametrosAnteriores +
              desplazamiento;

        return res;

    }

///////////777
    private int leeValorMuestra(int posMuestra) throws IOException,
            EOFException {

        int res;
        int posBytes = (int) (stepBytes * posMuestra);
// System.out.println("Posicion en bytes: " + posBytes + " Pos. muestra: " + posMuestra);
        byte[] bytesMuestra = new byte[sizeArrayBytes];

        entrada.seek(posBytes); // Posicionamiento del puntero de lectura.

        // Lectura de los 2 bytes.
        if (entrada.read(bytesMuestra) == -1) {
            throw new EOFException();
        }
        // Conversion de los bytes a un int
        //   System.out.println("(" + bytesMuestra[0] + "," + bytesMuestra[1] + ")");
        // OBTENER RESULTADO DE CONVERSION
        res = algoritmoConversion.convierteBytes(bytesMuestra, posMuestra);

        return res;
    }

//////////////
    public void abreFichero() {

        try {

            File f = parametro.getFicheroDat();
            RandomAccessFile raf = new RandomAccessFile(f, "r");
            entrada = new BufferedRandomAccessFile(raf, 10240);

        } catch (Exception e) {
            System.out.println("Error al abrir el fichero .DAT");
        }

    } // Fin abrir fichero

    public void cierraFichero() {

        try {
            entrada.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar el fichero .DAT");
        }
    } // Fin cerrar fichero

    public String getSiguiente() throws IOException {

        String res;
        int posMuestraOriginal;
        int valorMuestraOriginal;

        if (numMuestra > numMuestraFinal) {
            return null; // Si ya hemos llegado al final del intervalo de remuestreo
        }

        posMuestraOriginal = this.buscaMuestraOriginal();

        try {
            valorMuestraOriginal = this.leeValorMuestra(posMuestraOriginal);
            res = Integer.toString(valorMuestraOriginal);
        } catch (EOFException e) {
            res = null;
        }

        numMuestra++; // Pasamos a la siguiente muestra
        //  System.out.println("Valor de la muestra: " + res);
        return res;

    } // Fin lectura del siguiente valor.

    // Devuelve todos los valores de un parametro en forma de array de enteros.

    public int[] getTodosValores() {

        int[] res = null;

        Vector valoresTemp = new Vector();
        String valorTemp;

        this.abreFichero();

        try {
            valorTemp = this.getSiguiente();
//@todo este bucle le uno por uno los valores como cadenas de caracteres
            while (valorTemp != null) {
                valoresTemp.add(valorTemp);
                valorTemp = this.getSiguiente();
            }
        } catch (IOException e) {}

        this.cierraFichero();
//@todo y aqui es donde reserva el espacio para los datos de verdad
        res = new int[valoresTemp.size()];
        System.out.println(res.length);

        for (int i = 0; i < res.length; i++) {
            res[i] = Integer.parseInt((String) valoresTemp.elementAt(i)) -
                     parametro.getOffset();
            if (parametro.getGanancia() != 200) {
                res[i] = (int) (res[i] / parametro.getGanancia());
            }
            //@todo
            /*
             anhadido el -parametro.getOffset()
             y
             if (parametro.getGanancia()!=200) {
                res[i]=(int) (res[i]/parametro.getGanancia());
                         }
             Por algun motivo, el algoritmo cuando la ganancia es 200 le perfectamente
             pero cuando es distinto de 200 no. Quizas el algoritmo esta preparado para
             que la ganancia sea 200 (no se ve que normalice y ningun sitio). Tambien
             he observado que no restan el ofset.

             No obstante, habria que verificar y que testearcuidadosamente estos cambios.
             */

        }

        return res;

    }


    // Main de prueba

    /* public static void main(String[] args) {
       FicheroHead fh=new FicheroHead("./f_box7_15-9-13_20-1-2000/senal.hea");
       Parametro[] parG=fh.getParametros();
       LeeFicheroDat lector = new LeeFicheroDat(fh, parG[0], 250.0F);
       lector.abreFichero();

       for (int i=0; i<100; i++) {
         try {
         System.out.println( lector.getSiguiente() );
         } catch (IOException e) { e.printStackTrace(); }
       }

       lector.cierraFichero();
     } */

} // Fin clase LeeFicheroDat
