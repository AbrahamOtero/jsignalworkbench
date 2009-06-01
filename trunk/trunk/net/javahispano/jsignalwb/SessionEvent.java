package net.javahispano.jsignalwb;


/**
 * Evento que se genera cuando se crea, destruye o guarda una sesi�n de trabajo.
 */
public class SessionEvent {
    private boolean  newSession,saved,noSession,saveAs= false;
    SessionEvent(boolean created, boolean saved) {
        this.saved=saved;
        this.newSession=created;
        this.noSession= false;
    }
   SessionEvent(boolean saved) {
        this.saved=saved;
        this.newSession= false;
        this.noSession= true;
    }

    /**
     * isNewSession
     *
     * @return Cierto si la sesi�n de trabajo que gener� el evento es una nueva sesi�n; esto es, si se acaba de cargar
     *   en la herramienta. Devolver� falso si la sesi�n ya estaba cargada.
     */
    public boolean isNewSession() {
        return newSession;
    }

    /**
     * isNoSession
     *
     * @return Devolver� cierto si no hay ninguna sesi�n en el entorno en este momento. Sucede cuando el usuario pulsa
     *   el bot�n de "Nuevo".
     */
    public boolean isNoSession() {
        return noSession;
    }

    /**
     * isSaved
     *
     * @return Cierto si la sesi�n de trabajo que gener� el evento est� guardada, falso en caso contrario.
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
