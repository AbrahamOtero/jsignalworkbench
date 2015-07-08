package com.uspceu;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

public class SquareNewSignal extends SimpleAlgorithm {

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, 
                float[] datos, float samplingFrquency) {

           float newData[] = new float[datos.length];
        for (int i = 1; i < datos.length; i++) {
            newData[i] = datos[i]*datos[i];
        }

        Signal square = new Signal("Cuadrado de" + signal.getName(),
                              newData, samplingFrquency, signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
    }

    public String getName() {
        return "Cuadrado nueva señal";
    }
}
