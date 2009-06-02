package es.usc.gsi.conversorDatosMIT.algoritmos;


public class AlgoritmoF212 implements InterfazConversion {

    private int arraySize = 2; // Numero de bytes necesarios para poder extraer una muestra del fichero.
    private float step = 12.0F / 8.0F; // Numero de bytes que ocupa una muestra: 12 bits / 8 bits por byte

    public int convierteBytes(byte[] b, int numMuestra) {
        // Algoritmo de conversion especifico para formato 61
        int res = 0;
        if (numMuestra % 2 == 0) { // Hay que distinguir entre muestras pares o impares: dependiendo de esto, el algoritmo de conversion es distinto.

            res = (((b[1] & 0x0f) << 28) >> 20) | // Segunda mitad del segundo byte: parte mas significativa
                  (b[0] & 0xff); // Todo el primer byte: parte menos significativa.

        } else {
            res = (((b[0] & 0xf0) << 24) >> 20) |
                  (b[1] & 0xff);

        }

        return res;

    }

    public float getStep() {
        return step;
    }

    public int getArraySize() {
        return arraySize;
    }


} // Fin AlgoritmoF212
