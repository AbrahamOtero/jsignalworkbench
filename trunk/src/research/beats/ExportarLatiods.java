package research.beats;

import java.awt.HeadlessException;
import java.io.File;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.BasicSaver;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

public class ExportarLatiods extends AlgorithmAdapter {

    private String ultimoDirectorioAbierto = null;
    private JFileChooser jf;
    private BasicSaver basicSaver;
    private float rr[];
    private boolean error = true;
    private boolean exportRHRV = true;

    public ExportarLatiods() {
        basicSaver = new BasicSaver();
        jf = new JFileChooser(ultimoDirectorioAbierto);
    }

    public int numberOfSignalsNeeded() {
        return 0;
    }

    public String getDataToSave() {
        return ultimoDirectorioAbierto;
    }

    public String getDescription() {
        return "Permite Generar una serie de intervalos RR a partir de anotaciones de latido";
    }

    public Icon getIcon() {
        return this.generateImage("RR");
    }

    public String getName() {
        return "ExportarLatidos";
    }

    public String getPluginVersion() {
        return "0.5";
    }

    public String getShortDescription() {
        return "Genera una serie de intervalos RR";
    }

    public boolean hasDataToSave() {
        return true;
    }

    public boolean hasOwnConfigureGUI() {
        return false;
    }

    public void setSavedData(String data) {
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
        });
    }

    public void runAlgorithm(SignalManager sm,
            List<SignalIntervalProperties> signals, AlgorithmRunner ar) {
        Signal signal;
        List<MarkPlugin> l;
        SignalIntervalProperties interval = signals.get(0);
        signal = interval.getSignal();
        if (signals.get(0).isFullSignal()) {
            l = signal.getAllMarks();
        } else {
            l = new LinkedList<MarkPlugin>();
            for (SignalIntervalProperties e : signals) {
                List<MarkPlugin> kk = signal.getMarks(e.getStartTime(), e.getEndTime());
                l.addAll(kk);
            }
        }
        List<DefaultIntervalMark> beatMarks = new LinkedList();
        for (MarkPlugin mark : l) {
            if (mark instanceof DefaultIntervalMark && ((DefaultIntervalMark) mark).getComentary().equals("0")) {
                beatMarks.add((DefaultIntervalMark) mark);
            }
        }
        if (beatMarks.size() == 0) {
            this.errorMensaje();
            error = true;
            return;
        }
        Collections.sort(beatMarks);
        generateRR(signal, beatMarks);
        error = false;
    }

    private void generateRR(Signal signal, List<DefaultIntervalMark> beatMarks) {
        rr = new float[beatMarks.size()];
        boolean useMax = decideOnMaxMin(beatMarks, signal);
        int i = 0;
        for (MarkPlugin m : beatMarks) {
            long refinedRR = ajustarPrincipios(m, useMax, signal);
            rr[i] = refinedRR - signal.getStart();
            if (Math.random()>0.98) rr[i]+=60;
            rr[i] /= 1000;
            i++;
        }
        if (!this.exportRHRV) {
            for (int j = rr.length - 1; j > 0; j--) {
                rr[j] = rr[j] - rr[j - 1];
            }
            float[] nrr = new float[rr.length - 1];
            for (int j = 0; j < nrr.length; j++) {
                nrr[j] = rr[j + 1];
            }
            rr = nrr;
        }
        //@chapuza
      /*  else{
         for (int j = 0; j < rr.length; j++) {
         rr[j] /= 2F;
         }

         }*/
    }

    private boolean decideOnMaxMin(List<DefaultIntervalMark> beatMarks, Signal e) {
        int counter = 0;
        float[] ec = e.getValues();
        float min = 0, max = 0;
        for (MarkPlugin m : beatMarks) {
            int beging = TimePositionConverter.timeToPosition(m.getMarkTime(), e);
            int end = TimePositionConverter.timeToPosition(m.getEndTime(), e);
            float minValue = Float.POSITIVE_INFINITY;
            float maxValue = Float.NEGATIVE_INFINITY;
            if (beging < 0) {
                continue;
            }
            for (int j = beging; j < end; j++) {
                if (ec[j] < minValue) {
                    minValue = ec[j];
                }
                if (ec[j] > maxValue) {
                    maxValue = ec[j];
                }
            }
            min += minValue;
            max += maxValue;
            counter++;
            if (counter > 1000) {
                break;
            }
        }
        if (Math.abs(min) > max) {
            return false;
        } else {
            return true;
        }
    }

    private long ajustarPrincipios(MarkPlugin m, boolean useMax, Signal e) {
        float[] ec = e.getValues();
        int begining = TimePositionConverter.timeToPosition(m.getMarkTime(), e);
        int end = TimePositionConverter.timeToPosition(m.getEndTime(), e);
        int selectedIndex = 0;
        if (useMax) {
            float mv = Float.NEGATIVE_INFINITY;
            for (int i = begining; i < end; i++) {
                if (ec[i] > mv) {
                    mv = ec[i];
                    selectedIndex = i;
                }
            }
        } else {
            float mv = Float.POSITIVE_INFINITY;
            for (int i = begining; i < end; i++) {
                if (ec[i] < mv) {
                    mv = ec[i];
                    selectedIndex = i;
                }
            }
        }

        long rrInterval = TimePositionConverter.positionToTime(selectedIndex, e);
        return rrInterval;
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
        if (ultimoDirectorioAbierto != null) {
            jf.setCurrentDirectory(new File(ultimoDirectorioAbierto));
        }
        if (jf.showSaveDialog(JSWBManager.getParentWindow())
                == JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();
            ultimoDirectorioAbierto = jf.getCurrentDirectory().getAbsolutePath();
            System.out.println(ultimoDirectorioAbierto);
            String n = f.getAbsolutePath();
            if (this.exportRHRV) {
                if (!n.endsWith(".beats")) {
                    n = n.concat(".beats");
                }

            } else {
                if (!n.endsWith(".dat")) {
                    n = n.concat(".dat");
                }
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

    /**
     * Por defecto no proporciona interfaz propia de ejecucion.
     *
     * @return boolean
     */
    public boolean hasOwnExecutionGUI() {
        return true;
    }

    public void launchExecutionGUI(JSWBManager jswbManager) {
        DialogKubiosRHRV d = new DialogKubiosRHRV(null, "Seleciona Formato", true);
        d.setVisible(true);
        exportRHRV = d.isExportRHRV();
        super.launchExecutionGUI(jswbManager);

    }

    private void errorGuardarMensaje() throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                "Error, no se pudo exportar los latidos",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void errorMensaje() throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                "Error, no pudo generar los RR ?Se han detectado los latidos previamente?",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
