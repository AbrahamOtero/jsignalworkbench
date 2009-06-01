package net.javahispano.jsignalwb.plugins;

import java.io.File;
import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * <p>Interfaz que representa cualquier plugin dentro de JSignalWorkbench.
 * No se recomienda Implementar directamente esta interfaz; suele ser
 * preferible extender la clase {@link PluginAdapter}. </p>
 *
 * <p> Esta interfaz, adem�s de un conjunto de m�todos de utilidad, s�lo
 * permite al plugin lanzar una interfaz gr�fica que deber�a emplearse
 * �nicamente para configurar el plugin. Si se desea crear un plugin gen�rico
 * que tenga cierta capacidad para ejecutar ciertas acciones se recomienda
 * emplear la clase {@link GenericPlugin}.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Rom�n Segador y
 *   Abraham Otero
 */
public interface Plugin {
    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     */
    public String getName();

    /**
     * Devuelve una de extinci�n textual corta sobre la funcionalidad del
     * plugin. Ser� empleada en varios sitios de la interfaz de usuario para
     * presentar una ayuda al usuario de la aplicaci�n.
     *
     * @return descripci�n textual corta
     */
    public String getShortDescription();

    /**
     * Devuelve una decisi�n textual m�s amplia de la funcionalidad del plugin.
     * Se emplean varios sitios en la interfaz de usuario para presentar una
     * ayuda al usuario.
     *
     * @return descripci�n textual larga
     */
    public String getDescription();

    /**
     * Devuelve la versi�n del plugin. La versi�n puede ser cualquier cadena de
     * caracteres. Se emplea para realizar un control de versiones de los
     * plugings.
     *
     * @return Versi�n del plugin
     */
    public String getPluginVersion();

    /**
     * Devuelve un icono que ser� empleado en varios sitios de la interfaz de
     * usuario para representar al plugin.
     *
     * @return icono que representa al plugin
     */
    public Icon getIcon();

    /**
     * Indica si el plugin tiene una interfaz gr�fica propia para configurarse.
     *
     * @return devuelve cierto si tiene interfaz y falso en caso contrario
     */
    public boolean hasOwnConfigureGUI();

    /** Lanza la interfaz de configuraci�n del plugin*/
    public void launchConfigureGUI(JSWBManager jswbManager);

    /**
     * Proporciona datos de configuraci�n del algoritmo que deben de ser
     * guardados por el entorno.
     *
     * @return cadena de caracteres con los datos que debe guardar el entorno.
     *   Si el entorno no debe de guardar ninguna informaci�n sobre el plugin
     *   el valor de retorno ser� null.
     */
    public String getDataToSave();

    /**
     * Este m�todo es invocado por el entorno al cargar el plugin para pasarle
     * una cadena de caracteres con los datos que el plugin le pidi� que
     * almacenarse en la �ltima ejecuci�n.
     *
     * @param data datos que el plugin  pidi� al entorno que almacenarse en la
     *   �ltima ejecuci�n
     */
    public void setSavedData(String data);

    /**
     * Devuelve cierto si el plugin tiene datos se desea que el entorno guarde y
     * le devuelva la pr�xima vez que se ejecute la herramienta.
     *
     * @return ciertos y el entorno debe guardar de datos, falso en caso
     *   contrario.
     */
    public boolean hasDataToSave();

    /**
     * Indica si el plugin desea trabajar con un fichero externo
     * @return true si desea trabajar con un fichero. false en caso contrario.
     */
    public boolean createFile();

    /** Recibe un archivo {@link File} sobre el cual podra trabajar. Este File
     *  debe ser el mismo que devuelva al llamar al metodo getFile
     *  @param {@link File} fichero sobre el cual puede trabajar el plugin.
     */

    public void setFile(File file);

    /**
      * indica si el plugin desea aparecer, representado a trav�s de un {@link Action}, en distintas partes de la
      * interfaz de usuario. Las opciones son las dadas por la enumeracion {@link GUIPositions}
      *
      * @param gUIPositions GUIPositions
      * @return boolean
      */
       public boolean showInGUIOnthe(GUIPositions gUIPositions);

    /**
     * Representa las distintas partes de la interfaz de usuario donde un plugin puede quedar registrado a traves de un
     * objeto {@link Action}. Un plugin puede aparecer en el men� de plugins,
     * y/o en la barra de tareas de la herramienta.
     */
    public enum GUIPositions {
        MENU, TOOLBAR} ;

        /**
         * Enumeraci�n que representa los distintos tipos de plugin soportados
         * por JSignalWorkbench.
         */
        public enum PluginTypes {
            ALGORITHM, GRID, MARK, ANNOTATION, GENERIC, LOADER, SAVER} ;

        }
