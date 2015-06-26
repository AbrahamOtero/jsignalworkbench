package es.usc.gsi.conversorDatosMIT.algoritmos;


public class AlgoritmoF61 implements InterfazConversion {

    private int arraySize = 2; // Numero de bytes necesarios para poder extraer una muestra del fichero.
    private float step = 16.0F / 8.0F; // Numero de bytes que ocupa una muestra: 16 bits / 8 bits por byte

    public int convierteBytes(byte[] b, int numMuestra) {
        // Algoritmo de conversion especifico para formato 61
        int res = (((b[0] & 0xff) << 24) >> 16) |
                  (b[1] & 0xff);

        return res;

    }

    public float getStep() {
        return step;
    }

    public int getArraySize() {
        return arraySize;
    }


} // Fin AlgoritmoF61
