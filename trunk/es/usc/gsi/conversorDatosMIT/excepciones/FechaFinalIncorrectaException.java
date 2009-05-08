package es.usc.gsi.conversorDatosMIT.excepciones;

public class FechaFinalIncorrectaException extends Exception {

    public FechaFinalIncorrectaException() {
        super("La fecha final tiene un formato incorrecto");
    }
}