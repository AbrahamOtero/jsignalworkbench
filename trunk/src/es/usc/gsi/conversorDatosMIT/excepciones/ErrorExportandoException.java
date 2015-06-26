package es.usc.gsi.conversorDatosMIT.excepciones;


public class ErrorExportandoException extends Exception {

    public ErrorExportandoException() {
        super("Error exportando archivos");
    }
}