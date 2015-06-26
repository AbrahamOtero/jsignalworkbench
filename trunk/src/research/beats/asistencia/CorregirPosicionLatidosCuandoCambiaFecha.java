package research.beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.Collection;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import javax.swing.Icon;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import java.util.*;

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
public class CorregirPosicionLatidosCuandoCambiaFecha extends AlgorithmAdapter {

    private boolean transparente = false;
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        Date fechaOriginalDeInicioDelRegistro = new Date(2000 - 1900, 1, 1, 0, 0, 0);
        Collection<Signal> allSignals = sm.getSignals();
        for (Signal signal : allSignals) {
            Collection<MarkPlugin> listaMarcas = signal.getAllMarks();
            long diferencia = signal.getStart() - fechaOriginalDeInicioDelRegistro.getTime();
            for (MarkPlugin s : listaMarcas) {
                DefaultIntervalMark marca = (DefaultIntervalMark) s;
                marca.setMarkTime(marca.getMarkTime() + diferencia);
                marca.setEndTime(marca.getEndTime() + diferencia);
            }
        }
        JSWBManager.getJSignalMonitor().repaintChannels();
    }

    public boolean hasOwnExecutionGUI() {
        return true;
    }

    public void launchExecutionGUI(JSWBManager jswbManager) {
        this.runAlgorithm(jswbManager.getSignalManager(), null, null);
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    public Icon getIcon() {
        return super.generateImage("CF");
    }

    public String getName() {
        return "Corregir la posicion cuando cambia fecha";
    }

    public String getDescription() {
        return "Corrige la posicin de los latidos al cambiar la fecha";
    }

    public String getShortDescription() {
        return "Corrige la posición de los latidos al cambiar la fecha";
    }
}
