package research.beats.hrv;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.ArrayList;
import java.util.Date;
import net.javahispano.jsignalwb.SignalManager;
import java.io.File;
import net.javahispano.jsignalwb.io.BasicLoader;
import net.javahispano.jsignalwb.jsignalmonitor.Resample;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class MultiTXTLoader extends BasicLoader {
 private String[]nombres ={"ECG","Flujo","Sat02","Movimiento abdominal","Movimiento toracico"};

        //float[][] s ={ecg,flujo,sp02,a,t};
    public String getName(){
        return "MultiTXTLoader";
    }

    public String getShortDescription() {
        return "Cargador inicial";
    }

    /**
     * La extensión que soporta es "txt".
     *
     * @return ArrayList
     */
    public ArrayList<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("Carpetas");
        return ext;
    }
    protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
        //  float fs = obtenerFS(f);
       float[] ecg = super.load(new File(f.getAbsoluteFile()+"\\ECG.txt"))[0];
       float[] flujo = super.load(new File(f.getAbsoluteFile()+"\\Flow.txt"))[0];
       float[] a = super.load(new File(f.getAbsoluteFile()+"\\Abdom.txt"))[0];
       float[] t = super.load(new File(f.getAbsoluteFile()+"\\Torax.txt"))[0];
       float[] sp02 = super.load(new File(f.getAbsoluteFile()+"\\SPO2.txt"))[0];
       flujo = Resample.resampleFs(flujo,32,4,true);
       a = Resample.resampleFs(a,32,4,true);
       t = Resample.resampleFs(t,32,4,true);
        float[] fs={256,4,4,4,4};
        float[][] s ={ecg,flujo,sp02,a,t};
        for (int i = 0; i < 5; i++) {
            Signal newSignal = new Signal(nombres[i], s[i]);
            newSignal.setFrecuency(fs[i]);
            newSignal.setStart((new Date(100, 1, 1, 0, 0,0)).getTime());
            sm.addSignal(newSignal);
            newSignal.adjustVisibleRange();
        }
        JSWBManager.getJSWBManagerInstance().setJSMFrecuency(fs[0]);
        return flag;
    }

    private float obtenerFS(File f) {
        String n = f.getName();
        int i = n.indexOf('_');

        int i2 = n.indexOf('.');
         String s2 = n.substring(i+1,i2);
         return 1/Float.parseFloat(s2);

    }
}
