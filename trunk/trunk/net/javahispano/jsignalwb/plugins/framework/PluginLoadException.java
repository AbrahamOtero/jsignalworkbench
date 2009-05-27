package net.javahispano.jsignalwb.plugins.framework;

/**Excepción que se lanza cuando no es posible cargar un plugin.
 *
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public class PluginLoadException extends RuntimeException {
    public PluginLoadException(String error, Exception excp) {
        super(error, excp);
    }
}
