package es.usc.gsi.conversorDatosMIT.excepciones;


public class ParametroNoValidoException extends Exception {

    public ParametroNoValidoException() {
        super("Parametro incorrecto");
    }
}