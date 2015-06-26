package research.mining.tmp;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.Collection;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import java.util.Arrays;

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
public class CalcularValorBasalSpO2 extends AlgorithmAdapter {

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Signal s  =sm.getSignal("Sat02");
        float f []=s.getValues();
       float f2 []=Arrays.copyOf(f,f.length);
       double media =0;
       int contador=0;
       for (Float dato : f2) {
          if (dato<= 100&&dato>20) {
              media+=dato;
              contador++;
          }
       }
       media/=contador;

       double basal =0;
       contador=0;

       Arrays.sort(f2,0,f2.length);
       for (int i = 8*f2.length/10; i < f2.length; i++) {
           if (f2[i]<= 100&&f2[i]>20) {
               basal+=f2[i];
               contador++;
          }
       }
       basal/= contador;

       System.out.println("Media: "+ media+ " Basal: "+ basal);
    }

    public boolean hasOwnExecutionGUI() {
        return true;
    }

    public void launchExecutionGUI(JSWBManager jswbManager) {
        this.runAlgorithm(jswbManager.getSignalManager(), null, null);
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return false;
        }
        return false;
    }

    public Icon getIcon() {
        return super.generateImage("mm");
    }

    public String getName() {
        return "Media ";
    }

}
