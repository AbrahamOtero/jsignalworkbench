package es.usc.gsi.conversorDatosMIT.excepciones;


public class DirectorioVacioException extends Exception {

    public DirectorioVacioException() {
        super("Directorio vacio");
    }

}