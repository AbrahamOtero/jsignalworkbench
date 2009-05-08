package net.javahispano.jsignalwb;

/**
 * Esta excepción se lanza cuando se le pide al API de JSignalWorkbench se
 * realice una operación sobre una señal que no existe. Habitualmente es indicio
 * de un error de programación por parte del código cliente.
 *
 * @author Abraham Otero
 */
public class SignalNotFoundException extends RuntimeException {
    private String signalName;
    /**
     * SignalNotFoundException
     *
     * @param signalName nombre de la señal que no se ha encontrado.
     * @param msg mensajes de error.
     * @param e Throwable que ha provocado esta excepción.
     */
    public SignalNotFoundException(String signalName, String msg, Throwable e) {
        super(generateMessage(msg, signalName), e);
        this.signalName = signalName;
    }


    /**
     * SignalNotFoundException
     *
     * @param signalName nombre de la señal que no se ha encontrado.
     * @param msg mensajes de error.
     */
    public SignalNotFoundException(String signalName, String msg) {
        super(generateMessage(msg, signalName));
        this.signalName = signalName;
    }

    /**
     * getSignalName
     *
     * @return Nombre de la señal sobre la cual se solicitó realizar una
     *   operación y que no existe actualmente en el entorno.
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
