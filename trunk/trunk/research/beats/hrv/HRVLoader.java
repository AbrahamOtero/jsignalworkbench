package research.beats.hrv;

import java.io.File;
import java.util.ArrayList;
import java.util.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.BasicLoader;
import net.javahispano.jsignalwb.plugins.MarkPlugin;

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
public class HRVLoader extends BasicLoader {
     private String[] nombres = {"ULF", "VLF", "LF", "HF", "LF/HF", "HRV","FC"};
    //  private String[] nombres = {"Diuresis","F. riñón","F. carótida","P. arterial",
      // "P. pulmonar","F. corteza","F. médula","F. corteza+médula"};
   //  private String[] nombres = {"Presión arterial", "Flujo carótida"};

    // private String[] nombres = {"I", "II", "III"};



    public String getName() {
        return "HRVLoader";
    }

    public String getShortDescription() {
        return "HRV Loader";
    }

    /**
     * La extension que soporta es "txt".
     *
     * @return ArrayList
     */
    public ArrayList<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("hrv");
        return ext;
    }

    protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
        float fs = 250;//obtenerFS(f);
        Object[] signalArray= sm.getSignals().toArray();
        Date baseDate;
        if (signalArray.length>0) {
            baseDate = new Date(((Signal)signalArray [0]).getStart());
        } else {
             baseDate = new Date(100, 1, 1, 0, 0, 0);
        }

        super.load(f, sm);
        int numberOfSignals = sm.getSignalsSize();
        for (int i = 0; i < numberOfSignals; i++) {
            Signal s = sm.getSignal("Signal" + i);
            if (s == null) {
                return false;
            }
            Signal newSignal = new Signal(nombres[i], corrigeInicioYFin(f,s,sm));
            newSignal.setFrecuency(fs);
            newSignal.setStart(baseDate.getTime());
            //alinearConECG(sm,newSignal,s);
            sm.removeSignal(s.getName());
            sm.addSignal(newSignal);
            newSignal.adjustVisibleRange();
        }
        JSWBManager jsw = JSWBManager.getJSWBManagerInstance();
        if (jsw.isDeleteSignalsInNextLoad()) {
            jsw.setJSMFrecuency(fs);
        }
        return flag;
    }

    private void alinearConECG(SignalManager sm, Signal newSignal, Signal oldSignal) {
        Signal ecg = sm.getSignal("ECG");
        if (ecg == null) {//no hay ECG; estamos abriendo directamente un registro hrv y no añadiendo
            return;
        }
        List<MarkPlugin> beats =ecg.getAllMarks();
        MarkPlugin primerLatido = buscarPrimerLatido(beats);

        long posicionPrimerLatido = primerLatido.getMarkTime();
        posicionPrimerLatido += (primerLatido.getEndTime()-primerLatido.getMarkTime())/2;
        long origenOldSignal = oldSignal.getStart();
        float desplazamientoEnSegundos = (posicionPrimerLatido-origenOldSignal)/(1000F);
        int desplazamientoEnMuestras = Math.round (desplazamientoEnSegundos * newSignal.getSRate());


        float[] oldValues = newSignal.getValues();
        float[] newValues = new float[oldValues.length+desplazamientoEnMuestras];
        int i =0;
        for (; i < desplazamientoEnMuestras; i++) {
            newValues[i]=0;
        }
        for (i=0; i < oldValues.length; i++) {
            newValues[desplazamientoEnMuestras+i]=oldValues[i];
        }
        newSignal.setValues(newValues);
    }

    private MarkPlugin buscarPrimerLatido(List<MarkPlugin> m) {
        MarkPlugin primerLatido=m.get(0);
        for (MarkPlugin markPlugin : m) {
            if (primerLatido.getMarkTime()>markPlugin.getMarkTime()) {
                primerLatido=markPlugin;
            }
        }
        return primerLatido;
    }

    private float[] corrigeInicioYFin(File f, Signal s, SignalManager sm) {
        Signal ecg = sm.getSignal("ECG");
        if (ecg == null) {//no hay ECG; estamos abriendo directamente un registro hrv y no añadiendo
            return s.getValues();
        }
        float ventana = extraerVentanaEnSegundos(f);
        float fs = obtenerFS(f);
        int muestras = Math.round(ventana*fs);
        float[] oldValues = s.getValues();
        float[] newValues = new float[oldValues.length+muestras];
        int numCerosPrincipioYFinal= muestras/2 + muestras%2;
        int i =0;
        for (; i < numCerosPrincipioYFinal; i++) {
            newValues[i]=0;
        }
        for (i=0; i < oldValues.length; i++) {
            newValues[numCerosPrincipioYFinal+i]=oldValues[i];
        }
        for (i=numCerosPrincipioYFinal+i; i < newValues.length; i++) {
            newValues[i]=0;
        }
        return newValues;
    }

    private float extraerVentanaEnSegundos(File f) throws NumberFormatException {
        String nombreArchivo = f.getName();
        int indicePrincipioVentana = nombreArchivo.indexOf('_');
        int indiceFinalVentana = nombreArchivo.lastIndexOf('.');
        String ventanaEnSegundosString = nombreArchivo.substring(indicePrincipioVentana + 1, indiceFinalVentana);
        float ventanaEnSegundos = Float.parseFloat(ventanaEnSegundosString);
        return ventanaEnSegundos;
    }

    private float obtenerFS(File f) {
        String n = f.getName();
        int i = n.lastIndexOf('_');

        int i2 = n.indexOf('.');
        String s2 = n.substring(i + 1, i2);
        return 1 / Float.parseFloat(s2);

    }
}
