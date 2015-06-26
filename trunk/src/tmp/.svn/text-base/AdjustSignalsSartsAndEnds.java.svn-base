package tmp;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import java.awt.Color;
import java.util.Collection;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;

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
public class AdjustSignalsSartsAndEnds extends AlgorithmAdapter {
    public String getName() {
        return "Ajustar principio y fin de las senhales";
    }

    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        Collection<Signal> allSignals = sm.getSignals();
        long maximumStart = Long.MIN_VALUE, minimumEnd = Long.MAX_VALUE;
        for (Signal s : allSignals) {
            maximumStart = maximumStart > s.getStart() ? maximumStart : s.getStart();
            long tiemeOfEnd = TimePositionConverter.positionToTime(s.getValues().length, s);
            minimumEnd = minimumEnd < tiemeOfEnd ? minimumEnd : tiemeOfEnd;
        }

        for (Signal s : allSignals) {
            if (s.getStart() < maximumStart) {
                int indexOfStart = TimePositionConverter.timeToPosition(maximumStart, s);
                float[] data = s.getValues();
                float[] newData = new float[data.length - indexOfStart];
                for (int i = indexOfStart; i < data.length; i++) {
                    newData[i - indexOfStart] = data[i];
                }
                s.setValues(newData);
                s.setStart(maximumStart);
            }
        }

        for (Signal s : allSignals) {
            long tiemeOfEnd = TimePositionConverter.positionToTime(s.getValues().length, s);
            if (tiemeOfEnd > minimumEnd) {
                int indexOfEnd = TimePositionConverter.timeToPosition(minimumEnd, s);
                float[] data = s.getValues();
                float[] newData = new float[indexOfEnd];
                for (int i = 0; i < indexOfEnd; i++) {
                    newData[i] = data[i];
                }
                s.setValues(newData);
                s.setStart(maximumStart);
            }
        }

    }


    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }

    public Icon getIcon() {
        return super.generateImageSimple("SE", Color.blue);
    }

}
