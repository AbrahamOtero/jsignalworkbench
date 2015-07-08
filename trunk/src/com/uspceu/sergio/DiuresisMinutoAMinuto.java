/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uspceu.sergio;

import com.uspceu.SimpleAlgorithm;
import javax.swing.Icon;
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
    public Icon getIcon(){
        return null;
    }
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
        
        System.out.println("fs "+ fs +"  tiempo "+100/fs );
        for (int i = 0; i < 100; i++) {
            System.out.println(""+datos[i]);}
       
        //minuto a minuto
        float newData[] = new float[datos.length];
        
        for (int i = 1; i < datos.length; i++) {
          if(datos[i+1]<datos[i]){
            newData[i] = 0;
          }
          newData[i] = datos[i]-datos[i-1];
        }

        Signal square = new Signal("Minuto a minuto de " + signal.getName(),
                              newData, fs , signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
    }  
}
