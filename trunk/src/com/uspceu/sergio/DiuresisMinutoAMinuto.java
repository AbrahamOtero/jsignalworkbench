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
public class DiuresisMinutoAMinuto extends SimpleAlgorithm  {
    
    public String getName() {
           return "Calculo de la diuresis minuto a minuto";
    }
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
        
        System.out.println("fs "+ fs +"  tiempo "+100/fs );
        for (int i = 0; i < 100; i++) {
            System.out.println(""+datos[i]);
        }
        
        //minuto a minuto
        float newData[] = new float[datos.length/60 +1];
        for (int i = 0; i < datos.length; i++) {
           float acumulado = 0;
           for (int j =i; j < i+60&&j<datos.length; j++) {
              acumulado+=datos[j];
           }
           newData[i/60] = acumulado;
        }

        Signal square = new Signal("Minuto a minuto de " + signal.getName(),
                             newData, fs/60, signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
    }  
}