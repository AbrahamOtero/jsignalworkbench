package net.javahispano.jsignalwb.io;

import java.io.File;
import java.io.IOException;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;
import net.javahispano.jsignalwb.ui.LoaderExecutionJDialog;
import net.javahispano.jsignalwb.ui.SaverExecutionJDialog;

/**
 * Fachada para realizar las operaciones de entrada y salida. Para funcionar
 * correctamente requiere un {@link PluginManager}, para obtener los plugins en los cuales
 * delegara la carga y el almacenamiento de los datos, y {@link SignalManager}, para poder
 * modificar las senhales y el resto de la informacion de la sesion de trabajo.
 */
public class IOManager {
    private PluginManager pm;
    private SignalManager sm;
    private File currentFile = null;

    /**
     * Crea una instancia nueva de IOManager.
     *
     * @param sm {@link SignalManager}.
     * @param pm {@link PluginManager}.
     * @todo  arreglar dependencia
     */
    public IOManager(JSWBManager jswbManager) {
        pm = jswbManager.getPluginManager();
        sm = jswbManager.getSignalManager();
    }


    /**
     * <p>Carga en el sistema las senales contenidas en path, descargando todas
     * aquellas que ya estuviesen cargadas en el sistema si asi se le indica.</p>
     *
     * <p> Precondiciones -->
     * path debe contener la ruta relativa correcta de un fichero que contenga
     * los datos de las senales a cargar. </p>
     *
     * <p> Postcondiciones --> Devolvera true si
     * las senales son cargadas correctamente y false en caso contrario. </p>
     *
     * @param path direccion del archivo cargar.
     * @param nombre del {@link Loader}a emplear.
     * @param clear true si se desea borrar todas las senhales existentes antes
     * de realizar la carga, false en caso contrario.
     * @throws Exception
     * @return true si la carga se realiza correctamente, false en caso contrario.
     */
    public boolean loadSignals(String path, String loaderName, boolean clear) throws Exception {
        return loadSignals(new File(path), loaderName, clear);
    }

    /**
     * <p>Carga en el sistema las senales contenidas en path, descargando todas
     * aquellas que ya estuviesen cargadas en el sistema.</p>
     *
     * <p> Precondiciones -->
     * path debe contener la ruta relativa correcta de un fichero que contenga
     * los datos de las senales a cargar. </p>
     *
     * <p> Postcondiciones --> Devolvera true si
     * las senales son cargadas correctamente y false en caso contrario. </p>
     *
     * @param f archivo cargar.
     * @param nombre del {@link Loader}a emplear.
     * @param clear true si se desea borrar todas las senhales existentes antes
     * de realizar la carga, false en caso contrario.
     * @throws Exception
     * @return true si la carga se realiza correctamente, false en caso contrario.
     */
    public boolean loadSignals(File f, String loaderName, boolean clear) throws
            Exception {
        Loader loader;
        loader = pm.getLoader(loaderName);
        currentFile = f;
        if (clear) {
            sm.removeAllSignals();
        }
        new LoaderExecutionJDialog(loader, f);
        return true;
    }

    /**
     * Salva los datos cargados en el sistema en un destino cuyo nombre sera
     * path y cuyo formato vendra determinado por el saver correspondiente, los
     * almacenara en la ubicacion indicada en absolutePath, con el nombre
     * indicado en path(y path.xml para el archivo xml)
     *
     * @param path direccion del archivo.
     * @param saverName nombre del {@link Saver a emplear}
     * @throws Exception
     */
    public void saveSignals(String path, String saverName) throws Exception {
        saveSignals(new File(path), saverName);
    }

    /**
     * Salva los datos cargados en el sistema en un destino cuyo nombre sera
     * path y cuyo formato vendra determinado por el saver correspondiente, los
     * almacenara en la ubicacion indicada en absolutePath, con el nombre
     * indicado en path(y path.xml para el archivo xml)
     *
     * @param path direccion del archivo.
     * @param saverName nombre del {@link Saver a emplear}
     * @throws Exception
     */
    public void saveSignals(File f, String saverName) throws Exception {
        Saver saver;
        if (sm.getSignalsSize() > 0) {
            if (pm.isPluginRegistered("saver", saverName)) {
                saver = pm.getSaver(saverName);
                if (saver != null) {
                    // saver.save(f, jswbManager);
                    new SaverExecutionJDialog(saver, f);
                }
            }
        }
        currentFile = f;
    }

    /**
     * Devuelve el archivo con el cual se esta trabajando en este momento.
     *
     * @return File
     */
    public File getCurrentFileName() {
        return currentFile;
    }

    /**
     * Permite a un plugin solicitar que se le cree un archivo con el cual pueda trabajar.
     * En JSignalWorkbench se instala un nuevo plugin si la herramienta es empleada para abrir un archivo que no fue
     * guardado con una instancia de JSignalWorkbench que tuviese instalado dicho plugin, JSignalWorkbench no llamara al
     * plugin para inicializarlo. Estos plugin deben gestionar su propia inicializacion implementando la interfaz {@link
     * SessionListener}; en caso de que para dicha inicializacion requieran de un archivo deberan emplear este metodo
     * para obtenerlo.
     *
     * @param p Plugin que solicita la creacion del archivo.
     * @return File te ha creado el metodo.
     * @throws SessionNotSavedException Si se invoca este metodo sin que la sesion de trabajo este guardada se lanzara
     *   esta excepcion.
     * @throws IOException Si, por cualquier motivo, no se puede crear el chivo se lanzara esta excepcion.
     */
    public File createNameForPlugin(Plugin p) throws SessionNotSavedException, IOException {
        if (currentFile == null) {
            throw new SessionNotSavedException("Session not saved");
        }
        File f = new File(currentFile.getAbsolutePath(), (p.getName() + ".jpc").trim());
        f.createNewFile();
        return f;
    }

    /**
     * Indica que, en caso de estarse trabajando sobre un archivo, dicho archivo ha pasado a ser invalido. La clase se
     * olvida de su existencia y pasa a no tener ningun archivo de trabajo asociado.
     */
    public void invalidateFile() {
        currentFile = null;
    }

}
