package research.spo2;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

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
public class TestJSWB extends AlgorithmAdapter {

    private static void test(float[] test) {
        DetectorDesaturaciones b = new DetectorDesaturaciones();
        b.setTiempoInicial(inicio);
        for (int i = 0; i < test.length; i++) {
            Desaturacion d = b.anadeDato(test[i]);
            // System.out.println("*****************************Valor basal "+b.getValorBasal());

            if (d != null) {
                System.out.println(d.toString());
                if (!d.isInicioSolo() && true) {
                    DefaultIntervalMark m = new DefaultIntervalMark();
                    m.setMarkTime(inicio + d.getComienzo() * 1000);
                    m.setEndTime(inicio + d.getFin() * 1000);
                    m.setComentary("" + d.getPos() + " " + d.getValorMinimo());
                    //m.setMarkTime(inicio + 10*1000);
                    //  m.setEndTime(inicio + 100*1000);



                    JSWBManager.getSignalManager().addSignalMark("SaO2", m);
                }
            }
        }
    }

    public void runAlgorithm(SignalManager sm, float[] signal) {

        inicio = JSWBManager.getSignalManager().getSignal("SaO2").getStart();
        test(signal);
    }

    public String getName() {
        return "Test";
    }

    private static long inicio = 0;

}
