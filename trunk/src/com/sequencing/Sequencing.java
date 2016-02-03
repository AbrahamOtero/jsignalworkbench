
package com.sequencing;

import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

public class Sequencing extends SimpleAlgorithm{
    public static final double HEIGHT = 300;
    public static final double WIDTH = 12;
    
    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //test
        int numberOfSamples = 0;
        int peakStart = 0;
        int peakEnd = 0;
        int maxPos;
        float maxValue;
        int width = 0;
        
//        for(int i=0 ; i<datos.length ; i++){
//            if(datos[i]>=HEIGHT && numberOfSamples<=WIDTH){
//                peakStart = i;
//                if(datos[i]<datos[i-1]){
//                    maxValue = datos[i-1];
//                    maxPos = i-1;
//                    //CREAR LETRA
//                }
//                numberOfSamples++;
//                if(numberOfSamples==WIDTH){
//                    peakEnd = peakStart + numberOfSamples;
//                    //CREAR PICO
//                    DefaultIntervalMark mark = createIntervalMark(peakStart, peakEnd, signal);  
//                    mark.setColor(Color.blue);
//                    signal.addMark(mark);
//                }
//            }
//            else if(datos[i]>=HEIGHT && numberOfSamples>WIDTH){
//                peakEnd = peakStart;
//                numberOfSamples = 0;
//                i--; //Así el for repite el mismo, pero en vez de entrar aquí entra en el if de arriba, volviendo a hacer el pico.
//            }
//            
//        }
        
        for (int i = 0; i < datos.length; i++) {
            if(datos[i]>=HEIGHT){
                peakStart = i;
                numberOfSamples = 0;
                //CREAR LETRA?
                while (datos[i]>=HEIGHT && i<datos.length && numberOfSamples<=WIDTH){
                    i++;
                    numberOfSamples++;
                    if(datos[i]<datos[i-1]){
                        maxValue = datos[i-1];
                        maxPos = i-1;
                        //CREAR LETRA?
                    }
                }
                peakEnd = i;
                //CREAR PICO
                DefaultIntervalMark mark = createIntervalMark(peakStart, peakEnd, signal);  
                mark.setColor(Color.blue);
                signal.addMark(mark);
            } 
        }
    }

    public String getName() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "Detectar Picos";
    }
    
}
