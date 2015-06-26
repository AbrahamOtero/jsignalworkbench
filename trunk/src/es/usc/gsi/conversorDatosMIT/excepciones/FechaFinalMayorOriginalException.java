package es.usc.gsi.conversorDatosMIT.excepciones;


public class FechaFinalMayorOriginalException extends Exception {

    public FechaFinalMayorOriginalException() {
        super("La fecha final es mayor que la original");
    }
}