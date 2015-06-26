package research.beats;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.ArrayList;
import java.util.Date;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import java.io.File;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.io.BasicLoader;

public class RatasLoader extends BasicLoader {

    private String[] nombres = {"I", "II", "III"};


    public String getName() {
        return "RataLoader";
    }

    public String getShortDescription() {
        return "Rata Loader";
    }

    public ArrayList<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("txt");
        return ext;
    }

    protected boolean load(File f, SignalManager sm) throws Exception {

        super.load(f, sm);
        int numberOfSignals = sm.getSignalsSize();
        for (int i = 0; i < numberOfSignals; i++) {
            Signal s = sm.getSignal("Signal" + i);
            if (s == null) {
                return false;
            }
            Signal newSignal = new Signal(nombres[i], s.getValues());
            newSignal.setFrecuency(500);
            Date baseDate = new Date(100, 1, 1, 0, 0, 0);
            newSignal.setStart(baseDate.getTime());
            //alinearConECG(sm,newSignal,s);
            sm.removeSignal(s.getName());
            sm.addSignal(newSignal);
            newSignal.adjustVisibleRange();
        }
        JSWBManager jsw = JSWBManager.getJSWBManagerInstance();
        if (jsw.isDeleteSignalsInNextLoad()) {
            jsw.setJSMFrecuency(500);
        }
        return true;
    }


}
