
package com.uspceu.victoria;

import com.uspceu.SimpleAlgorithm;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.io.BasicSaver;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
 *
 * @author Victoria
 */


public class ExportarOndaP extends SimpleAlgorithm{
    /*
    private String directorio = null;
    private JFileChooser jf;
    private BasicSaver basicSaver;
    private String ultimoDirectorioAbierto = null;
    private float pp[];
    private boolean error = true;
    private boolean exportRHRV = true;
    
    
    public ExportarOndaP(){
        basicSaver = new BasicSaver();
        jf = new JFileChooser(directorio);
    }
    
     public int numberOfSignalsNeeded() {
        return 0;
    }
     
    public String getDataToSave() {
        return directorio;
    } 
    
    public String getDescription() {
        return "Obtencion de intervalos PP para el latido";
    }
    
     public String getName() {
        return "ExporOndaP";
    }

    public boolean hasDataToSave() {
        return true;
    }

    public boolean hasOwnConfigureGUI() {
        return false;
    }
    
    public void setSavedData(String data) {
        jf.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                if (f.getName().toLowerCase().endsWith(".beats")) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                String desc = "Archivos PP";
                return desc;
            }
        });
    }
    
    

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties> signals, AlgorithmRunner ar){
    
    }*/
    
    
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {
        float marcasP [] = marcasPDetectadas(signalManager, signal, datos, fs);
        List<MarkPlugin> ondasP = devolverMarcasP(signalManager, signal, datos);
        
        
        
    }
    
    /*public List<MarkPlugin> marcasP (SignalManager signalManager, Signal signal){
        List<MarkPlugin> latidos = signal.getAllMarks();
        List<MarkPlugin> ondasP = null ;
        
        for(MarkPlugin latido: latidos){
            
            if(latido.getName().equals("P")){
                ondasP.add(latido);
            }
        }
        return ondasP;
    }*/
    
    public List<MarkPlugin> devolverMarcasP (SignalManager signalManager, Signal signal, float[] datos){
        List<MarkPlugin> latidos = signal.getAllMarks();
        List<MarkPlugin> ondasP = new ArrayList<MarkPlugin>() ;
    
            for (MarkPlugin latido: latidos){
            int i = (int) (latido.getMarkTime()-signal.getStart());
            System.out.println(""+i);    
                if(datos[i]>2050 && datos[i]<2250){
                    ondasP.add(latido);
                }
            }
        return ondasP;
    }
    
    public float[] marcasPDetectadas (SignalManager signalManager, Signal signal, float[] datos, float freq){
        
        List<MarkPlugin> latidos = signal.getAllMarks();
        float ondasP [] = new float[datos.length];    
        int i = 0;
        
            for (MarkPlugin latido: latidos){
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
                }
            return ondasP;
    }
    
    public String getName() {
         return "Exportar onda P";
    }
    
}
