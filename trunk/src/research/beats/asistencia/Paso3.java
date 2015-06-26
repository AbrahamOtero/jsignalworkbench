package research.beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.Collection;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
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
public class Paso3  extends AlgorithmAdapter {

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Collection<Signal> l = sm.getSignals();
        for (Signal s : l) {
            sm.setSignalVisible(s.getName(), false);
        }
        sm.renameSignal("Sat0III","Sat02");
        sm.renameSignal("Movimientoabdominal","Movimiento abdominal");
        sm.renameSignal("Movimientotoracico","Movimiento toracico");

        sm.setSignalVisible("Flujo",true);
                sm.setSignalVisible("Sat02",true);
                sm.setSignalVisible("Movimiento abdominal",true);
        sm.setSignalVisible("Movimiento toracico",true);

        sm.setSignalVisibleRange("Sat02", 80, 100);
        sm.setSignalVisibleRange("Flujo", -20,20);
        Signal sat = sm.getSignal("Sat02");
        sat.getProperties().setInvadeNearChannels(false);
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setFrecuency(2);
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
        return super.generateImage("33");
    }

    public String getName() {
        return "As";
    }

    public String getDescription() {
        return "As";
    }

    public String getShortDescription() {
        return "As";
    }
}
