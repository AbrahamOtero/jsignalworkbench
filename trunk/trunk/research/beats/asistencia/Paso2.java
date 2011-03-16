package research.beats.asistencia;

import java.util.Collection;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

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
public class Paso2 extends AlgorithmAdapter {

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Collection<Signal> l = sm.getSignals();
        for (Signal s : l) {
            sm.setSignalVisible(s.getName(), false);
        }
        sm.setSignalVisible("Flujo", true);
        sm.setSignalVisible("Sat02", true);
        sm.setSignalVisibleRange("Sat02", 80, 100);
        Signal sat = sm.getSignal("Sat02");
        sat.getProperties().setInvadeNearChannels(false);
        float[] d = sat.getValues();
        for (int i = 1; i < d.length; i++) {
            if (d[i] >= 103) {
                d[i] = d[i - 1];
            }
        }
//        sm.setSignalVisible("ULF", true);
  //      sm.setSignalVisible("VLF", true);
    //    sm.setSignalVisible("LF", true);
      //  sm.setSignalVisible("HF", true);
        //sm.setSignalVisible("LF/HF", true);
        //sm.setSignalVisible("HRV", true);
        sm.setSignalVisible("Movimiento abdominal", true);
       sm.setSignalVisible("Movimiento toracico", true);
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setRepresentingXYValues(true);
        js.setMarksSelectionMode(true);
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
        return super.generateImage("22");
    }

    public String getName() {
        return "Anotando eventos respiratorios";
    }

    public String getDescription() {
        return "Anotando eventos respiratorios";
    }

    public String getShortDescription() {
        return "Anotando eventos respiratorios";
    }
}
