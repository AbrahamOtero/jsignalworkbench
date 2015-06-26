/*
 * FactoryPlugin.java
 *
 * Created on 8 de mayo de 2007, 12:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.util.HashMap;

/**
 *
 * @author Roman
 */
public class FactoryPlugin {
    private HashMap<String, Object> pluginAssociation;
    /** Creates a new instance of FactoryPlugin */
    public FactoryPlugin(HashMap<String, Object> pluginAssociation) {
        this.pluginAssociation = pluginAssociation;
    }

    public Object getPlugin(String name, ClassLoader classLoader) {
        Object plugin = "";
        try {
            plugin = pluginAssociation.get(name);
            if (plugin == null) {
                throw new PluginLoadException("El plugin '" + name +
                                              "' que intenta cargar no esta registrado",
                                              new RuntimeException(name));
            } else {
                if (plugin instanceof String) {
                    plugin = classLoader.loadClass((String) plugin).newInstance();
                    pluginAssociation.remove(name);
                    pluginAssociation.put(name, plugin);
                    return plugin;
                } else {
                    return plugin;
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new PluginLoadException("Class not found: \"" + plugin +
                                          "\" ", ex);
        } catch (InstantiationException ex) {
            throw new PluginLoadException("Can`t instantiate the class \"" +
                                          plugin + "\"", ex);
        } catch (IllegalAccessException ex) {
            throw new PluginLoadException("Illegal access to the class \"" +
                                          plugin + "\"", ex);
        }
    }

}
