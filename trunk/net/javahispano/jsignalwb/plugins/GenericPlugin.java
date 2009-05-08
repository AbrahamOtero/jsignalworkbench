package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * Esta interfaz añade a {@link Plugin} un método que permite ejecutar acciones
 * arbitrarias. Esta interfaz es más genérica y flexible que {@link Algorithm};
 * sus ventajas son que el framework proporciona menos apoyo a las acciones que
 * realiza; por ejemplo, no se proporciona una interfaz gráfica de ejecución por
 * defecto o se gestiona la ejecución en un thread distinto del thread de
 * gestión de eventos de Swing de las acciones contenidas en el método de esta
 * interfaz.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public interface GenericPlugin extends Plugin{

    /**
     * Lanza la ejecución del plugin.
     *
     * @param jswbManager {@link JSWBManager}
     */
    public void launch(JSWBManager jswbManager);
}
