package net.javahispano.jsignalwb.plugins;

import java.io.File;

import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * <p>Interfaz que representa cualquier plugin dentro de JSignalWorkbench.
 * No se recomienda Implementar directamente esta interfaz; suele ser
 * preferible extender la clase {@link PluginAdapter}. </p>
 *
 * <p> Esta interfaz, ademas de un conjunto de metodos de utilidad, solo
 * permite al plugin lanzar una interfaz grafica que deberia emplearse
 * unicamente para configurar el plugin. Si se desea crear un plugin generico
 * que tenga cierta capacidad para ejecutar ciertas acciones se recomienda
 * emplear la clase {@link GenericPlugin}.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
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
     * Devuelve una de extincion textual corta sobre la funcionalidad del
     * plugin. Sera empleada en varios sitios de la interfaz de usuario para
     * presentar una ayuda al usuario de la aplicacion.
     *
     * @return descripcion textual corta
     */
    public String getShortDescription();

    /**
     * Devuelve una decision textual mas amplia de la funcionalidad del plugin.
     * Se emplean varios sitios en la interfaz de usuario para presentar una
     * ayuda al usuario.
     *
     * @return descripcion textual larga
     */
    public String getDescription();

    /**
     * Devuelve la version del plugin. La version puede ser cualquier cadena de
     * caracteres. Se emplea para realizar un control de versiones de los
     * plugings.
     *
     * @return Version del plugin
     */
    public String getPluginVersion();

    /**
     * Devuelve un icono que sera empleado en varios sitios de la interfaz de
     * usuario para representar al plugin.
     *
     * @return icono que representa al plugin
     */
    public Icon getIcon();

    /**
     * Indica si el plugin tiene una interfaz grafica propia para configurarse.
     *
     * @return devuelve cierto si tiene interfaz y falso en caso contrario
     */
    public boolean hasOwnConfigureGUI();

    /** Lanza la interfaz de configuracion del plugin*/
    public void launchConfigureGUI(JSWBManager jswbManager);

    /**
     * Proporciona datos de configuracion del algoritmo que deben de ser
     * guardados por el entorno.
     *
     * @return cadena de caracteres con los datos que debe guardar el entorno.
     *   Si el entorno no debe de guardar ninguna informacion sobre el plugin
     *   el valor de retorno sera null.
     */
    public String getDataToSave();

    /**
     * Este metodo es invocado por el entorno al cargar el plugin para pasarle
     * una cadena de caracteres con los datos que el plugin le pidio que
     * almacenarse en la ultima ejecucion.
     *
     * @param data datos que el plugin  pidio al entorno que almacenarse en la
     *   ultima ejecucion
     */
    public void setSavedData(String data);

    /**
     * Devuelve cierto si el plugin tiene datos se desea que el entorno guarde y
     * le devuelva la proxima vez que se ejecute la herramienta.
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
     * indica si el plugin desea aparecer, representado a traves de un {@link Action}, en distintas partes de la
     * interfaz de usuario. Las opciones son las dadas por la enumeracion {@link GUIPositions}
     *
     * @param gUIPositions GUIPositions
     * @return boolean
     */
    public boolean showInGUIOnthe(GUIPositions gUIPositions);

    /**
     * Representa las distintas partes de la interfaz de usuario donde un plugin puede quedar registrado a traves de un
     * objeto {@link Action}. Un plugin puede aparecer en el menu de plugins,
     * y/o en la barra de tareas de la herramienta.
     */
    public enum GUIPositions {
        MENU, TOOLBAR} ;

        /**
         * Enumeracion que representa los distintos tipos de plugin soportados
         * por JSignalWorkbench.
         */
        public enum PluginTypes {
            ALGORITHM, GRID, MARK, ANNOTATION, GENERIC, LOADER, SAVER} ;

        }
