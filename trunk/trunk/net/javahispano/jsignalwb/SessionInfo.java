/*
 * SessionInfo.java
 *
 * Created on 30 de julio de 2007, 14:13
 */

package net.javahispano.jsignalwb;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roman Segador
 */
public class SessionInfo {
    private String lastFileOpenedPath;
    private String lastLoaderUsed;
    private String lastSaverUsed;
    private List<String> pluginsToDelete;
    private boolean sessionSaved;
    private boolean debugMode;
    public SessionInfo() {
        sessionSaved = true;
        pluginsToDelete = new ArrayList<String>();
        lastFileOpenedPath = "";
        lastLoaderUsed = "";
        lastSaverUsed = "";
        debugMode = true;
    }

    public String getLastFileOpenedPath() {
        return lastFileOpenedPath;
    }

    public void setLastFileOpenedPath(String lastFileOpenedPath) {
        this.lastFileOpenedPath = lastFileOpenedPath;
    }

    public String getLastLoaderUsed() {
        return lastLoaderUsed;
    }

    public void setLastLoaderUsed(String lastLoaderUsed) {
        this.lastLoaderUsed = lastLoaderUsed;
    }

    public String getLastSaverUsed() {
        return lastSaverUsed;
    }

    public void setLastSaverUsed(String lastSaverUsed) {
        this.lastSaverUsed = lastSaverUsed;
    }

    public void addPluginToDelete(String pluginToDelete) {
        pluginsToDelete.add(pluginToDelete);
    }

    public List<String> getPluginsToDelete() {
        return pluginsToDelete;
    }

    public boolean isSessionSaved() {
        return sessionSaved;
    }

    public void setSessionSaved(boolean sessionSaved) {
        this.sessionSaved = sessionSaved;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
