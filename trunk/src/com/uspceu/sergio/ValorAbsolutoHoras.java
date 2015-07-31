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
public class ValorAbsolutoHoras extends SimpleAlgorithm {
       
    public String getName() {
        return "Calculo del valor absoluto hora a hora";
    }
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
    
    Signal biometrix = signalManager.getSignal("Hora a hora de Acumulado de Biometrix");
    Signal bascula = signalManager.getSignal("Hora a hora de Bascula");
    
    float arrayBiometrix[];
    float arrayBascula[];
    
    arrayBiometrix = biometrix.getValues();
    arrayBascula = bascula.getValues();
    float newData[] = new float[arrayBascula.length];
    
    for (int i = 0; i < arrayBascula.length; i++) {
       newData[i] = Math.abs(arrayBiometrix[i] - arrayBascula[i]);
    }
    
    Signal square = new Signal("Error absoluto hora a hora",
                              newData, biometrix.getSRate() , signal.getStart(), "Unidades");
    square.adjustVisibleRange();
    signalManager.addSignal(square);
    }

    
}
