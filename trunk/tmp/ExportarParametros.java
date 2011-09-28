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

        //  generarSenhalesSiNoEstanYBorrarInutiles(sm);
        BasicSaver bs = new BasicSaver();
        //float[] diuresisDesplazada = ajustarPrincipiosSenhales(sm);

        /*    Signal pam = sm.getSignal("P. arterial Media");
            pam.setValues(VisualizadorDatosCerdo.calculaMediaMovil(pam.getValues(), 30));
            pam.setFrecuency(pam.getSRate() / 30);
            Signal pamm = sm.getSignal("P. pulmonar Media");
           pamm.setValues(VisualizadorDatosCerdo.calculaMediaMovil(pamm.getValues(), 30));
           pamm.setFrecuency(pamm.getSRate() / 30);

          Signal fm = sm.getSignal("F. médula suavizado");
          fm.setValues(VisualizadorDatosCerdo.calculaMediaMovil(fm.getValues(), 30));
          fm.setFrecuency(fm.getSRate() / 30);
          Signal fc = sm.getSignal("F. corteza suavizado");
          fc.setValues(VisualizadorDatosCerdo.calculaMediaMovil(fc.getValues(), 30));
          fc.setFrecuency(fc.getSRate() / 30);

          Signal fr = sm.getSignal("F. riñón suavizado");
          fr.setValues(VisualizadorDatosCerdo.calculaMediaMovil(fr.getValues(), 30));
          fr.setFrecuency(fr.getSRate() / 30);
          Signal fcr = sm.getSignal("F. carótida suavizado");
          fcr.setValues(VisualizadorDatosCerdo.calculaMediaMovil(fcr.getValues(), 30));
          fcr.setFrecuency(fcr.getSRate() / 30);

          float[] presion = sm.getSignal("P. arterial Media").getValues();
          float[] flujo = sm.getSignal("F. riñón suavizado").getValues();
          float[] resistencia = new float[pam.getValues().length];
          for (int i = 0; i < resistencia.length; i++) {
              resistencia[i] = presion[i] / flujo[i];
          }

          Signal s = new Signal("Resistencia renal", resistencia, pam.getSRate(), pam.getStart(), "");
          sm.addSignal(s);
          s.adjustVisibleRange();
          s.setVisible(true);*/

        float[][] sd = new float[32][];
        sd[0] = sm.getSignal("Diuresis").getValues();
        sd[1] = sm.getSignal("F. riñón suavizado").getValues();
        sd[2] = sm.getSignal("F. carótida suavizado").getValues();
        sd[3] = sm.getSignal("F. médula suavizado").getValues();
        sd[4] = sm.getSignal("F. corteza suavizado").getValues();
        sd[5] = sm.getSignal("P. arterial Media").getValues();
        sd[6] = sm.getSignal("P. pulmonar Media").getValues();
        sd[7] = sm.getSignal("Resistencia renal").getValues();

        sd[8] = sm.getSignal("Diuresis_Suave1_3").getValues();
        sd[9] = sm.getSignal("F. riñón suavizado_Suave1_3").getValues();
        sd[10] = sm.getSignal("F. carótida suavizado_Suave1_3").getValues();
        sd[11] = sm.getSignal("F. médula suavizado_Suave1_3").getValues();
        sd[12] = sm.getSignal("F. corteza suavizado_Suave1_3").getValues();
        sd[13] = sm.getSignal("P. arterial Media_Suave1_3").getValues();
        sd[14] = sm.getSignal("P. pulmonar Media_Suave1_3").getValues();
        sd[15] = sm.getSignal("Resistencia renal_Suave1_3").getValues();

        sd[16] = sm.getSignal("Diuresis_Suave1_5").getValues();
        sd[17] = sm.getSignal("F. riñón suavizado_Suave1_5").getValues();
        sd[18] = sm.getSignal("F. carótida suavizado_Suave1_5").getValues();
        sd[19] = sm.getSignal("F. médula suavizado_Suave1_5").getValues();
        sd[20] = sm.getSignal("F. corteza suavizado_Suave1_5").getValues();
        sd[21] = sm.getSignal("P. arterial Media_Suave1_5").getValues();
        sd[22] = sm.getSignal("P. pulmonar Media_Suave1_5").getValues();
        sd[23] = sm.getSignal("Resistencia renal_Suave1_5").getValues();

        sd[24] = sm.getSignal("Diuresis_Suave1_7").getValues();
        sd[25] = sm.getSignal("F. riñón suavizado_Suave1_7").getValues();
        sd[26] = sm.getSignal("F. carótida suavizado_Suave1_7").getValues();
        sd[27] = sm.getSignal("F. médula suavizado_Suave1_7").getValues();
        sd[28] = sm.getSignal("F. corteza suavizado_Suave1_7").getValues();
        sd[29] = sm.getSignal("P. arterial Media_Suave1_7").getValues();
        sd[30] = sm.getSignal("P. pulmonar Media_Suave1_7").getValues();
        sd[31] = sm.getSignal("Resistencia renal_Suave1_7").getValues();




        try {
            String n = JSWBManager.getIOManager().getCurrentFileName().getName();
            bs.save(new File("C:/" + n + ".txt"), sd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private float[] ajustarPrincipiosSenhales(SignalManager sm) {
        Signal pas = sm.getSignal("P. arterial Sist.");
        Signal diu = sm.getSignal("Diuresis");
        long ms = pas.getStart() - diu.getStart();
        int desplazamientoDiuresisAtras = (int) (ms * diu.getSRate() / 1000);
        float[] diuresisOriginal = sm.getSignal("Diuresis").getValues();
        float[] diuresisDesplazada = new float[diuresisOriginal.length - desplazamientoDiuresisAtras];
        for (int i = 0; i < diuresisDesplazada.length; i++) {
            diuresisDesplazada[i] = diuresisOriginal[desplazamientoDiuresisAtras + i];
        }
        float[] pasDatos = pas.getValues();
        if (diuresisDesplazada.length >= pasDatos.length) {
            float[] nd = new float[pasDatos.length];
            for (int i = 0; i < pasDatos.length; i++) {
                nd[i] = diuresisDesplazada[i];
            }
            diuresisDesplazada = nd;
        }
        return diuresisDesplazada;
    }

    private void generarSenhalesSiNoEstanYBorrarInutiles(SignalManager sm) throws SignalNotFoundException {
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
        return "Exportar parametros cerdo";
    }

    public String getDescription() {
        return "Visualizador de datos del cerdo";
    }

    public String getShortDescription() {
        return "Visualizador de datos del cerdo";
    }
}
