package tmp;

import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

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
public class PigAlg extends AlgorithmAdapter {
    public String getName() {
        return "Diuresis";
    }

    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        SignalIntervalProperties i = signals.get(0);
        Signal s = i.getSignal();
        float[] d = s.getValues();

        /*/  float[] d2 = new float[d.length/1+1];
          Signal s2 = new Signal ("Urine1",d2,1.0F/60,s.getStart(), "");
          sm.addSignal(s2);
          for (int j = 0; j < d.length-1; j++) {
              System.out.println(""+j+ " "+d.length);
              d2[j] = d[j+1]-d[j];
              d2[j] = d2[j] >0?d2[j] :0;

  }/**/


/*/  float[] d2 = new float[d.length/10+1];
             Signal s2 = new Signal ("Urine2",d2,1.0F/600,s.getStart(), "");
             sm.addSignal(s2);
             for (int j = 0; j < d.length-10; j+=10) {
                 d2[j/10] = (d[j+1]+d[j+2]+d[j+3]+d[j+4]+d[j]
                           +d[j+5]+d[j+6]+d[j+7]+d[j+8]+d[j+9])/10;


        }/**/

        /**/  float[] d2 = new float[d.length/30+1];
             Signal s2 = new Signal ("Urine2",d2,1.0F/1800,s.getStart(), "");
             sm.addSignal(s2);
             for (int j = 0; j < d.length-30; j+=30) {
                 d2[j/30] = (d[j+1]+d[j+2]+d[j+3]+d[j+4]+d[j]
                           +d[j+5]+d[j+6]+d[j+7]+d[j+8]+d[j+9]
                           +d[j+10]+d[j+11]+d[j+12]+d[j+13]+d[j+14]
                           +d[j+15]+d[j+16]+d[j+17]+d[j+18]+d[j+19]
                           +d[j+20]+d[j+21]+d[j+22]+d[j+23]+d[j+24]
                         +d[j+25]+d[j+26]+d[j+27]+d[j+28]+d[j+29])/20;

        }/**/


      /*/  float[] d2 = new float[d.length/5+1];
                Signal s2 = new Signal ("Urine2",d2,1.0F/300,s.getStart(), "");
                sm.addSignal(s2);
                for (int j = 0; j < d.length-5; j+=5) {
                    System.out.println(""+j+ " "+d.length);
                    d2[j/5] = d[j+5]-d[j];

        }/**/
       /*/ float[] d2 = new float[d.length/4+1];
        Signal s2 = new Signal ("Urine3",d2,1.0F/1200,s.getStart(), "");
        sm.addSignal(s2);
        for (int j = 0; j < d.length-4; j+=4) {
            System.out.println(""+j+ " "+d.length);
            d2[j/4] = d[j+1]+d[j+2]+d[j+3]+d[j];

        }/**/

    }
}
