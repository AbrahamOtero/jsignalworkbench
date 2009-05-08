package es.usc.gsi.conversorDatosMIT;

import javax.swing.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.*;
import java.util.ArrayList;
import java.io.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class ConversorDatosMITPlugin extends PluginAdapter implements GenericPlugin {
    String ultimoDirectorioAbierto = null;

    public ConversorDatosMITPlugin() {
    }



    /**
     * Proporciona datos de configuraci�n del algoritmo que deben de ser
     * guardados por el entorno.
     *
     * @return cadena de caracteres con los datos que debe guardar el
     *   entorno. Si el entorno no debe de guardar ninguna informaci�n sobre
     *   el plugin el valor de retorno ser� null.
     */
    public String getDataToSave() {
        return ultimoDirectorioAbierto;
    }

    /**
     * Devuelve una decisi�n textual m�s amplia de la funcionalidad del
     * plugin.
     *
     * @return descripci�n textual larga
     */
    public String getDescription() {
        return "Permite importar se�ales de registros MIT-BIH. Importa nombre de se�ales, frecuencias de muestreo, fecha de comienzo del registro, etc.";
    }

    /**
     * Devuelve un icono que ser� empleado en varios sitios de la interfaz de
     * usuario para representar al plugin.
     *
     * @return icono que representa al plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("MIT.gif"));
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     */
    public String getName() {
        return "Conversor datos MIT-BIH";
    }

    /**
     * Devuelve la versi�n del plugin.
     *
     * @return Versi�n del plugin
     */
    public String getPluginVersion() {
        return "0.5";
    }

    /**
     * Devuelve una de extinci�n textual corta sobre la funcionalidad del
     * plugin.
     *
     * @return descripci�n textual corta
     */
    public String getShortDescription() {
        return "Importa registros MIT-BIH";
    }

    /**
     * Devuelve ciertos y el plugin tiene datos se desea que el entorno
     * guarde y le devuelva la pr�xima vez que se ejecute la herramienta.
     *
     * @return ciertos y el entorno debe guardar de datos, falso en caso
     *   contrario.
     */
    public boolean hasDataToSave() {
        return true;
    }

    /**
     * Indica si el plugin tiene una interfaz gr�fica propia para
     * configurarse.
     *
     * @return devuelve cierto si tiene interfaz y falso en caso contrario
     */
    public boolean hasOwnConfigureGUI() {
        return false;
    }



    /**
     * Este m�todo es invocado por el entorno al cargar el plugin para
     * pasarle una cadena de caracteres con los datos que el plugin le pidi�
     * que almacenarse en la �ltima ejecuci�n.
     *
     * @param data datos que el plugin pidi� al entorno que almacenarse en
     *   la �ltima ejecuci�n
     */
    public void setSavedData(String data) {
        ultimoDirectorioAbierto = data;
    }


    public void launch(JSWBManager jswbManager) {
        FrameConversorMIT f = new FrameConversorMIT(jswbManager.getParentWindow(),jswbManager);
        if (this.ultimoDirectorioAbierto != null) {

            File directorioViejo = new File(this.ultimoDirectorioAbierto);
            if (directorioViejo.exists()) {
                f.setArchivoAbierto(directorioViejo);
            }
        }
        f.setVisible(true);
        //obtenemos el ltimo directorio
        File directorio =f.getArchivoAbierto();
        if (directorio != null) {
            this.ultimoDirectorioAbierto = directorio.getAbsolutePath();
        }

    }
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
    if (gUIPositions == GUIPositions.MENU) {
        return true;
    } else if (gUIPositions == GUIPositions.TOOLBAR) {
        return true;
    }
    return false;
}

}
