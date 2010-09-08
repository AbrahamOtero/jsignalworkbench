package tmp;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.JSWBManager;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import java.awt.Color;
import net.javahispano.jsignalwb.plugins.Plugin;
import javax.swing.JDialog;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.SignalNotFoundException;
import java.util.List;
import net.javahispano.jsignalwb.io.BasicSaver;
import java.io.File;

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
public class ExportarParametros extends AlgorithmAdapter {
    private boolean flujos = false, flujosDelRinhon = false, presionA = true, presionP = false,
    diuresis = true, flujosDelRinhonSuavizados = false, presionesASuavizadas = true,
    presionesPSuavizadas = false, flujosSuavizados = false;

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        if (sm.getSignal("F. carótida suavizado") != null) {
            borraInicial(sm);
        }
        if (sm.getSignal("F. carótida suavizado") == null) {
            VisualizadorDatosCerdo.generarFlujos(sm, 6000);
        }
        if (sm.getSignal("P. arterial Sist.") == null) {
            VisualizadorDatosCerdo.generarPresiones(sm, 200);
            Signal pas = sm.getSignal("P. arterial Sist.");
            pas.setValues(VisualizadorDatosCerdo.calculaMediaMovil(pas.getValues(), 30));
            pas.setFrecuency(pas.getSRate() / 30);



            Signal pad = sm.getSignal("P. arterial Diast.");
            pad.setValues(VisualizadorDatosCerdo.calculaMediaMovil(pad.getValues(), 30));
            pad.setFrecuency(pad.getSRate() / 30);

            Signal pam = sm.getSignal("P. arterial Media");
            pam.setValues(VisualizadorDatosCerdo.calculaMediaMovil(pam.getValues(), 30));
            pam.setFrecuency(pam.getSRate() / 30);

            Signal pps = sm.getSignal("P. pulmonar Sist.");
            pps.setValues(VisualizadorDatosCerdo.calculaMediaMovil(pps.getValues(), 30));
            pps.setFrecuency(pps.getSRate() / 30);

            Signal ppd = sm.getSignal("P. pulmonar Diast.");
            ppd.setValues(VisualizadorDatosCerdo.calculaMediaMovil(ppd.getValues(), 30));
            ppd.setFrecuency(ppd.getSRate() / 30);

            Signal ppm = sm.getSignal("P. pulmonar Media");
            ppm.setValues(VisualizadorDatosCerdo.calculaMediaMovil(ppm.getValues(), 30));
            ppm.setFrecuency(ppm.getSRate() / 30);
        }

        sm.hideAllSignals();
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setFrecuency(0.1F);

        borrarSenalesInutiles(sm);
        BasicSaver bs = new BasicSaver();
        Signal pas = sm.getSignal("P. arterial Sist.");
        Signal diu = sm.getSignal("Diuresis");
        long ms =  pas.getStart()-diu.getStart();
        int desplazamientoDiuresisAtras= (int)(ms*diu.getSRate()/1000);
        float[] diuresisOriginal = sm.getSignal("Diuresis").getValues();
        float[] diuresisDesplazada= new float[diuresisOriginal.length-desplazamientoDiuresisAtras];
        for (int i = 0; i < diuresisDesplazada.length; i++) {
            diuresisDesplazada [i]=diuresisOriginal [desplazamientoDiuresisAtras+i];
        }
        float[] pasDatos = pas.getValues();
        if (diuresisDesplazada.length>=pasDatos.length) {
            float [] nd = new float[pasDatos.length];
            for (int i = 0; i < pasDatos.length; i++) {
                nd[i]=diuresisDesplazada [i];
            }
            diuresisDesplazada =nd;
        }

        float[][] sd = new float[11][];
        sd[0] = diuresisDesplazada;
        sd[1] = sm.getSignal("F. carótida suavizado").getValues();
        sd[2] = sm.getSignal("F. riñón suavizado").getValues();
        sd[3] = sm.getSignal("F. corteza suavizado").getValues();
        sd[4] = sm.getSignal("F. médula suavizado").getValues();
        sd[5] = sm.getSignal("P. arterial Sist.").getValues();
        sd[6] = sm.getSignal("P. arterial Diast.").getValues();
        sd[7] = sm.getSignal("P. arterial Media").getValues();
        sd[8] = sm.getSignal("P. pulmonar Sist.").getValues();
        sd[9] = sm.getSignal("P. pulmonar Diast.").getValues();
        sd[10] = sm.getSignal("P. pulmonar Media").getValues();

        try {
          String n=  JSWBManager.getIOManager().getCurrentFileName().getName();
            bs.save(new File("C:/"+n+".txt"), sd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void borrarSenalesInutiles(SignalManager sm) throws SignalNotFoundException {
        sm.setSignalVisible("Diuresis", true);
        sm.setSignalVisible("F. carótida suavizado", true);
        sm.setSignalVisible("F. riñón suavizado", true);

        sm.removeSignal("Presión arterial");
        sm.removeSignal("Presión pulmonar");
        sm.removeSignal("Flujo carótida");
        sm.removeSignal("Flujo riñón");
        sm.removeSignal("Flujo corteza");
        sm.removeSignal("Flujo médula");

    }

    private void borraInicial(SignalManager sm) throws SignalNotFoundException {
        sm.removeSignal("P. arterial Sist.");
        sm.removeSignal("P. arterial Diast.");
        sm.removeSignal("P. arterial Media");
        sm.removeSignal("P. pulmonar Sist.");
        sm.removeSignal("P. pulmonar Diast.");
        sm.removeSignal("P. pulmonar Media");
        sm.removeSignal("F. corteza suavizado");
        sm.removeSignal("F. médula suavizado");
        sm.removeSignal("F. carótida suavizado");
        sm.removeSignal("F. riñón suavizado");
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
        return super.generateImageSimple("X", Color.blue);
    }

    public String getName() {
        return "X";
    }

    public String getDescription() {
        return "Visualizador de datos del cerdo";
    }

    public String getShortDescription() {
        return "Visualizador de datos del cerdo";
    }
}
