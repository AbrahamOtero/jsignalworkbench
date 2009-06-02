package research.beats.hrv;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.BasicLoader;

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
public class HRVLoader extends BasicLoader {
    private String[] nombres = {"ULF", "VLF", "LF", "HF", "LF/HF", "HRV"};
    public String getName() {
        return "HRVLoader";
    }

    public String getShortDescription() {
        return "HRV Loader";
    }

    /**
     * La extension que soporta es "txt".
     *
     * @return ArrayList
     */
    public ArrayList<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("hrv");
        return ext;
    }

    protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
        float fs = obtenerFS(f);
        super.load(f, sm);
        int numberOfSignals = sm.getSignalsSize();
        for (int i = 0; i < numberOfSignals; i++) {
            Signal s = sm.getSignal("Signal" + i);
            if (s == null) {
                return false;
            }
            Signal newSignal = new Signal(nombres[i], s.getValues());
            newSignal.setFrecuency(fs);
            newSignal.setStart((new Date(100, 1, 1, 0, 0, 0)).getTime());
            sm.removeSignal(s.getName());
            sm.addSignal(newSignal);
            newSignal.adjustVisibleRange();
        }
        JSWBManager jsw = JSWBManager.getJSWBManagerInstance();
        if (jsw.isDeleteSignalsInNextLoad()) {
            jsw.setJSMFrecuency(fs);
        }
        return flag;
    }

    private float obtenerFS(File f) {
        String n = f.getName();
        int i = n.indexOf('_');

        int i2 = n.indexOf('.');
        String s2 = n.substring(i + 1, i2);
        return 1 / Float.parseFloat(s2);

    }
}
