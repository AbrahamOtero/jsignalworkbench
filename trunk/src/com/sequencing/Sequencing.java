
package com.sequencing;

import com.sequencing.GUI.Configure;
import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

public class Sequencing extends SimpleAlgorithm{
    public static int HEIGHT;
    public static double WIDTH;
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int numberOfSamples = 0;
        int peakStart = 0;
        int peakEnd = 0;
        int maxPos = 0;
        float maxValue = 0;
        int width = 0;
        HEIGHT = 300;
        WIDTH = 12;
        
        for (int i = 0; i < datos.length; i++) {
            if(datos[i]>=HEIGHT){
                peakStart = i;
                numberOfSamples = 0;
                //CREAR LETRA?
                while (datos[i]>=HEIGHT && i<datos.length && numberOfSamples<=WIDTH){
                    i++;
                    numberOfSamples++;
                    if(datos[i]<datos[i-1]&&datos[i-1]>datos[i-2]){
                        maxValue = datos[i-1];
                        maxPos = i-1;
                        //CREAR LETRA?
                    }
                }
                peakEnd = i;
                Peak peak = new Peak(numberOfSamples,peakStart,peakEnd,maxPos,maxValue);
                DefaultIntervalMark mark = createIntervalMark(peakStart, peakEnd, signal);  
                mark.setColor(Color.blue);
                signal.addMark(mark);
            } 
        }
    }
    
    public boolean hasOwnConfigureGUI() {
        return true;
    }
    
    public void launchConfigureGUI(JSWBManager jswbManager){
        Configure c = new Configure();
        c.show();
    }

    public String getName() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Detectar Picos";
    }
    
}
