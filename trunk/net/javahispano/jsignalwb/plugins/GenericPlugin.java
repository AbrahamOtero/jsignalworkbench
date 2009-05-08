package net.javahispano.jsignalwb.plugins;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * Esta interfaz a�ade a {@link Plugin} un m�todo que permite ejecutar acciones
 * arbitrarias. Esta interfaz es m�s gen�rica y flexible que {@link Algorithm};
 * sus ventajas son que el framework proporciona menos apoyo a las acciones que
 * realiza; por ejemplo, no se proporciona una interfaz gr�fica de ejecuci�n por
 * defecto o se gestiona la ejecuci�n en un thread distinto del thread de
 * gesti�n de eventos de Swing de las acciones contenidas en el m�todo de esta
 * interfaz.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Rom�n Segador y
 *   Abraham Otero
 */
public interface GenericPlugin extends Plugin{

    /**
     * Lanza la ejecuci�n del plugin.
     *
     * @param jswbManager {@link JSWBManager}
     */
    public void launch(JSWBManager jswbManager);
}
