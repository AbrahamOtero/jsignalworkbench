package com.uspceu;

import java.awt.Color;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;

public class SquareNewSignalBeatDetect extends SimpleAlgorithm {
    public static final double THRESHOLD = 5E7;

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal ecg, 
                float[] datos, float samplingFrquency) {

        float newData[] = new float[datos.length];
        for (int i = 0; i < datos.length; i++) {
            newData[i] = datos[i]*datos[i];
        }
        

        Signal square = new Signal("Cuadrado de" + ecg.getName(),
                              newData, samplingFrquency, ecg.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
        
        
        for (int i = 0; i < newData.length; i++) {
            if(newData[i]>THRESHOLD){
                int start = i;
                while (newData[i]>THRESHOLD && i < newData.length){
                    i++;
                }
                int end = i;
                DefaultIntervalMark mark = createIntervalMark(start, end, ecg);  
                mark.setColor(Color.blue);
                ecg.addMark(mark);
            }            
        }
    }

    public String getName() {
        return "Cuadrado nueva señal y detectar";
    }
}
