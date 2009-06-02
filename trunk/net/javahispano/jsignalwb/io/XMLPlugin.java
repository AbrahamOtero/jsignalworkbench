package net.javahispano.jsignalwb.io;

import net.javahispano.jsignalwb.plugins.Plugin;
import org.jdom.Element;

/**
 *
 * @author Roman
 */
class XMLPlugin extends Element {
    /** Creates a new instance of XMLPlugin */
    public XMLPlugin(String key, Plugin plugin) {
        super("Plugin");
        String baseClass = plugin.getClass().getCanonicalName();
        String version = plugin.getPluginVersion();
        String data = "";
        if (plugin.hasDataToSave()) {
            data = plugin.getDataToSave();
        }
        setAttribute("Key", key);
        setAttribute("BaseClass", baseClass);
        setAttribute("Version", version);
        setText(data);
    }
}
