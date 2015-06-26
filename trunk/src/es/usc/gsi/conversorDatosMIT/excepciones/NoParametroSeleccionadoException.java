package es.usc.gsi.conversorDatosMIT.excepciones;


public class NoParametroSeleccionadoException extends Exception {

    public NoParametroSeleccionadoException() {
        super("No se ha seleccionado ningun parametro para exportar");
    }
}