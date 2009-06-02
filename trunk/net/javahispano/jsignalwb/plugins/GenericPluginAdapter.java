package net.javahispano.jsignalwb.plugins;

/**
 * Clase adapter para {@link GenericPlugin}.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public abstract class GenericPluginAdapter extends PluginAdapter implements
        GenericPlugin {
    /**
     * Por defecto los plugins genericos se muestran en el menu, pero no en la
     * barra de herramientas. Este comportamiento puede modificarse
     * sobreescribiendo este metodo.
     *
     * @param gUIPositions GUIPositions
     * @return boolean
     */
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }

}
