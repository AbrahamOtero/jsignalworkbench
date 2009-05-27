package beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.Collection;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.plugins.AlgorithmRunner;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.DefaultIntervalMark;

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
        Collection<MarkPlugin> listaMarcas = sm.getAllSignalMarks("ECG");
        for (MarkPlugin s : listaMarcas) {
            DefaultIntervalMark marca = (DefaultIntervalMark)s;
            if (!transparente) {
                marca.setInnerTransparencyLevel(0);
            marca.setBorderTransparencyLevel(0);
            } else {
                marca.setInnerTransparencyLevel(50);
            marca.setBorderTransparencyLevel(150);
            }
        }
        transparente=!transparente;
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
