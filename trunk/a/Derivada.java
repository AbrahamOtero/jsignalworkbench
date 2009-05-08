package a;


import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.Signal;

public class Derivada extends AlgorithmAdapter {
    private Signal s;


    public String getName() {
        return "Derivada";
    }
    public void runAlgorithm(SignalManager sm,
        List<SignalIntervalProperties> signals) {
        float[] derivada=null;
        float [] datos;
        for (SignalIntervalProperties si : signals) {
            s= si.getSignal();
            datos =s.getValues();
         derivada=new float[datos.length];
            if (!si.isFullSignal()) {
                for (int i = 1; i < datos.length; i++) {
                   derivada[i] = datos[i] - datos[i - 1];
               }

            } else {
                for (int i = 1; i < datos.length; i++) {
                    derivada[i] = datos[i] - datos[i - 1];
        }
            }


        }
        /**/
        s = new Signal(s.getName()+"'",
                      derivada,
                      s.getSRate(),s.getStart(), "posibilidad");
                 sm.addSignal(s);

         s.setVisibleRange(0, 10, 400);
         /* */

}

}
