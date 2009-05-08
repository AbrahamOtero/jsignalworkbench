package net.javahispano.jsignalwb;


/**
 * Aquellos plugin que deseen ser alertados de los eventos de creación, guardado y destrucción de sesiones deberán
 * implementar esta interfaz. Para registrarse deberán emplear el método addSessionListenet de {@link JSWBManager}.
 */
public interface SessionListener {
    /**
     * Se invoca cuando se ha creado una sesión nueva. Dicha sesión no tiene por qué estar guardada.
     *
     * @param event SessionEvent
     */
    public void sessionCreated(SessionEvent event);

    /**
     * Se invoca cuando se guarda una sesión de trabajo.
     *
     * @param event SessionEvent
     */
    public void sessionSaved(SessionEvent event);

    /**
     * Se invoca cuando una sesión de trabajo es destruida. Esto puede suceder porque se carga otra sesión de trabajo,
     * o porque el usuario pulsa el botón de "Nuevo".
     *
     * @param event SessionEvent
     */
    public void sessionDestroyed(SessionEvent event);
}
