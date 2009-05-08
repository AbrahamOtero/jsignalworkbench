package beats;

import java.awt.HeadlessException;
import java.io.File;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.BasicSaver;
import net.javahispano.jsignalwb.plugins.*;

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
public class ExportarLatiods extends AlgorithmAdapter {
    private String ultimoDirectorioAbierto = null;
    private JFileChooser jf;
    private BasicSaver basicSaver;
    private float rr[];
    private boolean error = true;

    public ExportarLatiods() {
        basicSaver = new BasicSaver();
        jf = new JFileChooser();
    }

    public int numberOfSignalsNeeded() {
        return 1;
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
        return "Permite Generar una serie de intervalos RR a partir de anotaciones de latido";
    }

    /**
     * Devuelve un icono que ser� empleado en varios sitios de la interfaz de
     * usuario para representar al plugin.
     *
     * @return icono que representa al plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public Icon getIcon() {
        return this.generateImage("RR");
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     */
    public String getName() {
        return "ExportarLatidos";
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
        return "Genera una serie de intervalos RR";
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
        jf = new JFileChooser(ultimoDirectorioAbierto);
        jf.setFileFilter(new FileFilter() {

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                if (f.getName().toLowerCase().endsWith(".beats")) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                String desc = "Archivos RR";
                return desc;
            }
        }
        );
    }

    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        Signal signal;
        if (signals.size() != 1) {
            this.errorMensaje();
            error =  true;
            return;
        } else {
            signal = signals.get(0).getSignal();
            List<MarkPlugin> l = signal.getAllMarks();
            List<DefaultIntervalMark> beatMarks = new LinkedList();
            for (MarkPlugin mark : l) {
                if (mark instanceof DefaultIntervalMark && ((DefaultIntervalMark) mark).getTitle().equals("N")) {
                    beatMarks.add((DefaultIntervalMark) mark);
                }
            }
            if (beatMarks.size()==0) {
                this.errorMensaje();
                error =  true;
                return;
            }
            Collections.sort(beatMarks);
            rr = new float[beatMarks.size()];
            int i = 0;
            for (MarkPlugin m : beatMarks) {
                rr[i] = m.getMarkTime() - signal.getStart();
                rr[i] /= 1000;
                i++;
            }
        }
        error =  false;
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }


    public boolean hasResultsGUI() {
        return true;
    }

    public void launchResultsGUI(JSWBManager jswbManager) {
        if (error) {
            return;
        }
        if (jf.showSaveDialog(JSWBManager.getParentWindow()) ==
            JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();
            ultimoDirectorioAbierto = jf.getCurrentDirectory().getAbsolutePath();
            String n = f.getAbsolutePath();
            if (!n.endsWith(".beats")) {
                n = n.concat(".beats");
            }
            f = new File(n);
            float tmp[][] = new float[1][rr.length];
            tmp[0] = rr;
            try {
                if (!basicSaver.save(f, tmp, false)) {
                    errorGuardarMensaje();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                errorGuardarMensaje();
            }
        }
    }

    private void errorGuardarMensaje() throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                      "Error, no se pudo exportar los latidos",
                                      "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void errorMensaje() throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                      "Error, no pudo generar los RR �Se han detectado los latidos previamente?",
                                      "Error", JOptionPane.ERROR_MESSAGE);
    }

}
