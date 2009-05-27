package research.apneas;

import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.AlgorithmRunner;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.Signal;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: a
 * Date: 03-dic-2008
 * Time: 20:30:07
 * To change this template use File | Settings | File Templates.
 */
public class EraseInterval extends AlgorithmAdapter{
    public String getName() {
        return  "Borrar intervalo";
    }
      public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
          SignalIntervalProperties i = signals.get(0);
        Signal s = i.getSignal();
          float[] d= s.getValues();
          int borrar = i.getLastArrayPosition()-i.getFirstArrayPosition();
          int j ;
          for (j = i.getLastArrayPosition(); j < d.length; j++) {
              d[j-borrar]=d[j];

          }
          for (int k = j-borrar; k < d.length; k++) {
              d[k]=0;
          }


    }
}
