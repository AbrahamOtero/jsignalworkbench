package net.javahispano.jsignalwb;

/**
 * Esta excepcion se lanza cuando se le pide al API de JSignalWorkbench se
 * realice una operacion sobre una senhal que no existe. Habitualmente es indicio
 * de un error de programacion por parte del codigo cliente.
 *
 * @author Abraham Otero
 */
public class SignalNotFoundException extends RuntimeException {
    private String signalName;
    /**
     * SignalNotFoundException
     *
     * @param signalName nombre de la senhal que no se ha encontrado.
     * @param msg mensajes de error.
     * @param e Throwable que ha provocado esta excepcion.
     */
    public SignalNotFoundException(String signalName, String msg, Throwable e) {
        super(generateMessage(msg, signalName), e);
        this.signalName = signalName;
    }


    /**
     * SignalNotFoundException
     *
     * @param signalName nombre de la senhal que no se ha encontrado.
     * @param msg mensajes de error.
     */
    public SignalNotFoundException(String signalName, String msg) {
        super(generateMessage(msg, signalName));
        this.signalName = signalName;
    }

    /**
     * getSignalName
     *
     * @return Nombre de la senhal sobre la cual se solicito realizar una
     *   operacion y que no existe actualmente en el entorno.
     */
    public String getSignalName() {
        return signalName;
    }

    private static String generateMessage(String msg, String signalName) {
        String text = "\n The missing signal is " + signalName;
        if (msg != null) {
            msg += msg + text;
        } else {
            msg = text;
        }
        return msg;
    }

}
