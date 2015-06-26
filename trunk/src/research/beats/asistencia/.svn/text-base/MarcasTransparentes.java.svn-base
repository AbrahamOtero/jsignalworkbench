package research.beats.asistencia;

import java.util.Collection;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
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
public class MarcasTransparentes extends AlgorithmAdapter {
    private boolean transparente = false;
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
       Collection<Signal> allSignals = sm.getSignals();
        for (Signal signal : allSignals) {
            Collection<MarkPlugin> listaMarcas = signal.getAllMarks();
            for (MarkPlugin s : listaMarcas) {
                DefaultIntervalMark marca = (DefaultIntervalMark) s;
                if (!transparente) {
                    marca.setInnerTransparencyLevel(0);
                    marca.setBorderTransparencyLevel(0);
                } else {
                    marca.setInnerTransparencyLevel(50);
                    marca.setBorderTransparencyLevel(150);
                }
            }
        }

        transparente = !transparente;
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
        return super.generateImage("IN");
    }

    public String getName() {
        return "MarcasTransparentes";
    }

    public String getDescription() {
        return "MarcasTransparentes";
    }

    public String getShortDescription() {
        return "MarcasTransparentes";
    }
}
