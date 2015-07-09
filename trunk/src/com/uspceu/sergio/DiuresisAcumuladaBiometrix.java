/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uspceu.sergio;

import com.uspceu.SimpleAlgorithm;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

/**
 *
 * @author Sergio
 */
public class DiuresisAcumuladaBiometrix  extends SimpleAlgorithm {

    public String getName() {
           return "Calculo de la diuresis acunulada";
    }

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
        
        //acumulada
        float newData[] = new float[datos.length];
        float acumulado = 0;
        
        for (int i = 1; i < datos.length; i++) {
          if(datos[i]<datos[i-1]){
            acumulado = datos[i-1]+acumulado;
          }
          newData[i] = datos[i] + acumulado;
        }

        Signal square = new Signal("Acumulado de " + signal.getName(),
                              newData, fs , signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
    }      
}
    

