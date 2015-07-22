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
public class ValorAbsolutoMinutos extends SimpleAlgorithm{
    
    public String getName() {
          return "Calculo del valor absoluto minuto a minuto";
    }
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
    
    Signal biometrix = signalManager.getSignal("Minuto a minuto de Acumulado de Biometrix");
    Signal bascula = signalManager.getSignal("Minuto a minuto de Bascula");
    
    float arrayBiometrix[];
    float arrayBascula[];
    
    arrayBiometrix = biometrix.getValues();
    arrayBascula = bascula.getValues();
    float newData[] = new float[datos.length];
    
    for (int i = 0; i < datos.length; i++) {
       newData[i] = Math.abs(arrayBiometrix[i] - arrayBascula[i]);
    }
    
    Signal square = new Signal("Error minuto a minuto",
                              newData, fs , signal.getStart(), "Unidades");
    square.adjustVisibleRange();
    signalManager.addSignal(square);
    }

    
}
