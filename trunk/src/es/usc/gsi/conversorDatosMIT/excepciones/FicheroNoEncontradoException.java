package es.usc.gsi.conversorDatosMIT.excepciones;

public class FicheroNoEncontradoException extends Exception {

    public FicheroNoEncontradoException() {
        super("Error: no se encuentra el fichero");
    }
}