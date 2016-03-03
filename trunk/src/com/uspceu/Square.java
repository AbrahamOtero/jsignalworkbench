package com.uspceu;

import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import java.util.List;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.MarkPlugin;

public class Square extends SimpleAlgorithm{

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, 
                             float[] datos, float samplingFrquency) {
        for (int i = 0; i < datos.length; i++) {
            datos[i] = datos[i]*datos[i];
        }
       List<MarkPlugin> latidos = signal.getAllMarks();
        for (MarkPlugin latido : latidos) {
            System.out.println(" "+(latido.getMarkTime()- signal.getStart())); 
            
            ;
        }
    }

    public String getName() {
        return "Cuadrado";
    }    
}
