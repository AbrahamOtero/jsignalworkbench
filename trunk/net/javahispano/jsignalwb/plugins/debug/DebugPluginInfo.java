/*
 * DebugPluginInfo.java
 *
 * Created on 24 de julio de 2007, 1:35
 */

package net.javahispano.jsignalwb.plugins.debug;

import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.Plugin.PluginTypes;

/**
 *
 * @author Roman Segador
 */
public class DebugPluginInfo {
    private PluginTypes pluginType;
    private String pluginName;
    private Plugin plugin;

    public DebugPluginInfo(PluginTypes pluginType, String pluginName, Plugin plugin) {
        this.plugin = plugin;
        this.pluginName = pluginName;
        this.pluginType = pluginType;
    }

    public DebugPluginInfo(String pluginType, String pluginName, Plugin plugin) {
        this.plugin = plugin;
        this.pluginName = pluginName;
        this.pluginType = getPluginType(pluginType);
    }

    public String getPluginType() {
        if (pluginType == PluginTypes.ALGORITHM) {
            return "algorithm";
        } else if (pluginType == PluginTypes.ANNOTATION) {
            return "anotation";
        } else if (pluginType == PluginTypes.GENERIC) {
            return "generic";
        } else if (pluginType == PluginTypes.GRID) {
            return "grid";
        } else if (pluginType == PluginTypes.MARK) {
            return "mark";
        } else if (pluginType == PluginTypes.LOADER) {
            return "loader";
        } else {
            return "saver";
        }

    }

    private static PluginTypes getPluginType(String p) {
        if (p.equals("algorithm")) {
            return PluginTypes.ALGORITHM;
        } else if (p.equals("anotation")) {
            return PluginTypes.ANNOTATION;
        } else if (p.equals("generic")) {
            return PluginTypes.GENERIC;
        } else if (p.equals("grid")) {
            return PluginTypes.GRID;
        } else if (p.equals("mark")) {
            return PluginTypes.MARK;
        } else if (p.equals("loader")) {
            return PluginTypes.LOADER;
        } else {
            return PluginTypes.SAVER;
        }
    }

    public String getPluginName() {
        return pluginName;
    }

    public Plugin getPlugin() {
        return plugin;
    }

}
