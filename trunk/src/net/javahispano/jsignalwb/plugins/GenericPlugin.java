package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * Esta interfaz anhade a {@link Plugin} un metodo que permite ejecutar acciones
 * arbitrarias. Esta interfaz es mas generica y flexible que {@link Algorithm};
 * sus ventajas son que el framework proporciona menos apoyo a las acciones que
 * realiza; por ejemplo, no se proporciona una interfaz grafica de ejecucion por
 * defecto o se gestiona la ejecucion en un thread distinto del thread de
 * gestion de eventos de Swing de las acciones contenidas en el metodo de esta
 * interfaz.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface GenericPlugin extends Plugin {

    /**
     * Lanza la ejecucion del plugin.
     *
     * @param jswbManager {@link JSWBManager}
     */
    public void launch(JSWBManager jswbManager);
}
