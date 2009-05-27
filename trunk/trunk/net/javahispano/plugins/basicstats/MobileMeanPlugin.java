package net.javahispano.plugins.basicstats;

import java.util.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.*;
import javax.swing.Icon;
import javax.swing.*;
import net.javahispano.jsignalwb.plugins.framework.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MobileMeanPlugin extends AlgorithmAdapter {
    private int window = 4;
    private boolean mediana = false;
    private boolean eliminarHuecos = true;
    private float valorHuecos=50;


    public String getDataToSave() {
        return window+" "+mediana+" "+eliminarHuecos+" "+valorHuecos;
    }

    public void setSavedData(String data) {
        StringTokenizer t = new StringTokenizer(data);
        try {
            window = Integer.parseInt(t.nextToken());
            mediana = Boolean.parseBoolean(t.nextToken());
            eliminarHuecos = Boolean.parseBoolean(t.nextToken());
            valorHuecos = Float.parseFloat(t.nextToken());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public int numberOfSignalsNeeded() {
        return 1;
    }

    /**
     *@todo parece que no añade la nueva señal
     * @param sm SignalManager
     * @param signals Enumeration
     */
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        Signal signal = signals.get(0).getSignal();
        float[] data = signal.getValues();
        float[] newData;
        int numberOfSamples  = Math.round(this.window*signal.getSRate());

            if (!this.mediana) {
                try {
                    newData = MediaMovil.calculaMediaMovilClone(data, numberOfSamples);
                } catch (MediaMovilException ex) {
                    ex.printStackTrace();
                    return;
                }
            } else {
                int medium;
                float[] dataOnWindow;
                if (numberOfSamples%2 != 0) {
                    medium = numberOfSamples/2;
                    dataOnWindow = new float[numberOfSamples];
                }
                else{
                    medium = (numberOfSamples+1)/2;
                    dataOnWindow = new float[numberOfSamples+1];
                }
                newData = new float[data.length];
                for (int i = 0; i < medium; i++) {
                    newData[i] = data[i];
                }
                for (int i = medium; i < data.length-medium; i++) {
                    int c=0;
                    for (int j = i-medium; j < i-medium+dataOnWindow.length; j++) {
                        dataOnWindow[c++] = data[j];
                    }
                    Arrays.sort(dataOnWindow);
                    newData[i] = dataOnWindow[medium];
                }
            }
            //si hay que eliminar los huecos
            if (eliminarHuecos) {
                for (int i = 2; i < newData.length; i++) {
                    if (newData[i]<valorHuecos) {
                        newData[i]=(newData[i-1]>newData[i-2])?newData[i-1]:newData[i-2];
                    }
                }
            }

        Signal newSignal = new Signal(signal.getName() + "_Suave1_"+this.window, newData,
                                      signal.getSRate(), signal.getStart(),
                                      signal.getMagnitude());
            sm.addSignal(newSignal);
    }

    public boolean hasDataToSave() {
        return false;
    }


    public String getName() {
        return "Media movil";
    }

    public String getShortDescription() {
        return "Calcula la media móvil de una señal";
    }

    public String getDescription() {
        return "Calcula la media móvil de una señal";
    }

    public String getPluginVersion() {
        return "1.0";
    }

    public boolean hasOwnConfigureGUI() {
        return true;
    }

    public void launchConfigureGUI(JSWBManager jswbManager) {
        DialogMobileMeanPlugin c = new DialogMobileMeanPlugin((JFrame)jswbManager.getParentWindow(),"Mobile Mean", true, window);
        c.setRellenar(this.eliminarHuecos);
        c.setValorRelleno(this.valorHuecos);
        c.setMediana(this.mediana);
        c.setVisible(true);
        this.window = c.getWindow();
        this.mediana = c.isMediana();
        this.valorHuecos = c.getValorRelleno();
        this.eliminarHuecos = c.isRellenar();
    }




}
