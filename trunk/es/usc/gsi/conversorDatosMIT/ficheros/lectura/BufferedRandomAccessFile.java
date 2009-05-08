package es.usc.gsi.conversorDatosMIT.ficheros.lectura;

import java.io.*;

public class BufferedRandomAccessFile {

    private static final int DEFAULTBUFFERSIZE = 10240;

    private RandomAccessFile entrada;

    private int bufferSize; // Tama�o del buffer normal
    private int bufferSizeReducido; // Tama�o del buffer reducido: el �ltimo buffer que llenamos es menor o igual en tama�o que el buffer normal.

    private byte[] buffer = null; // Buffer de almacenamiento de datos

    private int numLecturasBuffer; // N�mero de veces que se ha rellenado el buffer = n�mero de lecturas en disco que se han hecho.
    private int numMaxLecturasNormales; // N�mero m�ximo de lecturas de bufferNormal que se pueden hacer. Despu�s, hay que leer un buffer reducido.

    private int posicionEnBuffer;

    private int numPeticion = 0;

    // Contructor con tama�o de buffer por defecto.
    public BufferedRandomAccessFile(RandomAccessFile entrada) {
        this(entrada, DEFAULTBUFFERSIZE);
    }

    public BufferedRandomAccessFile(RandomAccessFile entrada, int bufferSize) {

        this.entrada = entrada;
        this.bufferSize = bufferSize;

        try {
            this.numMaxLecturasNormales = (int) (entrada.length() / bufferSize); // N�mero de lecturas que podremos hacer con buffers de tama�o bufferSize.
            this.bufferSizeReducido = (int) (entrada.length() % bufferSize); // Tama�o del �ltimo buffer: el resto de los bytes que quedan por leer en el final del fichero.
        } catch (IOException e) {
            this.numMaxLecturasNormales = 0;
            this.bufferSizeReducido = 0;
        }

        this.numLecturasBuffer = 0; // No se ha hecho ninguna lectura
        this.posicionEnBuffer = 0; // La posici�n inicial es la primera

        this.llenaBuffer(); // LLenamos el buffer por primera vez.

    }

    // M�todos que se superponen a los de RandomAccessFile

    public void seek(long posicion) {

        int numLecturasSolicitado = (int) (posicion / bufferSize);

        if (numLecturasSolicitado != numLecturasBuffer) {
            this.numLecturasBuffer = numLecturasSolicitado;
            this.llenaBuffer();
        }

        // if (buffer==null) this.llenaBuffer();

        posicionEnBuffer = (int) (posicion % bufferSize);
        //  System.out.println("Posicion en buffer: " + posicionEnBuffer + " Posicion: " + posicion + "pet.: " + numPeticion);
        //  numPeticion++;

    } // Fin seek

    public int read(byte[] b) {

        int res = 0;

        for (int i = 0; i < b.length; i++) {

            if (posicionEnBuffer >= buffer.length) { // Si nos salimos del buffer mientras estamos leyendo
                int resTemp = this.llenaBuffer();
                numLecturasBuffer++;
                if (resTemp == -1) {
                    return -1; // Si se ha alcanzado el final del fichero, devolvemos -1 aunque pudiesemos llenar el array b con ceros: es mejor no dejar leer todo un array que rellenarlo con datos falsos.
                }
                posicionEnBuffer = 0;
            }

            b[i] = buffer[posicionEnBuffer];
            posicionEnBuffer++;

        }

        res = b.length;

        return res;

    } // Fin read

    public void close() {

        entrada = null;
        buffer = null;

    }

    private int llenaBuffer() {

        int res;

        if (numLecturasBuffer > numMaxLecturasNormales) {
            return -1; // No podemos leer m�s: estamos al final del fichero.
        }

        if (numLecturasBuffer == numMaxLecturasNormales) {
            if (bufferSizeReducido == 0) {
                return -1; // Devuelve -1 porque no existe buffer reducido: ya hemos llegado al final del fichero.
            }
            buffer = new byte[bufferSizeReducido];
        }

        else {
            buffer = new byte[bufferSize];
        }

        try {
            res = entrada.read(buffer);
        } catch (IOException e) {
            res = -1;
        }

        // numLecturasBuffer++;

        return res;

    }

} // Fin clase BufferedRandomAccessFile