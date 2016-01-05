
package com.uspceu.victoria;

import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import java.util.List;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

public class MarcasP extends SimpleAlgorithm {
    
    
    @Override
    public void runAlgorithm (SignalManager manager, Signal signal, float[] datos,float freq ){
            List<MarkPlugin> latidos =  signal.getAllMarks();
        
            for (MarkPlugin latido : latidos) {
                
                int marca = (int)latido.getMarkTime();
                int tiempoIn = (int) ((marca - 220)-signal.getStart());
                int tiempoFin = (int) ((marca - 80)-signal.getStart());

                System.out.println(""+(latido.getMarkTime()-signal.getStart()));
                System.out.println(""+marca);
                System.out.println(""+tiempoIn);
                System.out.println(""+tiempoFin);


                DefaultIntervalMark mark = createIntervalMark((int)(tiempoIn*freq/1000),
                        (int)(tiempoFin*freq/1000), signal);
                mark.setTitle("P");
                mark.setColor(Color.yellow);
                signal.addMark(mark);

            
            }
        
        }
        
    
    public String getName() {
        return "OndaP";
    }
    
}
