package com.uspceu;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

public class Square extends SimpleAlgorithm{

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, 
                             float[] datos, float samplingFrquency) {
        for (int i = 0; i < datos.length; i++) {
            datos[i] = datos[i]*datos[i];
        }
    }

    public String getName() {
        return "Cuadrado";
    }    
}
