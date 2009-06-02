package research.beats;

import java.awt.Color;
import static java.lang.Math.*;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import research.beats.anotaciones.LatidoAnotacion;

/**
 *
 * @author Santiago Fernandez Dapena
 */
public class TestDeteccion extends AlgorithmAdapter {

    private Bdac bdac;
    private long sampleCount = 0;
    private int delay;
    private float[] ecg = null;

    public TestDeteccion() {
        bdac = new Bdac();
    }

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        //Asignar a SampleRate la frecuencia de la senhal actual
        SampleRate.setSampleRate((int) signals.get(0).getSignal().getSRate());

        // Initialize beat detection
        bdac.resetBdac();
        sampleCount = 0;
        ecg = signals.get(0).getSignal().getValues();
        long detectionTimeR;

        for (int i = 0; i < ecg.length - 1; i++) {
            ++sampleCount;
            // Pass sample to beat detection and classification.
            delay = bdac.beatDetect((int) ecg[i], i);

            // If a beat was detected, annotate the beat location
            // and type.
            if (delay != 0) {
                detectionTimeR = sampleCount - delay;
                generarMarcas(signals.get(0).getSignal(), detectionTimeR,
                              Color.GREEN);
            }
        }
    }


    public static void generarMarcas(Signal s, long posicion, Color color) {

        LatidoAnotacion m = new LatidoAnotacion();
        // m.setTitle("Latidos" + (s.getStart() + (SampleRate.getMsPerSample() * posicion)));
        m.setTitle("N");
        final long time = (long) (s.getStart() + (SampleRate.getMsPerSample() * (posicion - 4)));
        m.setMarkTime(time);
        m.setEndTime(s.getStart() + (int) ((SampleRate.getMsPerSample() * (posicion + 4))));
        m.setColor(Color.RED);
        m.setAutomatica(true);
        s.addMark(m);
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
        return new javax.swing.ImageIcon(getClass().getResource("hearth.gif"));
    }

    public String getName() {
        return "Detector de latidos";
    }

    public int numberOfSignalsNeeded() {
        return 1;
    }

    public String getDescription() {
        return "Un simple detector de latidos";
    }

    public String getPluginVersion() {
        return "0.5";
    }

    public String getShortDescription() {
        return "Un simple detector de latidos";
    }
}
