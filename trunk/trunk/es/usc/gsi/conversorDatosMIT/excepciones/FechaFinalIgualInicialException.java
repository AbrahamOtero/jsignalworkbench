package es.usc.gsi.conversorDatosMIT.excepciones;


public class FechaFinalIgualInicialException extends Exception {

    public FechaFinalIgualInicialException() {
        super("Las fechas inicial y final son iguales");
    }
}