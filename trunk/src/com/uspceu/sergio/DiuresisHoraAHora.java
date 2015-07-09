/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uspceu.sergio;

import com.uspceu.SimpleAlgorithm;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

/**
 *
 * @author Sergio
 */
public class DiuresisHoraAHora extends SimpleAlgorithm {
   
     public String getName() {
           return "Calculo de la diuresis hora a hora";
    }
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
        
        //hora a hora
        float newData[] = new float[datos.length/60];
        
        for (int i = 59; i < datos.length;) {
            newData[i/60] = datos[i]-datos[i-59];
            i = i + 60;
        }

        Signal square = new Signal("Hora a hora de " + signal.getName(),
                              newData, fs/60 , signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
    }
}
