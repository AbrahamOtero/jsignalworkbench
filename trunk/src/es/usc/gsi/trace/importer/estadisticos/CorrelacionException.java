package es.usc.gsi.trace.importer.estadisticos;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>. </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class CorrelacionException extends Exception {
    private int longitutud1, longitud2;

    public CorrelacionException(String mensaje, int longitutud1,
                                int longitutud2) {
        super(mensaje);
        this.longitud2 = longitud2;
        this.longitutud1 = longitutud1;
    }

    /**
     * Devuelve la longitud del primero de los arrays
     * @return
     */
    public int getLongitud1() {
        return longitutud1;
    }

    /**
     * Devuelve la longitud del segundo de los arrays.
     * @return
     */
    public int getLongitud2() {
        return longitud2;
    }


}