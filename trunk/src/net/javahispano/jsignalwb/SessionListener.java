package net.javahispano.jsignalwb;


/**
 * Aquellos plugin que deseen ser alertados de los eventos de creacion, guardado y destruccion de sesiones deberan
 * implementar esta interfaz. Para registrarse deberan emplear el metodo addSessionListenet de {@link JSWBManager}.
 */
public interface SessionListener {
    /**
     * Se invoca cuando se ha creado una sesion nueva. Dicha sesion no tiene por que estar guardada.
     *
     * @param event SessionEvent
     */
    public void sessionCreated(SessionEvent event);

    /**
     * Se invoca cuando se guarda una sesion de trabajo.
     *
     * @param event SessionEvent
     */
    public void sessionSaved(SessionEvent event);

    /**
     * Se invoca cuando una sesion de trabajo es destruida. Esto puede suceder
     * porque se carga otra sesion de trabajo, o porque el usuario pulsa el boton de "Nuevo".
     *
     * @param event SessionEvent
     */
    public void sessionDestroyed(SessionEvent event);
}
