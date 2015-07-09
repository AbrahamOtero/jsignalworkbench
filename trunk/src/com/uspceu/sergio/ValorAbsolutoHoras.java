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
    
    Signal biometrix = signalManager.getSignal("Hora a Hora de Acumulado de Biometrix");
    Signal bascula = signalManager.getSignal("Hora a hora de Acumulado de Bascula");
    
    float [] arrayBiometrix = bascula.getValues();
    float [] arrayBascula = bascula.getValues();
    float newData[] = new float[datos.length/60];
    
    for (int i = 59; i < datos.length;) {
       newData[i/60] = Math.abs(arrayBiometrix[i] - arrayBascula[i]);
       i = i + 60;
    }
    
    Signal square = new Signal("Hora a hora de " + signal.getName(),
                              newData, fs/60 , signal.getStart(), "Unidades");
    square.adjustVisibleRange();
    signalManager.addSignal(square);
    }

    
}
