package es.usc.gsi.conversorDatosMIT.excepciones;


public class FechasIncorrectasException extends Exception {

    public FechasIncorrectasException() {
        super("Error: alguna de las dos fechas o ambas estan mal");
    }
}