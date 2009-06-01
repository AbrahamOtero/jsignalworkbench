package net.javahispano.jsignalwb.plugins;

/**
 * Clase adapter para {@link GenericPlugin}.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public abstract class GenericPluginAdapter extends PluginAdapter implements
        GenericPlugin {
    /**
     * Por defecto los plugins genéricos se muestran en el menú, pero no en la
     * barra de herramientas. Este comportamiento puede modificarse
     * sobreescribiendo este método.
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
