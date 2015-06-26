package es.usc.gsi.conversorDatosMIT.ficheros.lectura;

import java.io.*;

// Los metodos de esta clase se superpondran a los de RandomAccessFile: se devuelven
// los datos de la buffer mientras se pueda, y si no se recarga la buffer para poder devolverlos.
public class CacheMITDB {

    private RandomAccessFile entrada; // Fichero de entrada
    private byte[] buffer; // Array de almacenamiento en memoria

    private byte[] bufferNormal; // Array de tamanho igual al especificado para este buffer.
    private byte[] bufferFinal; // Array de tamanho igual al numero de bytes restantes.
    // Se implementa este buffer para que el ultimo buffer que se lea
    // no devuelva una cantidad de 0's en el array debido a
    // que se intenta leer fuera del fichero. Por lo tanto,
    // este ultimo buffer debe ser de tamanho menor al normal.

    private static final int TAMANHO_buffer = 10240; // Tamanho de la buffer: tamanho del array buffer.

    private int numLectura = 0; // Numero de veces que se ha rellenado el array
    private int numMaxLecturasNormales; // Numero de lecturas de buffers normales que podemos hacer
    // Depues de este numero de lecturas, deberemos leer un bufferFinal.
    private long posicionLecturaActual = 0; // Posicion de lectura actual dentro del fichero.

    private int posicionEnArray = 0; // Posicion de lectura dentro del array.

    public CacheMITDB(File ficheroDat) throws FileNotFoundException {
        this(ficheroDat, TAMANHO_buffer);
    }

    public CacheMITDB(File ficheroDat, int bufferSize) throws
            FileNotFoundException {

        this.numMaxLecturasNormales = (int) (ficheroDat.length() / bufferSize);
        int bufferSizeFinal = (int) (ficheroDat.length() % bufferSize); // El tamanho del buffer final sera el del resto.

        entrada = new RandomAccessFile(ficheroDat, "r");

        this.bufferNormal = new byte[bufferSize];
        this.bufferFinal = new byte[bufferSizeFinal]; // IMPORTANTE: QUE OCURRE SI bufferSizeFinal=0?
        // ADEMAS: MEJORAR ESTO. SI NO, EXISTIRAN 2 ARRAYS ABIERTOS EN MEMORIA A LA VEZ.
        this.llenaBuffer();

    }

    public void seek(long posicion) {

        // Si la posicion se sale del rango del array buffer, localizamos el nuevo punto
        // para leer del fichero y llenamos el array.
        if (posicion >= (posicionLecturaActual + buffer.length) ||
            posicion < posicionLecturaActual) {

            numLectura = (int) (posicion / buffer.length); // Division entera: nos indica que cantidad de bufferes debemos avanzar para leer los datos
            this.llenaBuffer();

        }

        // Almacenamos la posicion que se leera del array.
        posicionEnArray = (int) (posicion % buffer.length);

    }

    public int read(byte[] resultado) {

        int numBytesLeidos = 0;

        for (int i = 0; i < resultado.length; i++) {

            if (posicionEnArray + i >= buffer.length) { // Si nos salimos de indice mientras estamos leyendo...
                numLectura++; // Avanzamos una posicion de lectura para leer el siguiente array buffer.
                if ( -1 == this.llenaBuffer()) {
                    return -1; // Cargamos la buffer y comprobamos que no hemos llegado al final del fichero.
                }
                posicionEnArray = 0; // Nos posicionamos al principio de la buffer.
            }

            resultado[i] = buffer[posicionEnArray];
            numBytesLeidos++;
            posicionEnArray++;

//      System.out.println("Posicion de lectura: " + posicionLecturaActual);
            //     System.out.println("Posicion de lectura array: " + posicionEnArray);

        } // Fin for i

        return numBytesLeidos;
    }

    public void close() throws IOException {

        entrada.close();

    }

    private int llenaBuffer() {

        int res;
        if (numLectura > numMaxLecturasNormales) {
            buffer = bufferFinal;
        } else {
            buffer = bufferNormal;
        }

        posicionLecturaActual = (long) numLectura * buffer.length; // INCORRECTO: CALCULAR LA POSICION REAL TENIENDO EN CUENTA SI ESTAMOS USANDO BUFFERFINAL O NO.
        try {
            entrada.seek(posicionLecturaActual);
            res = entrada.read(buffer);
        } catch (IOException e) {
            res = -1;
        }
        return res;

    }

} // Fin clase CacheMITDB
