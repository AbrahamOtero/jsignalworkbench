package net.javahispano.jsignalwb;


/**
 * Evento que se genera cuando se crea, destruye o guarda una sesion de trabajo.
 */
public class SessionEvent {
    private boolean newSession, saved, noSession, saveAs = false;
    SessionEvent(boolean created, boolean saved) {
        this.saved = saved;
        this.newSession = created;
        this.noSession = false;
    }

    SessionEvent(boolean saved) {
        this.saved = saved;
        this.newSession = false;
        this.noSession = true;
    }

    /**
     * isNewSession
     *
     * @return Cierto si la sesion de trabajo que genero el evento es una nueva sesion; esto es, si se acaba de cargar
     *   en la herramienta. Devolvera falso si la sesion ya estaba cargada.
     */
    public boolean isNewSession() {
        return newSession;
    }

    /**
     * isNoSession
     *
     * @return Devolvera cierto si no hay ninguna sesion en el entorno en este momento. Sucede cuando el usuario pulsa
     *   el boton de "Nuevo".
     */
    public boolean isNoSession() {
        return noSession;
    }

    /**
     * isSaved
     *
     * @return Cierto si la sesion de trabajo que genero el evento esta guardada, falso en caso contrario.
     */
    public boolean isSaved() {
        return saved;
    }

    public boolean isSaveAs() {
        return saveAs;
    }

    void setNoSesion(boolean noSession) {
        this.noSession = noSession;
    }

    public void setSaveAs(boolean saveAs) {
        this.saveAs = saveAs;
    }
}
