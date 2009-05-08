package beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import beats.Bdac;
import beats.SampleRate;
import javax.swing.Icon;
import beats.anotaciones.LatidoAnotacion;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.plugins.AlgorithmRunner;
import java.awt.Color;
import java.util.Collection;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

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
public class Paso1 extends AlgorithmAdapter {

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Collection<Signal> l = sm.getSignals();
        for (Signal s : l) {
            sm.setSignalVisible(s.getName(), false);
        }
        sm.setSignalVisible("ECG", true);
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setRepresentingXYValues(true);
        js.setMarksSelectionMode(true);
        js.setFrecuency(128);
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
        return super.generateImage("11");
    }

    public String getName() {
        return "Anotando latidos";
    }

    public String getDescription() {
        return "Anotando latidos";
    }

    public String getShortDescription() {
        return "Anotando latidos";
    }
}
