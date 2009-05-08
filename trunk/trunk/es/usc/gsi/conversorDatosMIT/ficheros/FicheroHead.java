// Clase FicheroHead: al crearlo, se abre el fichero de cabecera y
// se lee para asignarle valores a los atributos.
// Adem�s, se crean tantos objetos Parametro como
// par�metros haya en el fichero.

package es.usc.gsi.conversorDatosMIT.ficheros;

import java.io.*;

import es.usc.gsi.conversorDatosMIT.algoritmos.*;
import es.usc.gsi.conversorDatosMIT.excepciones.*;
import es.usc.gsi.conversorDatosMIT.utilidades.*;

public class FicheroHead extends File {

    private String nombreFrame;
    private int numSenhales;
    private float frecuenciaMuestreoFrame;
    private long numMuestras;

    private String fechaInicio;
    private String fechaFin;

    private Parametro[] parametros; // Array de parametros de este fichero head.

    private FileReader fr;
    private BufferedReader br;

    // Constructor
    public FicheroHead(String nombreFichero) throws FicheroNoValidoException {
        super(nombreFichero);
        try {
            fr = new FileReader(this);
            br = new BufferedReader(fr);

            this.leeFichero(); // Inicializaci�n de atributos.
            parametros = new Parametro[numSenhales]; // MEJOR: LEER PRIMERO EL FICHERO ALMACENANDO EN UN VECTOR
            // LOS PAR�METROS QUE SON V�LIDOS Y LUEGO CREAR EL ARRAY
            // A PARTIR DE ELLOS, POR SI HUBIESE ALGUNO DEFECTUOSO.
            this.leeParametros();

            // Estimaci�n del n�mero de muestras en funci�n del tama�o del fichero, si es necesario

            if (this.numMuestras == 0) {

                float step = 2.0f;
                Parametro p = this.parametros[0]; // Suponemos todos con el mismo formato y en mismo fichero

                switch (p.getFormatoAlmacenamiento()) {

                case Parametro.F16:
                    step = (new AlgoritmoF16()).getStep();
                    break;
                case Parametro.F61:
                    step = (new AlgoritmoF61()).getStep();
                    break;
                case Parametro.F212:
                    step = (new AlgoritmoF212()).getStep();
                    break;
                }

                // C�lculo del n�mero de marcos en el fichero
                int frameSize = 0;
                float frameSizeBytes = 0.0f;
                File dataFile = new File(this.parametros[0].getNombreFichero());

                for (int i = 0; i < this.parametros.length; i++) {
                    frameSize += parametros[i].getFactorFrecuencia();
                }

                frameSizeBytes = frameSize * step;

                this.numMuestras = dataFile.length() / (long) frameSizeBytes;

                long segundosMuestreo = (long) (this.numMuestras /
                                                this.frecuenciaMuestreoFrame);
                this.fechaFin = ParseadorFecha.calculaFechaFinal(fechaInicio,
                        segundosMuestreo);
            }

        } catch (Exception e) {
            System.out.println("Error al leer el fichero");
            throw new FicheroNoValidoException();
        }
        ;
    }

    public FicheroHead(File fich) throws FicheroNoValidoException {
        this(fich.getAbsolutePath());
    }

    // M�todos get

    public String getNombreFrame() {
        return this.nombreFrame;
    }

    public int getNumSenhales() {
        return this.numSenhales;
    }

    public float getFrecuenciaMuestreoFrame() {
        return this.frecuenciaMuestreoFrame;
    }

    public long getNumMuestras() {
        return this.numMuestras;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public int getNumParametrosActivos() {
        int num = 0;
        for (int i = 0; i < parametros.length; i++) {
            if (parametros[i].getActivado()) {
                num++;
            }
        }
        return num;
    }

    private void leeFichero() throws FicheroNoValidoException {

        String[] datosFichero;

        try {

            // Lectura de la primera l�nea del fichero de cabecera:
            // obtenemos los atributos de este objeto a partir de ella.

            // Poner c�digo que posicione el puntero de lectura
            // en la primera l�nea del fichero.

            String datosHead = br.readLine();

            datosFichero = ParseadorCadena.split(datosHead, " ");

            this.nombreFrame = datosFichero[0];
            this.numSenhales = Integer.parseInt(datosFichero[1]);
            this.frecuenciaMuestreoFrame = Float.parseFloat(datosFichero[2]);

            if (datosFichero.length < 4) {
                this.numMuestras = 0;
                this.fechaInicio = "01/01/1970 00:00:00";
                this.fechaFin = null;
                return;
            }

            this.numMuestras = Long.parseLong(datosFichero[3]);

            if (datosFichero.length < 6) {
                this.fechaInicio = "01/01/1970 00:00:00";
            } else {
                this.fechaInicio =
                        ParseadorFecha.convierteAFormatoHispano(datosFichero[4] +
                        " " + datosFichero[5]); // Reordenamos la fecha para que tenga un formato hispano

            }

            // TODO Si numMuestras = 0, estimar el n�mero de muestras a partir del tama�o del fichero.

            long segundosMuestreo = (long) (this.numMuestras /
                                            this.frecuenciaMuestreoFrame);
            this.fechaFin = ParseadorFecha.calculaFechaFinal(fechaInicio,
                    segundosMuestreo);

        } catch (Exception e) {
            System.out.println("Error al leer fichero head: " + this.getName());
            throw new FicheroNoValidoException();
        }

    }


    private void leeParametros() throws FicheroNoValidoException {
        // Poner c�digo que posicione el puntero de lectura
        // en la segunda l�nea del fichero.
        Parametro par;
        String lineaPar;
        String[] camposPar;
        String[] subCamposPar;

        // Usados para crear un nuevo objeto Parametro.
        String nombreFichero;
        int formatoAlmacenamiento;
        int factorFrecuencia;
        float ganancia;
        String unidades;
        int resolucionConversor;
        int offset;
        long primerValorLeido;
        long checksum;
        long tamanhoBloque;
        String nombreParametro;
        float frecuenciaMuestreo;

        try {

            for (int i = 0; i < numSenhales; i++) {

                lineaPar = br.readLine();
                /*  if (lineaPar.indexOf("#")!=-1) { // Si el fichero contiene comentarios, no creamos un par�metro y retrocedemos una posici�n para no saltar un elemento del array.
                    i--;
                    continue;
                    } */
                camposPar = ParseadorCadena.split(lineaPar, " "); // Lectura y particionado de linea de parametro

                nombreFichero = this.getParentFile().getAbsolutePath() +
                                File.separator + camposPar[0];

                subCamposPar = ParseadorCadena.split(camposPar[1], "x");

                formatoAlmacenamiento = Integer.parseInt(subCamposPar[0]);

                // Si s�lo existe un elemento en el array,
                //quiere decir que no hay "x n�mero" => factor de frecuencia =1
                if (subCamposPar.length < 2) {
                    factorFrecuencia = 1;
                } else {
                    factorFrecuencia = Integer.parseInt(subCamposPar[1]);
                }

                subCamposPar = ParseadorCadena.split(camposPar[2], "/");

                // Ignoramos baseline, si existe.
                if (subCamposPar[0].indexOf('(') != -1) {
                    ganancia = Float.parseFloat(subCamposPar[0].split("\\(")[0]);
                } else {
                    ganancia = Float.parseFloat(subCamposPar[0]);
                }

                // Igual que arriba: si s�lo hay un elemento en el array, no hay unidades.
                if (subCamposPar.length < 2) {
                    unidades = "";
                } else {
                    unidades = subCamposPar[1];
                }

                resolucionConversor = Integer.parseInt(camposPar[3]);
                offset = Integer.parseInt(camposPar[4]);
                primerValorLeido = Long.parseLong(camposPar[5]);
                checksum = Long.parseLong(camposPar[6]);
                tamanhoBloque = Long.parseLong(camposPar[7]);
                nombreParametro = camposPar[8];
                frecuenciaMuestreo = this.frecuenciaMuestreoFrame *
                                     factorFrecuencia;

                parametros[i] = new Parametro(nombreFichero,
                                              formatoAlmacenamiento,
                                              factorFrecuencia, ganancia,
                                              unidades, resolucionConversor,
                                              offset, primerValorLeido,
                                              checksum, tamanhoBloque,
                                              nombreParametro,
                                              frecuenciaMuestreo,
                                              fechaInicio, fechaFin
                                );

                //  System.out.println( parametros[i].getNombreParametro() );

            }

        } catch (Exception e) {
            System.out.println("Error de archivo al leer parametros");
            System.out.println("FicheroHead.leeParametros(): error " +
                               e.getMessage());
            e.printStackTrace();
            throw new FicheroNoValidoException();
        }

        // Si al acabar de leer los par�metros no hay ninguno leido,
        // entenderemos que el fichero no es v�lido, bien porque
        // ning�n par�metro es v�lido o bien porque no existen par�metros en
        // el fichero.

        if (parametros.length == 0) {
            throw new FicheroNoValidoException();
        }
    }

    /////////////////////
    public Parametro[] getParametros() {
        return parametros;
    }

    ////////////////////
    public Parametro getParametro(int indice) {
        return parametros[indice];
    }

} // Fin clase FicheroHead