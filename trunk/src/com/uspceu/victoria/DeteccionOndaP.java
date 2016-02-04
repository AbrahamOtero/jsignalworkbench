
package com.uspceu.victoria;

import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import java.util.List;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;

/**
 *
 * @author Victoria
 */
public class DeteccionOndaP extends SimpleAlgorithm{
    
    @Override
    public void runAlgorithm (SignalManager manager, Signal signal, float[] datos,float freq ){
            
        List<MarkPlugin> latidos =  signal.getAllMarks();
        float ondasP [] = new float[datos.length];
        int i = 0;
            for (MarkPlugin latido : latidos ) {
               
                int marca = (int) latido.getMarkTime();
                int tiempoIn = (int) ((marca - 220)-signal.getStart());
                int tiempoFin = (int) ((marca - 80)-signal.getStart());
                
                float max = 0;
                
                
                int posIni = (int)(tiempoIn*freq/1000);
                int posFin = (int)(tiempoFin*freq/1000);
                int posicionP = 0;

                
                    for(int j = posIni; j < posFin; j++){
                            if(j==0){
                                max = datos[j];
                            }
                            if (datos[j]>max){
                                max = datos[j];
                                posicionP = j;
                            }
                    }
                ondasP[i] = posicionP;
                i = i+1;
                int iniMarca = (int) (posicionP-2);
                int finMarca = (int) (posicionP+2);
                
                //System.out.println("Valor de P: "+max);
                //System.out.println("Posicion de P: "+posicionP);
                
                long pplong = TimePositionConverter.positionToTime(posicionP, signal);
                String maximoP = Long.toString(pplong);
                
                DefaultIntervalMark mark = createIntervalMark(iniMarca, finMarca, signal);
                mark.setTitle("P");//posicion maximo en cada onda P
                mark.setComentary(maximoP);
                mark.setColor(Color.BLUE);
                signal.addMark(mark);
                }
        }

    public String getName() {
         return "OndaP";
    }
}
