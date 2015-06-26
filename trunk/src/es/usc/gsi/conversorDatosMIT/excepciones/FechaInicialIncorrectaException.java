package es.usc.gsi.conversorDatosMIT.excepciones;


public class FechaInicialIncorrectaException extends Exception {

    public FechaInicialIncorrectaException() {
        super("Fecha inicial con formato incorrecto");
    }
}