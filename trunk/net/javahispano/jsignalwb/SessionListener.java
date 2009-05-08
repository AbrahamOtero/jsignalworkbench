package net.javahispano.jsignalwb;


/**
 * Aquellos plugin que deseen ser alertados de los eventos de creaci�n, guardado y destrucci�n de sesiones deber�n
 * implementar esta interfaz. Para registrarse deber�n emplear el m�todo addSessionListenet de {@link JSWBManager}.
 */
public interface SessionListener {
    /**
     * Se invoca cuando se ha creado una sesi�n nueva. Dicha sesi�n no tiene por qu� estar guardada.
     *
     * @param event SessionEvent
     */
    public void sessionCreated(SessionEvent event);

    /**
     * Se invoca cuando se guarda una sesi�n de trabajo.
     *
     * @param event SessionEvent
     */
    public void sessionSaved(SessionEvent event);

    /**
     * Se invoca cuando una sesi�n de trabajo es destruida. Esto puede suceder porque se carga otra sesi�n de trabajo,
     * o porque el usuario pulsa el bot�n de "Nuevo".
     *
     * @param event SessionEvent
     */
    public void sessionDestroyed(SessionEvent event);
}
