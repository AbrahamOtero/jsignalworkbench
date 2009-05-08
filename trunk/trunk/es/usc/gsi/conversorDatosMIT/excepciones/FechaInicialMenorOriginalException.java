package es.usc.gsi.conversorDatosMIT.excepciones;

public class FechaInicialMenorOriginalException extends Exception {

    public FechaInicialMenorOriginalException() {
        super("La fecha inicial es menor que la original");
    }
}